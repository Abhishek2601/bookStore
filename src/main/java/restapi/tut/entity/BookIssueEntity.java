package restapi.tut.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by root on 9/19/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data   //getter ,setter, hash, equals tostring automatically
@Builder

public class BookIssueEntity {

    //@JsonProperty("book_id")
    private int bookId;
   // @JsonProperty("user_id")
    private int userId;
    private boolean Isreturn;
    private Date issue_date;
    private Date return_date;
}
