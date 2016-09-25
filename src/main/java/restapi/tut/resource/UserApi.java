package restapi.tut.resource;

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
import restapi.tut.utils.LibraryResponseMessage;
import restapi.tut.utils.UserType;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    public UserApi(UserDao userDao, BookDao bookDao, BookIssueDao bookIssueDao)
    {
        this.userDao = userDao;
        this.bookDao = bookDao;
        this.bookIssueDao = bookIssueDao;
    }



    /*@GET
    @Path("/userlist")
    public Map<String,Integer> getUserList()
    {
        //Map<String,Integer> userInfo = new Map<String,Integer>();

    }*/

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
    public Response issueBook(@Valid BookIssueRequest bookIssueRequest){
        BookIssueResponse bookIssueResponse =  BookIssueResponse.builder().
                bookName(bookIssueRequest.getBookName())
                .userName(bookIssueRequest.getUserName())
                .build();
        String userName = bookIssueRequest.getUserName();
        User user = userDao.getUser(userName);
        if(user == null ){
            bookIssueResponse.setMessage(LibraryResponseMessage.USER_NOT_FOUND);
            return Response.status(Response.Status.BAD_REQUEST).entity(bookIssueResponse).build();
        }else{
            UserType userType = user.getType();
            Integer issuedBookCount = user.getNum();
            Integer maxAllowedBook = userType.getVal();
            if(maxAllowedBook-issuedBookCount <= 0)
            {
                if(userType.equals(UserType.GOLD)){
                    bookIssueResponse.setMessage(LibraryResponseMessage.USER_LIMIT_REACHED);
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookIssueResponse).build();
                }
                else {
                    bookIssueResponse.setMessage(LibraryResponseMessage.UPGRADE_MEMBERSHIP);
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookIssueResponse).build();
                }
            }
        }
        String bookName = bookIssueRequest.getBookName();
        Book book = bookDao.getBook(bookName);

        if(book==null){
            bookIssueResponse.setMessage(LibraryResponseMessage.BOOK_NOT_FOUND);
            return Response.status(Response.Status.BAD_REQUEST).entity(bookIssueResponse).build();
        }else{
            int availableBook = book.getNum();
            if(availableBook <= 0){
                bookIssueResponse.setMessage(LibraryResponseMessage.BOOK_LIMIT_REACHED);
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookIssueResponse).build();
            }
        }


        BookIssueEntity bookIssueEntity = bookIssueDao.getbook( book.getId(), user.getId());
        if(bookIssueEntity !=null){
            bookIssueResponse.setMessage(LibraryResponseMessage.ALREADY_ISSUED);
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(bookIssueResponse).build();
        }
        bookDao.updateBookCount(bookName, -1);
        userDao.updateBookCount(userName, 1);
        bookIssueDao.issue(book.getId(),user.getId());
        bookIssueResponse.setMessage(LibraryResponseMessage.Book_ISSUED);
        return Response.status(Response.Status.ACCEPTED).entity(bookIssueResponse).build();

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
        try{
        bookDao.updateBookCount(bookName, 1);
        userDao.updateBookCount(userName, -1);
        bookIssueDao.bookReturn(book.getId(),user.getId());
        bookReturnResponse.setMessage(LibraryResponseMessage.Book_ISSUED);
        return Response.status(Response.Status.ACCEPTED).entity(bookReturnResponse).build();
        }catch (Exception e){

        }


    }
}
