package restapi.tut.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import restapi.tut.utils.UserType;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 9/19/16.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","num"})
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User
{
    @JsonProperty("id")
    private Integer id;
    @NotNull
    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty("age")
    private int age;

    @NotNull
    @JsonProperty(value = "type", required = true)
    private UserType type;

    @JsonProperty("num")
    private int num;
}
