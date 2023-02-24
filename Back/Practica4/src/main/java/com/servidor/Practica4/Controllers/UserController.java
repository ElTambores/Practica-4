package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Services.TokenService;
import com.servidor.Practica4.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class userController {
    UserService userService;
    TokenService tokenService;
    UserBuilder userBuilder = new UserBuilder();

    public userController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @CrossOrigin
    @PostMapping("/register")
    public Map<String, String> SignUp(@RequestBody SignUpForm signUpForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, String> result = new HashMap<>();

        User user = userBuilder.createUserFromForm(signUpForm);
        String message = userService.createUser(user);

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
            Map<String, Object> userMap = userBuilder.createJson(userService.getUserByEmail(email));
            String token = tokenService.newToken(email);

            result.put("token", token);
            result.put("user", userMap);
        }
        result.put("message", message);
        return result;
    }

}