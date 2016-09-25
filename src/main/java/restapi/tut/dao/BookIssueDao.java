package restapi.tut.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import restapi.tut.entity.BookIssueEntity;
import restapi.tut.mapper.Mapper;

/**
 * Created by root on 9/19/16.
 */
@RegisterMapper(Mapper.BookIssueMapper.class)
public abstract class BookIssueDao {
    @SqlUpdate("INSERT INTO book_issue(book_id, user_id) VALUES(:bookId, :userId)")
    public abstract void issue(@Bind("bookId") final Integer bookId, @Bind("userId") final Integer userID);

    @SqlQuery("SELECT * FROM book_issue WHERE book_id = :bID and user_id = :uID")
    public abstract BookIssueEntity getbook(@Bind("bID") final Integer bookId, @Bind("uID") final Integer userId);

    @SqlUpdate("UPDATE book_issue SET Isreturn = 1 where book_id =:bookId and user_id = :userId")
    public abstract void bookReturn(@Bind("bookId") final Integer bookId, @Bind("userId") final Integer userID);
}
