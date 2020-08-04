package com.its.sanve.api.config;

import com.its.sanve.api.interceptors.LoginInterceptor;
import com.its.sanve.api.interceptors.GwInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Value("${application.allowed.methods}")
    private String[] allowMethods;

    @Value("${application.allowed.origins}")
    private String[] allowOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(allowMethods)
                .allowedOrigins(allowOrigins);
    }

    @Autowired
    private GwInterceptor interceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/sanve/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
