package store.api.config.jpa;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration 
@EnableTransactionManagement
@EnableJpaRepositories(
        enableDefaultTransactions = false,
        basePackages = {"store.api.domain", "store.api.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
) 
public class JpaConfig {


    @Bean(name = "espinosaDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource  espinosaDs() {
    	 return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jornadaEntityManagerFactory(@Qualifier("espinosaDataSource") DataSource espinosaDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(espinosaDataSource);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        EnableJpaRepositories enableJpaRepositories = getClass().getSuperclass().getAnnotation(EnableJpaRepositories.class);
        if (enableJpaRepositories != null) {
            em.setPackagesToScan(enableJpaRepositories.basePackages());
        }
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        em.setJpaProperties(jpaProperties);
        return em;
    } 

    @Bean(name = "transactionManager")
    public PlatformTransactionManager jornadaTransactionManager( LocalContainerEntityManagerFactoryBean jornadaEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(jornadaEntityManagerFactory.getObject()));
    }
}
