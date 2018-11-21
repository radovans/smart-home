package cz.sinko.smarthome.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateUpdateDto implements Serializable {

    @NotBlank(message = "Username should not be blank")
    @Size(min = 4, max = 30, message = "Username '${validatedValue}' must be between {min} and {max} characters long")
    private String username;

}
