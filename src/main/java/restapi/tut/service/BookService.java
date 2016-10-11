package restapi.tut.service;

import restapi.tut.dao.BookDao;
import restapi.tut.entity.Book;
import restapi.tut.entity.User;
import restapi.tut.ruleengine.BookShowRule;
import restapi.tut.ruleengine.Rule;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 10/11/16.
 */
public class BookService {
    static Logger logger = Logger.getLogger(BookService.class.getName());
    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    //intialize executor
    // 2 threads



    /***
     *
     * @param user, listof books
     * @return this method filters book on the basis of user age
     */
    public static List<Book> filterBooks(User user, List<Book> books) throws Exception{
        List<Book> outputBooks = new ArrayList<Book>();
        List<List<Book>> listofInputBooks = listOfList(books);

        for(List<Book> bookList : listofInputBooks){

            BookServicesRunnable bookServicesRunnable = new BookServicesRunnable(bookList,outputBooks,user);
            executorService.execute(bookServicesRunnable);
        }
        executorService.shutdown();
        while(! executorService.isTerminated()){
            logger.log(Level.INFO,"Still Running");
            Thread.sleep(500);
        }

        return outputBooks;
    }

    //convertToListOfList
    private static List<List<Book>> listOfList(List<Book> list){
        final Integer sizeofList = list.size();
        List< List<Book> > listofBooks = new ArrayList<List<Book>>();
        for(int i = 0; i<sizeofList; i = i+5)
        {
            int till = i+5>sizeofList?sizeofList:i+5;
            List<Book> subList = list.subList(i,till);
            listofBooks.add(subList);
        }
        logger.log(Level.INFO, "converted in to listofList " + listofBooks.size());
        return listofBooks;
    }

    static class BookServicesRunnable implements Runnable{
        final List<Book> inputBooks;
        final List<Book> outputBooks;
        final User user;


        public BookServicesRunnable(List<Book> input, List<Book> outputBooks, User user){
            this.inputBooks = input;
            this.outputBooks = outputBooks;
            this.user = user;
        }

        public void run() {
            for(Book book : inputBooks){
                logger.log(Level.INFO,"Added Book"+book);
                try {
                    if(new BookShowRule(user, book).validate()){
                        synchronized (outputBooks){
                            outputBooks.add(book);
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.ALL.SEVERE, e.getMessage());
                }
            }

        }
    }

    public static List<Book> filterBooksByAuthor(final String authorName  ,List<Book> books){
        List<Book>resultList = new ArrayList<Book>() ;
        List<List<Book>> listofInputBooks = listOfList(books);
        for(List<Book> bookList : listofInputBooks){
            BookServicesByAuthorRunnable bookServicesByAuthorRunnableRunnable = new BookServicesByAuthorRunnable(bookList,resultList,authorName);
            executorService.execute(bookServicesByAuthorRunnableRunnable);
        }
        executorService.shutdown();
        while(! executorService.isTerminated()){
            logger.log(Level.INFO,"Still Running");
        }

        return resultList;
    }

    static class BookServicesByAuthorRunnable implements Runnable{
        final List<Book> inputBooks;
        final List<Book> outputBooks;
        final String authorName;

        public BookServicesByAuthorRunnable(List<Book> input, List<Book> outputBooks,final String authorName){
            this.inputBooks = input;
            this.outputBooks = outputBooks;
            this.authorName = authorName;
        }

        public void run() {
            for(Book book : inputBooks){
                logger.log(Level.INFO,"Added Book"+book);
                try {
                    if(authorName.equals(book.getAuthor())){
                        synchronized (outputBooks){
                            outputBooks.add(book);
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.ALL.SEVERE, e.getMessage());
                }
            }

        }
    }


}
