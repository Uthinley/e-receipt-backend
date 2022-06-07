package bt.org.dsp.crst.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class WebConfig implements WebMvcConfigurer {

    @Value("${endpointUrl}")
    private String endpointUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(endpointUrl + "/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedOrigins(endpointUrl)
                .allowCredentials(true);
    }
}
