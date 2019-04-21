package exercise.config;

import exercise.common.exceptions.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    private final static int CONNECT_TIMEOUT = 20000;
    private final static int READ_TIMEOUT = 20000;

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setReadTimeout(READ_TIMEOUT)
                .build();
    }


    @Bean
    public RestTemplate purchaseRestTemplate(RestTemplateBuilder restTemplateBuilder, ResponseErrorHandler  responseErrorHandler) {
        return restTemplateBuilder
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setReadTimeout(READ_TIMEOUT)
                .errorHandler(responseErrorHandler)
                .rootUri(env.getProperty("purchase.url"))
                .build();
    }

    @Bean
    public ResponseErrorHandler responseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }


}
