package hello.hellospring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 허용할 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 허용할 HTTP 메소드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(false); // 자격 증명 허용 여부
    }
}
