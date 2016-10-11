package restapi.tut.ruleengine;

import restapi.tut.dao.BookDao;
import restapi.tut.dao.BookIssueDao;
import restapi.tut.entity.Book;
import restapi.tut.entity.User;
import restapi.tut.utils.LibraryResponseMessage;
import restapi.tut.utils.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 10/2/16.
 */
public class BookLimitRule implements Rule{

     User user;
     Book book;
     BookIssueDao bookIssueDao;
     BookDao bookDao;

    public BookLimitRule(User user,Book book, BookIssueDao bookIssueDao,BookDao bookDao)
    {
        this.user = user;
        this.book = book;
        this.bookIssueDao = bookIssueDao;
        this.bookDao = bookDao;
    }
    public boolean validate() throws Exception {
        if (user == null) {
            throw new Exception(LibraryResponseMessage.USER_NOT_FOUND);
        }
        UserType userType = user.getType();
        Integer issuedBookCount = user.getNum();
        Map<String, Integer> issuedBookMap = buildIssuedBookMap(user);
        if(issuedBookCount!=issuedBookMap.size()){
            throw new Exception("unknown issue");
        }
        Map<String, Integer> maxAllowedBookMap = userType.getMaxAllowedBookMap();
        Integer maxAllowedBook = maxAllowedBookMap.get(book.getType());
        issuedBookCount = issuedBookMap.get(book.getType());
        issuedBookCount = (issuedBookCount == null?0:issuedBookCount);
        maxAllowedBook = (maxAllowedBook==null?0:maxAllowedBook);
        if (maxAllowedBook - issuedBookCount <= 0) {
            if (userType.equals(UserType.GOLD)) {
                throw new Exception(LibraryResponseMessage.USER_LIMIT_REACHED);
            } else {
                throw new Exception(LibraryResponseMessage.UPGRADE_MEMBERSHIP);
            }
        }
        return true;
    }

    public String getName() {
        return "BookLimitRule";
    }


    private Map<String,Integer> buildIssuedBookMap(User user) {
        List<Integer> bookIds = bookIssueDao.issuedBookIds(user.getId());
        List<Book> books = getBookByBookIds(bookIds);
        Map<String, Integer> bookTypes = getBookTypeMap(books);
        return bookTypes;
    }

    /***
     * bookIds -> books
     * @param  bookIds(all the book ids that user have issued)
     * @return books(all the books that user have issued)
     */
    private List<Book> getBookByBookIds(List<Integer> bookIds){
        List<Book> allBooks = bookDao.getAllBooks();
        List<Book> resultBooks = new ArrayList<Book>();
        for(final Book book:allBooks){
            //contains
            if(bookIds.contains(book.getId())){
                resultBooks.add(book);
            }
        }
        return resultBooks;
    }

    /***
     * @param books
     * @return map key is bookType and value is number of book in that type
     */
    private Map<String, Integer> getBookTypeMap(List<Book> books){
        Map<String, Integer> bookTypes = new HashMap<String, Integer>();
        for(final Book book:books){
            if (bookTypes.containsKey(book.getType())){
                bookTypes.put(book.getType(), bookTypes.get(book.getType()) + 1);
            }else{
                bookTypes.put(book.getType(),1);
            }
        }
        return bookTypes;
    }
}
