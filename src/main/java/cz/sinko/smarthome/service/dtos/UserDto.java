package cz.sinko.smarthome.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends UserCreateUpdateDto implements Serializable {

    private Long id;

}
