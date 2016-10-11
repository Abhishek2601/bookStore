package restapi.tut.service;

import lombok.AllArgsConstructor;
import restapi.tut.dao.BookDao;
import restapi.tut.dao.BookIssueDao;
import restapi.tut.dao.UserDao;
import restapi.tut.entity.Book;
import restapi.tut.entity.User;
import restapi.tut.model.request.BookIssueRequest;
import restapi.tut.model.response.BookIssueResponse;
import restapi.tut.ruleengine.BookLimitRule;
import restapi.tut.ruleengine.DemoRule;
import restapi.tut.ruleengine.Rule;
import restapi.tut.ruleengine.RuleEngine;
import restapi.tut.utils.LibraryResponseMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 9/25/16.
 */
@AllArgsConstructor
public class UserService {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final BookIssueDao bookIssueDao;
    private RuleEngine ruleEngine = null;

    public BookIssueResponse buildBookIssueResponse(BookIssueRequest bookIssueRequest) {
        return  BookIssueResponse.builder().
                bookName(bookIssueRequest.getBookName())
                .userName(bookIssueRequest.getUserName())
                .build();
    }

    public User getUserByName(String userName){
        return userDao.getUser(userName);
    }

    public Book getBookByName(String bookName){
        return bookDao.getBook(bookName);
    }

    public void validateUserForBookIssue(User user, Book book) throws Exception{
        if (user == null) {
            throw new Exception(LibraryResponseMessage.USER_NOT_FOUND);
        }
        ruleEngine = intialiseRuleEngine();
        BookLimitRule bookLimitRule = new BookLimitRule(user,book,bookIssueDao,bookDao);
        ruleEngine.addRule(bookLimitRule);
        ruleEngine.valiadate();
    }



    public void validateBookForBookIssue(Book book) throws Exception{
        if(book==null){
            throw new Exception(LibraryResponseMessage.BOOK_NOT_FOUND);
        }
        if(book.getNum() < 1){
            //TODO add entry in bookrequest table so that we can notify user once book is available
            throw new Exception(LibraryResponseMessage.BOOK_LIMIT_REACHED);
        }

    }

    public void sendNotification(Integer book_id) {
        //check book is requested
        //if(requested){
            //send notification to top five requested user

        //}
    }

    public List<User> usersToNotify(){
        //created set on the basis of priority
        //priorty gold,silver,basic
        //most costly customer
        //return fist five users
        return null;
    }

    public List<Book> getBooksByUserId(int userid){
        List<Integer> bookList = bookIssueDao.allBookIds(userid);
        List<Book> books = getBookByBookIds(bookList);
        return books;

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

    public RuleEngine intialiseRuleEngine()
    {
        RuleEngine ruleEngine = new RuleEngine(new HashMap<String, Rule>());
        DemoRule demoRule = new DemoRule();
        ruleEngine.addRule(demoRule);
        return  ruleEngine;

    }
}
