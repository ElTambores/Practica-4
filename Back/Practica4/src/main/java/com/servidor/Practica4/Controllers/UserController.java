package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Services.TokenService;
import com.servidor.Practica4.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    UserService userService;
    TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @CrossOrigin
    @PostMapping("/register")
    public Map<String, Object> SignUp(@RequestBody SignUpForm signUpForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();

        String message = userService.createUser(signUpForm);

        if (!message.equals("done")) response.setStatus(400);

        result.put("message", message);
        return result;
    }

    @CrossOrigin
    @PostMapping("/login")
    public Map<String, Object> Login(@RequestBody SignUpForm signUpForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();
        String email = signUpForm.getEmail();
        String password = signUpForm.getPassword();

        String message = userService.login(email, password);

        if (!message.equals("done")) response.setStatus(400);
        else {
            Map<String, Object> userMap = userService.getUserJson(email);
            String token = tokenService.createUserToken(email);

            result.put("token", token);
            result.put("user", userMap);
        }
        result.put("message", message);
        return result;
    }

    @CrossOrigin
    @GetMapping("/getprofile")
    public Object getProfile(HttpServletRequest request) {
        return request.getAttribute("user");

    }
}
