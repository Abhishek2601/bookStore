package restapi.tut.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import restapi.tut.entity.BookIssueEntity;
import restapi.tut.mapper.Mapper;

import java.util.List;

/**
 * Created by root on 9/19/16.
 */
@RegisterMapper(Mapper.BookIssueMapper.class)
public interface BookIssueDao extends Transactional<BookIssueDao> {
    @SqlUpdate("INSERT INTO book_issue(book_id, user_id) VALUES(:bookId, :userId)")
    void issue(@Bind("bookId") final Integer bookId, @Bind("userId") final Integer userID);

    @SqlQuery("SELECT * FROM book_issue WHERE book_id = :bID and user_id = :uID")
    BookIssueEntity getbook(@Bind("bID") final Integer bookId, @Bind("uID") final Integer userId);

    @SqlUpdate("UPDATE book_issue SET Isreturn = 1, return_date = now() where book_id =:bookId and user_id = :userId")
    void bookReturn(@Bind("bookId") final Integer bookId, @Bind("userId") final Integer userID);

    @SqlQuery("select book_id from book_issue where user_id = :userId and Isreturn = 0")
    List<Integer> issuedBookIds(@Bind("userId") final Integer userId);

    @SqlQuery("select book_id from book_issue where user_id = :userId")
    List<Integer> allBookIds(@Bind("userId") final Integer userId);
}
