package cz.sinko.smarthome.service.services;

import cz.sinko.smarthome.service.dtos.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.UserDto;
import cz.sinko.smarthome.service.dtos.UserListDto;

public interface UserService {
    
    UserDto createUser(UserCreateUpdateDto userDto);
    
    UserDto getUserById(Long id);
    
    UserDto updateUser(Long id, UserCreateUpdateDto userDto);
    
    UserListDto getAllUsers();

    void deleteUser(Long id);
    
}
