package cz.sinko.smarthome.repository.dao;

import cz.sinko.smarthome.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long>{
    
    User findByUsername(String username);
    
}
