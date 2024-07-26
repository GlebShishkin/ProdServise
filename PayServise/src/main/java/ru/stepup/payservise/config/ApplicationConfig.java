package ru.stepup.payservise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.stepup.payservise.config.properties.ApplicationProperties;

@Configuration
@EnableConfigurationProperties({
        ApplicationProperties.class,
})
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler errorHandler)   // привязываем обработчик ошибок RestTemplateResponseErrorHandler
    {
        return new RestTemplateBuilder()
                .errorHandler(errorHandler) // привяжем обработчик ошибок
                .build();
    }
}
