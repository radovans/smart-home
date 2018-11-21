package cz.sinko.smarthome.service;

import cz.sinko.smarthome.service.dto.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dto.UserDto;
import cz.sinko.smarthome.service.dto.UserListDto;

public interface UserService {
    
    UserDto createUser(UserCreateUpdateDto userDto);
    
    UserDto getUserById(Long id);
    
    UserDto updateUser(Long id, UserCreateUpdateDto userDto);
    
    UserListDto getAllUsers();

    void deleteUser(Long id);
    
}
