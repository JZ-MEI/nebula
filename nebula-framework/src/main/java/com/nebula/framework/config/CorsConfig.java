//package com.mjz.framework.config;
//
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 允许跨域请求携带 Cookie
//        config.addAllowedOrigin("*"); // 允许所有域名访问，生产环境应根据实际情况设置允许的域名
//        config.addAllowedHeader("*"); // 允许所有请求头
//        config.addAllowedMethod("*"); // 允许所有请求方法
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}