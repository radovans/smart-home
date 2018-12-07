package cz.sinko.smarthome.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.sinko.smarthome.repository.daos.UserDao;
import cz.sinko.smarthome.repository.entities.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	@Autowired public MyUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(user);
	}
}