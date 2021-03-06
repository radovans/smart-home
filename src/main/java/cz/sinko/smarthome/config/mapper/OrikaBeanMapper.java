package cz.sinko.smarthome.config.mapper;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.service.dtos.user.UserCreateUpdateDto;
import cz.sinko.smarthome.service.dtos.user.UserDto;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * Orika mapper exposed as a Spring Bean. It contains the configuration for the
 * mapper factory and factory builder. It will scan the Spring application
 * context searching for mappers and converters to register them into the
 * factory. To use it we just need to autowire it into our class.
 */
@Component
public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware {

	private MapperFactory factory;
	private ApplicationContext applicationContext;

	public OrikaBeanMapper() {
		super(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(MapperFactory factory) {
		this.factory = factory;

		factory.classMap(User.class, UserDto.class).byDefault().register();
		factory.classMap(User.class, UserCreateUpdateDto.class).byDefault().register();

		addAllSpringBeans(applicationContext);
	}

	/**
	 * Constructs and registers a {@link ClassMapBuilder} into the
	 * {@link MapperFactory} using a {@link Mapper}.
	 *
	 * @param mapper mapper
	 */
	@SuppressWarnings("rawtypes") private void addMapper(Mapper<?, ?> mapper) {
		factory.classMap(mapper.getAType(), mapper.getBType())
				.byDefault()
				.customize((Mapper) mapper)
				.register();
	}

	/**
	 * Scans the appliaction context and registers all Mappers and Converters
	 * found in it.
	 *
	 * @param applicationContext applicationContext
	 */
	@SuppressWarnings("rawtypes")
	private void addAllSpringBeans(final ApplicationContext applicationContext) {
		Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
		mappers.values().forEach(this::addMapper);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		init();
	}

}
