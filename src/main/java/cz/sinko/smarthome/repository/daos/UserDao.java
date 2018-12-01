package cz.sinko.smarthome.repository.daos;

import cz.sinko.smarthome.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long>{
    
    User findByUsername(String username);
    
}
