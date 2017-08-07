package br.com.fedelix.jsondiff.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:datasource-config-test.properties")
@EnableJpaRepositories("br.com.fedelix.jsondiff")
@Profile("test")
public class TestPersistenceConfig extends PersistenceConfig {

    @Autowired
    private Environment env;

    @Override
    @Bean
    public DataSource buildDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("test.datasource.driver"));
        dataSource.setUrl(env.getProperty("test.datasource.url"));
        dataSource.setUsername(env.getProperty("test.datasource.username"));
        dataSource.setPassword(env.getProperty("test.datasource.password"));
        return dataSource;
    }
}
