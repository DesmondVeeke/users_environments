package coop.user.environment.userenvironment.WebConfig;

import coop.user.environment.userenvironment.Security.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add the JWT interceptor to the registry
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/api/environments/**")
                .excludePathPatterns("/api/users/login")
                .excludePathPatterns("/api/users/register");
    }
}
