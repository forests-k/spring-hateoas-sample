package jp.co.musako.application.config;

import jp.co.musako.application.filter.LoggingFilter;
import lombok.val;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Filter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean loggingFilter() {
        val bean = new FilterRegistrationBean(new LoggingFilter());
        bean.addUrlPatterns("*");
        return bean;
    }
}

