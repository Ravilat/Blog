package org.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//конфигурация для подключения "внешнего" фронта с порта 3000 (правила CORS)
@Configuration
public class WebConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//      return new WebMvcConfigurer() {
//          @Override
//          public void addCorsMappings(CorsRegistry registry) {
//              registry.addMapping("/**")
//                      .allowedOrigins("http://localhost:3000")
//                      .allowedMethods("*") // можно ограничить при необходимости
//                      .allowedHeaders("*")
//                      .allowCredentials(true); // если работаешь с куками / сессиями
//          }
//      };
//
//    }
}
