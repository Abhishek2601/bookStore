package restapi.tut.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;

/**
 * Created by root on 9/19/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookIssueResponse{
    private String message;
    private String userName;
    private String bookName;
}
