package restapi.tut.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/10/16.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","num","author"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @JsonProperty("id")
    private Integer id;
    @NotNull
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty("author")
    private String author;
    @JsonProperty(value = "num")
    private Integer num;
    @NotNull
    @JsonProperty(value = "type", required = true)
    private String type;

}
