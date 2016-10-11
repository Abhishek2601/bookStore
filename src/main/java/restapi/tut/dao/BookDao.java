package restapi.tut.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import restapi.tut.entity.Book;

import java.util.List;

/**
 * Created by root on 9/10/16.
 */
@RegisterMapper(restapi.tut.mapper.Mapper.BookMapper.class)
public interface BookDao extends Transactional<BookDao> {
    @SqlQuery("SELECT * From book")
    public abstract List<Book> getAllBooks();

    @SqlUpdate("insert into book (name, author, num, type) values(:b.name, :b.author, :count, :b.type)")
    public abstract void addBook(@BindBean("b") Book book, @Bind("count") Integer count);

    @SqlQuery("SELECT * From book where name = :bookName")
    public abstract  List<Book> getBookListByName(@Bind("bookName") String bookName);

    @SqlQuery("SELECT * from book where name = :bookName")
    public abstract Book getBook(@Bind("bookName") String bookName);

    @SqlUpdate("UPDATE book SET num = num + :count WHERE name = :name")
    public  abstract void updateBookCount(@Bind("name") final String bookName, @Bind("count") final Integer count);
}


