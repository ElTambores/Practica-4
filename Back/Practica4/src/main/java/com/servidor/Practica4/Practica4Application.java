package com.servidor.Practica4;

import com.servidor.Practica4.Interceptors.TokenInterceptor;
import com.servidor.Practica4.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Practica4Application  implements WebMvcConfigurer {

	@Autowired
	TokenInterceptor tokenInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(Practica4Application.class, args);
	}
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/getprofile")
				.addPathPatterns(("/topics"));
	}
}