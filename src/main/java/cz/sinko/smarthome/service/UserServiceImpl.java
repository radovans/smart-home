package cz.sinko.smarthome.service;

import cz.sinko.smarthome.config.mapper.OrikaBeanMapper;
import cz.sinko.smarthome.repository.dao.UserDao;
import cz.sinko.smarthome.repository.entity.User;
import cz.sinko.smarthome.service.dto.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dto.UserDto;
import cz.sinko.smarthome.service.dto.UserListDto;
import cz.sinko.smarthome.web.rest.exception.ResourceAlreadyExistsException;
import cz.sinko.smarthome.web.rest.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private OrikaBeanMapper mapper;
    
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
            throw new ResourceAlreadyExistsException(String.format("User with username = %s already exists", username));
        }
    }

}
