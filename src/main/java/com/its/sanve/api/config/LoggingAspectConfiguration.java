
package com.its.sanve.api.config;


import com.its.sanve.api.aop.MonitorAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;


@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    public MonitorAspect monitorAspect() {
        return new MonitorAspect();
    }
}
