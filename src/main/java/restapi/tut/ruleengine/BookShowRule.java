package restapi.tut.ruleengine;

import restapi.tut.entity.Book;
import restapi.tut.entity.User;
import restapi.tut.utils.LibraryResponseMessage;
import restapi.tut.utils.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/2/16.
 */
public class BookShowRule implements Rule {

    User user;
    Book book;


    public BookShowRule(User user,Book book)
    {
        this.user = user;
        this.book = book;
    }

    /**
     *
     * @return
     * @throws Exception
     * this function will validate user age and based on age it will show book to user.
     */
    public boolean validate() throws Exception {

        if(book == null || user ==null){
            throw new Exception(LibraryResponseMessage.BOOK_NOT_FOUND);
        }

        UserType userType = user.getType();
        Integer age = user.getAge();
        if(age > 18) {
            return true;
        }
        String bookType = book.getType();
        if(bookType.equalsIgnoreCase("comics")){
            return true;
        }
        return false;
    }



    public String getName() {
        return "BookShowRuleLimit";
    }
}
