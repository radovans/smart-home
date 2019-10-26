package cz.sinko.smarthome.service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.config.security.JwtService;
import cz.sinko.smarthome.repository.daos.UserRepository;
import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.service.dtos.LoginDto;
import cz.sinko.smarthome.service.dtos.user.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.user.UserDto;
import cz.sinko.smarthome.service.dtos.user.UserListDto;
import cz.sinko.smarthome.service.dtos.user.UserWithTokenDto;
import cz.sinko.smarthome.web.rest.exceptions.ResourceAlreadyExistsException;
import cz.sinko.smarthome.web.rest.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final OrikaBeanMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Autowired public UserServiceImpl(UserRepository userRepository, OrikaBeanMapper mapper, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@Override
	public UserWithTokenDto login(LoginDto loginDto) {
		log.info("Logging in user with username " + loginDto.getUsername());
		Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());
		if (!userOptional.isPresent()) {
			throw new ResourceNotFoundException("User does not exists");
		}
		User user = userOptional.get();
		if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			String loginToken = jwtService.generateToken(user);
			UserWithTokenDto userWithTokenDto = mapper.map(user, UserWithTokenDto.class);
			userWithTokenDto.setLoginToken(loginToken);
			return userWithTokenDto;
		} else {
			throw new BadCredentialsException("Invalid password");
		}
	}

	@Override
	public UserDto getUserById(Long id) throws ResourceNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto createUser(UserCreateUpdateDto userDto) {
		User user = mapper.map(userDto, User.class);
		checkUsername(user.getUsername());
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(Long id, UserCreateUpdateDto userDto) {
		User user = userRepository.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		mapper.map(userDto, user);
		checkUsername(user.getUsername());
		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserListDto getAllUsers() {
		List<User> users = userRepository.findAll();
		return new UserListDto(users.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList()));
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() ->
				new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
		userRepository.delete(user);
	}

	private void checkUsername(String username) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new ResourceAlreadyExistsException(String.format("User with username = %s already exists",
					username));
		}
	}

}
