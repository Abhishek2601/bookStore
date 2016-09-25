package restapi.tut.resource;

import com.google.common.base.Strings;
import restapi.tut.dao.BookDao;
import restapi.tut.entity.Book;
import restapi.tut.model.request.AddBookRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/10/16.
 */
@Path("/book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookApi
{
    private BookDao bookDao;
    public BookApi(BookDao bookDao){
        this.bookDao = bookDao;
    }
    @GET
    @Path("/names")
    public String getAllbookNames(){
        List bookNames = bookDao.getAllBookNames();
        return bookNames.toString();
    }
    @POST
    @Path("/add")

    public Response addBook(@Valid AddBookRequest request)
    {
        /*
        if(Strings.isNullOrEmpty(book.getName())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Null or empty bookName").build();
        }*/

        for(final Book dbBook : bookDao.getAllBookNames()){
            if(dbBook.equals(request.getBook())){
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
        List<Book>bookList = bookDao.getAllBookNames();
        List<Book>resultList = new ArrayList<Book>() ;

        for (Book b:bookList)
        {
            if(authorName.equals(b.getAuthor()))
            {
                resultList.add(b);
            }

        }
        return Response.status(Response.Status.ACCEPTED).entity(resultList).build();
    }



}
