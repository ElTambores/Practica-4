package com.example.autentificacionSegundaExplicacion.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.autentificacionSegundaExplicacion.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.SignatureException;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    TokenService tokenService;

    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        try {
            String token = authHeader.replace("Bearer ", "");
            request.setAttribute("userName", tokenService.getUserFromToken(token));
            return true;
        }catch (SignatureVerificationException signatureVerificationException){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
