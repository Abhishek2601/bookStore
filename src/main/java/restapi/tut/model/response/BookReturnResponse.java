package restapi.tut.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/25/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookReturnResponse
{
    @JsonProperty("book_name")
    private String bookName;
    @JsonProperty("message")
    private String message;
    @JsonProperty("username")
    private  String userName;
}
