package restapi.tut.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by root on 9/19/16.
 */
@NoArgsConstructor
@Data
public class BookIssueRequest {
    @NotNull
    @JsonProperty(value = "book_name", required = true)
    private String bookName;
    @NotNull
    @JsonProperty(value = "user_name", required = true)
    private String userName;
    @JsonProperty("issue_date")
    private Date issue_date;
    @JsonProperty("return_date")
    private Date return_date;

}
