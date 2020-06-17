package ro.ubb.server.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServerConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
