package restapi.tut;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import restapi.tut.dao.BookDao;
import restapi.tut.dao.BookIssueDao;
import restapi.tut.dao.UserDao;
import restapi.tut.resource.BookApi;
import restapi.tut.resource.HelloWorldResource;
import restapi.tut.resource.UserApi;

/**
 * Created by root on 9/10/16.
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration>
{
    public void run(HelloWorldConfiguration helloWorldConfiguration, Environment environment) throws Exception
    {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, helloWorldConfiguration.getDataSourceFactory(), "database");
        BookDao bookDao = jdbi.onDemand(BookDao.class);
        UserDao userdao = jdbi.onDemand(UserDao.class);
        BookIssueDao bookIssueDao = jdbi.onDemand(BookIssueDao.class);
        environment.jersey().register(new HelloWorldResource( helloWorldConfiguration.getName()));
        environment.jersey().register(new BookApi(bookDao,userdao));
        environment.jersey().register(new UserApi(userdao, bookDao, bookIssueDao));
    }
    public static void main(String [] args) throws  Exception
    {
            new HelloWorldApplication().run(args);
    }
}
