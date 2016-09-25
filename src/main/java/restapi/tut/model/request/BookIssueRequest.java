package restapi.tut.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/19/16.
 */
@NoArgsConstructor
@Data
public class BookIssueRequest {
    @NotNull
    @JsonProperty("book_name")
    private String bookName;
    @NotNull
    @JsonProperty("user_name")
    private String userName;
}
