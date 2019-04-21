package exercise.config;

import exercise.orders.jdbcrepository.JdbcOrderRepository;
import exercise.orders.jdbcrepository.impl.JdbcOrderRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Clase configuracion para el uso de ModelMapper
 * @author: Azael Espinosa
 * @version: 12/10/2018/V1.0
 */

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {


    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    //Configuracion JdbcTemplate para el bean de Ordenes.
    @Bean
    public JdbcOrderRepository jdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        JdbcOrderRepositoryImpl repository = new JdbcOrderRepositoryImpl();
        repository.setJdbcTemplate(jdbcTemplate);
        return repository;
    }

}
