package store.api.config.app;

import jakarta.activation.DataSource;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class AppBeansBootstrap {

    private final BeanFactory beanFactory;

    public AppBeansBootstrap(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    void timeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }


}