package restapi.tut.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import restapi.tut.entity.User;

import java.util.List;

/**
 * Created by root on 9/19/16.
 */
@RegisterMapper(restapi.tut.mapper.Mapper.UserMapper.class)
public interface UserDao extends Transactional<UserDao>
{
    @SqlUpdate("insert into person (name, age, user_type, num) values(:user.name, :user.age, :user.type, 0)")
    public abstract void registerUser(@BindBean("user") User user);

    @SqlQuery("SELECT * from person")
    public abstract List<User> getUserList();

    @SqlQuery("SELECT * from person where name = :name")
    public abstract User getUser(@Bind("name") String userName);

    @SqlUpdate("UPDATE person SET num = num + :count WHERE name = :name")
    public  abstract void updateBookCount(@Bind("name") final String userName, @Bind("count") final Integer count);
}
