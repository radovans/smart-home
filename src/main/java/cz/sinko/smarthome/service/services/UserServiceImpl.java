package cz.sinko.smarthome.service.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.repository.daos.UserDao;
import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.service.dtos.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.UserDto;
import cz.sinko.smarthome.service.dtos.UserListDto;
import cz.sinko.smarthome.web.rest.exceptions.ResourceAlreadyExistsException;
import cz.sinko.smarthome.web.rest.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final OrikaBeanMapper mapper;

	@Autowired public UserServiceImpl(UserDao userDao, OrikaBeanMapper mapper) {
		this.userDao = userDao;
		this.mapper = mapper;
	}

	@Override
	public UserDto getUserById(Long id) throws ResourceNotFoundException {
		User user = userDao.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto createUser(UserCreateUpdateDto userDto) {
		User user = mapper.map(userDto, User.class);
		checkUsername(user.getUsername());
		user = userDao.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(Long id, UserCreateUpdateDto userDto) {
		User user = userDao.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		mapper.map(userDto, user);
		checkUsername(user.getUsername());
		user = userDao.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserListDto getAllUsers() {
		List<User> users = userDao.findAll();
		return new UserListDto(users.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList()));
	}

	@Override
	public void deleteUser(Long id) {
		User user = userDao.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		userDao.delete(user);
	}

	private void checkUsername(String username) {
		if (userDao.findByUsername(username) != null) {
			throw new ResourceAlreadyExistsException(String.format("User with username = %s already exists",
					username));
		}
	}

}
