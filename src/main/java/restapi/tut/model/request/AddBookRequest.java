package restapi.tut.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.tut.entity.Book;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/19/16.
 */
@Data
@NoArgsConstructor
public class AddBookRequest {
    @NotNull
    @JsonProperty(value = "book_dsc", required = true)
    private Book book;
    @NotNull
    @JsonProperty(value = "num_of_books", required = true)
    private Integer numOfBooks;
}
