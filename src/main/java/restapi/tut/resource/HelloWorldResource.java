package restapi.tut.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by root on 9/10/16.
 */
@Path("MyFirstApi")
public class HelloWorldResource
{
    private String name;
    public HelloWorldResource(String name){
        this.name = name;
    }
    @GET
    @Path("test")
    public String test()
    {
        return "hello" + name;
    }


}
