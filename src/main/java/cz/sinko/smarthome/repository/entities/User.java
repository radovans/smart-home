package cz.sinko.smarthome.repository.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cz.sinko.smarthome.repository.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

	public User(@NotNull String username, @NotNull String password, @NotNull Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	@NotNull
	private String username;

	@NotNull
	private String password;

	private boolean enabled;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	private String loginToken;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
}
