package com.xaut.web.config;

import com.xaut.web.interceptor.AuthorizationInterceptor;
import com.xaut.web.interceptor.TokenInterceptor;
import com.xaut.web.interceptor.WebRequestInterceptor;
import com.xaut.web.resolver.CurrentUserResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Author ： Wangzhe
 * Date : 2017/12/25
 * Description : 配置项目的拦截器
 * Version : 0.1
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
    @Bean
    public WebRequestInterceptor webRequestInterceptor(){return new WebRequestInterceptor();}
    @Bean
    public AuthorizationInterceptor authorizationInterceptor(){return new AuthorizationInterceptor();}
    @Bean
    public CurrentUserResolver currentUserResolver(){return new CurrentUserResolver();}

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/*/**").
//                excludePathPatterns("/user/v1/login");
//        registry.addInterceptor(webRequestInterceptor()).addPathPatterns("/*/**");
//        super.addInterceptors(registry);
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/*/**");
        super.addInterceptors(registry);
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/internal/**").addResourceLocations("classPath:/");
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(currentUserResolver());
    }
}
