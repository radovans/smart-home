package cz.sinko.smarthome.config.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import cz.sinko.smarthome.repository.daos.UserRepository;
import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.web.rest.exceptions.ResourceNotFoundException;

@Validated
@Service
public class JwtServiceImpl implements JwtService {

	private static final Logger LOG = LoggerFactory.getLogger(JwtServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	private enum Claims {
		ID,
		ROLE
	}

	private Algorithm algorithm;

	public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
		algorithm = Algorithm.HMAC512(secret);
	}

	@Override
	public String generateToken(@NotNull @Valid User user) {
		LOG.debug("creating token for {}", user);
		String token = JWT.create()
				.withClaim(Claims.ID.name(), user.getId() + "")
				.withClaim(Claims.ROLE.name(), user.getRole().name())
				.withExpiresAt(Date.from(
						LocalDateTime.now().plusYears(1).atZone(
								ZoneId.systemDefault()).toInstant()))
				.sign(algorithm);
		user.setLoginToken(token);
		userRepository.save(user);
		return token;
	}

	@Override
	public User parseToken(@NotNull String token) {
		LOG.debug("parsing token {}", token);
		try {
			DecodedJWT decoded = JWT.decode(token);
			Long id = decoded.getClaim(Claims.ID.name()).as(Long.class);
			User user = userRepository.findById(id).orElseThrow(() ->
					new ResourceNotFoundException(String.format("Invalid user id = %s", id)));
			if (user.getLoginToken() != null
					&& user.getLoginToken().equals(token)
					&& user.isEnabled()) {
				return user;
			} else {
				throw new ResourceNotFoundException("Invalid login token");
			}
		} catch (JWTDecodeException e) {
			LOG.error("Error while decoding token");
			return null;
		}
	}
}
