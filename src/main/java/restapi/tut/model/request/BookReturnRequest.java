package restapi.tut.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/25/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class BookReturnRequest
{
    @JsonProperty("book_name")
    @NotNull
    String bookName;
    @NotNull
    @JsonProperty("user_name")
    String userName;
}
