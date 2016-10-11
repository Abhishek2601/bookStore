package restapi.tut.resource;

import org.apache.commons.lang3.time.DateUtils;
import restapi.tut.dao.BookDao;
import restapi.tut.dao.BookIssueDao;
import restapi.tut.dao.UserDao;
import restapi.tut.entity.Book;
import restapi.tut.entity.BookIssueEntity;
import restapi.tut.entity.User;
import restapi.tut.model.request.BookIssueRequest;
import restapi.tut.model.request.BookReturnRequest;
import restapi.tut.model.response.BookIssueResponse;
import restapi.tut.model.response.BookReturnResponse;
import restapi.tut.service.UserService;
import restapi.tut.utils.LibraryResponseMessage;
import restapi.tut.utils.UserType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 9/19/16.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class UserApi
{
    private UserDao userDao;
    private BookDao bookDao;
    private BookIssueDao bookIssueDao;
    private UserService userService;
    private static Logger logger = Logger.getLogger(UserApi.class.getName());
    public UserApi(UserDao userDao, BookDao bookDao, BookIssueDao bookIssueDao)
    {
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.bookIssueDao = bookIssueDao;
        this.userService = new UserService(bookDao, userDao, bookIssueDao, null);

    }


    @POST
    @Path("/register")
    public Response registeredUser(User theUser)
    {
        List<User> users = userDao.getUserList();
        for(User user : users)
        {
           if(user.equals(theUser))
           {
               return Response.status(Response.Status.NOT_ACCEPTABLE).entity(user).build();
           }
        }
        userDao.registerUser(theUser);
        return Response.status(Response.Status.CREATED).entity(theUser).build();
    }

    @POST
    @Path("/issue")
    public Response issueBook(@Valid BookIssueRequest bookIssueRequest) {
        BookIssueResponse bookIssueResponse = userService.buildBookIssueResponse(bookIssueRequest);
        User user = userService.getUserByName(bookIssueRequest.getUserName());
        Book book = userService.getBookByName(bookIssueRequest.getBookName());
        try {
            userService.validateBookForBookIssue(book);
            userService.validateUserForBookIssue(user, book);
            bookDao.updateBookCount(book.getName(), -1);
            userDao.updateBookCount(user.getName(), 1);
            bookIssueDao.issue(book.getId(), user.getId());
            bookIssueResponse.setMessage(LibraryResponseMessage.Book_ISSUED);
            return Response.status(Response.Status.ACCEPTED).entity(bookIssueResponse).build();
        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            bookIssueResponse.setMessage(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(bookIssueResponse).build();
        }
    }



    @POST
    @Path("/returnbook")
    public Response returnBook(@Valid BookReturnRequest request)
    {
        BookReturnResponse bookReturnResponse =  BookReturnResponse.builder().
                bookName(request.getBookName())
                .userName(request.getUserName())
                .build();
        String userName = request.getUserName();
        User user = userDao.getUser(userName);
        if(user == null ){
            bookReturnResponse.setMessage(LibraryResponseMessage.USER_NOT_FOUND);
            return Response.status(Response.Status.BAD_REQUEST).entity(bookReturnResponse).build();
        }else {

            Integer issuedBookCount = user.getNum();
            if (issuedBookCount < 1) {

                bookReturnResponse.setMessage(LibraryResponseMessage.NO_ISSUED_BOOK);
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookReturnResponse).build();
            }
        }

        String bookName = request.getBookName();
        Book book = bookDao.getBook(bookName);

        if(book==null){
            bookReturnResponse.setMessage(LibraryResponseMessage.BOOK_NOT_FOUND);
            return Response.status(Response.Status.BAD_REQUEST).entity(bookReturnResponse).build();
        }


        BookIssueEntity bookIssueEntity = bookIssueDao.getbook( book.getId(), user.getId());
        if(bookIssueEntity ==null){
            bookReturnResponse.setMessage(LibraryResponseMessage.NO_ISSUED_BOOK);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookReturnResponse).build();
        }
        if(bookIssueEntity.isIsreturn()){
            bookReturnResponse.setMessage(LibraryResponseMessage.ALREADY_RETURNED);
            return Response.status(Response.Status.BAD_REQUEST).entity(bookReturnResponse).build();
        }
        try
        {
            bookDao.begin();
            bookIssueDao.begin();
            userDao.begin();
            bookDao.updateBookCount(bookName, 1);
            userDao.updateBookCount(userName, -1);
            bookIssueDao.bookReturn(book.getId(),user.getId());
            //send notifcation to user if it requested in past
            bookReturnResponse.setMessage(LibraryResponseMessage.Book_ISSUED);
            bookDao.commit();
            bookIssueDao.commit();
            userDao.commit();
            return Response.status(Response.Status.ACCEPTED).entity(bookReturnResponse).build();
        }catch (Exception e){
            bookDao.rollback();
            bookIssueDao.rollback();
            userDao.rollback();
        }

    return null;
    }


    @GET
    @Path("/{userid}/cost")
    public Response getCost(@NotNull @PathParam("userid") String userId)
    {
        User user = userService.getUserByName(userId);
        List<Book> allUserBooks = userService.getBooksByUserId(user.getId());
        int cost = 0;
        final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
        Map<String,Integer> costMap = new HashMap<String, Integer>();
        for(Book book : allUserBooks)
        {
            BookIssueEntity book_issue = bookIssueDao.getbook(book.getId(),user.getId());
            Date bookIssueDate = book_issue.getIssue_date();
            Date bookReturnDate = book_issue.getReturn_date() == null?new Date():book_issue.getReturn_date();

            Integer noOfDays = (int)((bookIssueDate.getTime() - bookReturnDate.getTime())/DAY_IN_MILLIS);
            noOfDays += 1;
            costMap.put(book.getName(),noOfDays);
            cost +=noOfDays;
        }
        costMap.put("total", cost);
        return Response.status(Response.Status.ACCEPTED).entity(costMap).build();

    }


}
