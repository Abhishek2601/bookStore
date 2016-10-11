package restapi.tut.resource;

import com.google.common.base.Strings;
import restapi.tut.dao.BookDao;
import restapi.tut.dao.UserDao;
import restapi.tut.entity.Book;
import restapi.tut.entity.User;
import restapi.tut.model.request.AddBookRequest;
import restapi.tut.ruleengine.BookShowRule;
import restapi.tut.ruleengine.DemoRule;
import restapi.tut.ruleengine.RuleEngine;
import restapi.tut.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 9/10/16.
 */
@Path("/book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookApi
{
    private BookDao bookDao;
    private UserDao userDao;
    public BookApi(BookDao bookDao, UserDao userDao){
        this.userDao = userDao;
        this.bookDao = bookDao;
    }
    @GET
    @Path("/{username}/names")
    public String getAllbookNames(@PathParam("username")final String username) throws Exception {
        final List<Book> books = bookDao.getAllBooks();
        User user = userDao.getUser(username);
        return BookService.filterBooks(user, books).toString();
    }

    @POST
    @Path("/add")

    public Response addBook(@Valid AddBookRequest request)
    {
        /*
        if(Strings.isNullOrEmpty(book.getName())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Null or empty bookName").build();
        }*/

        for(final Book dbBook : bookDao.getAllBooks()){
            if(dbBook.equals(request.getBook())){
                //TODO check book_request table if some user request this book when it was not available
                bookDao.updateBookCount(request.getBook().getName(), request.getNumOfBooks());
                return Response.status(Response.Status.ACCEPTED).entity(request.getBook()).build();

            }
        }
        bookDao.addBook(request.getBook(), request.getNumOfBooks());
        return Response.status(Response.Status.ACCEPTED).entity(request.getBook()).build();
    }
    @GET
    @Path("/name/{bookName}")
    public Response getBookByBookName(@NotNull @PathParam("bookName") String bookName)
    {

        if(Strings.isNullOrEmpty(bookName))
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
        }
        List<Book>bookList = bookDao.getBookListByName(bookName);
        return Response.status(Response.Status.ACCEPTED).entity(bookList).build();

    }

    @GET
    @Path("/author/{bookbyAuthor}")
    public Response getBookByAuthor(@NotNull @PathParam("bookbyAuthor") final String authorName)
    {
        List<Book>bookList = bookDao.getAllBooks();
        List<Book>resultList = BookService.filterBooksByAuthor(authorName, bookList);


        return Response.status(Response.Status.ACCEPTED).entity(resultList).build();
    }

}
