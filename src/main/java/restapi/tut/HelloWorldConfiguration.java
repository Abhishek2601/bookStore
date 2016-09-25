package restapi.tut;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/10/16.
 */
public class HelloWorldConfiguration extends Configuration {
    @JsonProperty("name")
    private String name;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();



    public HelloWorldConfiguration() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
