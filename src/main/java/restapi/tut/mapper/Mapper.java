package restapi.tut.mapper;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import restapi.tut.entity.Book;
import restapi.tut.entity.BookIssueEntity;
import restapi.tut.entity.User;
import restapi.tut.utils.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 9/10/16.
 */
public class Mapper{
    public static class BookMapper implements ResultSetMapper<Book>
    {
        public Book map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException
        {
        return new Book(resultSet.getInt("id"),resultSet.getString("name"), resultSet.getString("author"),resultSet.getInt("num"), resultSet.getString("type"));
        }
    }

    //Using Builder Pattern
    public static class UserMapper implements ResultSetMapper<User>{

        public User map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return User.builder().id(resultSet.getInt("id"))
                    .type(UserType.getUserType(resultSet.getString("user_type")))
                    .name(resultSet.getString("name"))
                    .age(resultSet.getInt("age"))
                    .num(resultSet.getInt("num"))
                    .build();
        }
    }


    public static  class BookIssueMapper implements ResultSetMapper<BookIssueEntity>{
        public BookIssueEntity map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return BookIssueEntity.builder()
                    .bookId(resultSet.getInt("book_id"))
                    .userId(resultSet.getInt("user_id"))
                    .Isreturn(resultSet.getBoolean("Isreturn"))
                    .issue_date((resultSet.getDate("issue_date")))
                    .return_date((resultSet.getDate("return_date")))
                    .build();
        }

    }

}

