package cz.sinko.smarthome.service.services;

import cz.sinko.smarthome.service.dtos.LoginDto;
import cz.sinko.smarthome.service.dtos.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.UserDto;
import cz.sinko.smarthome.service.dtos.UserListDto;
import cz.sinko.smarthome.service.dtos.UserWithTokenDto;

public interface UserService {

	UserWithTokenDto login(LoginDto loginDto);

	UserDto createUser(UserCreateUpdateDto userDto);

	UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserCreateUpdateDto userDto);

	UserListDto getAllUsers();

	void deleteUser(Long id);

}
