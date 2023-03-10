package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Exceptions.*;
import com.servidor.Practica4.Forms.ChangePasswordForm;
import com.servidor.Practica4.Forms.LoginForm;
import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Forms.UpdateUserForm;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Services.TokenService;
import com.servidor.Practica4.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    UserService userService;
    TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public Map<String, Object> SignUp(@RequestBody SignUpForm signUpForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();
        String message = "";
        try {
            message = userService.createUser(signUpForm);
        } catch (EmailAlreadyExistsException e) {
            message = "This email is already registered";
            response.setStatus(400);
        }
        result.put("message", message);
        return result;
    }

    @PostMapping("/login")
    public Map<String, Object> Login(@RequestBody LoginForm loginForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();
        String message = "";
        try {
            User user = userService.login(loginForm);
            message = "done";
            result.put("token", tokenService.createUserToken(user));
            result.put("user", userService.getUserJson(user));
        } catch (EmailNotRegisteredException e) {
            response.setStatus(400);
            message = "This email is not registered";
        } catch (WrongPasswordException e) {
            response.setStatus(400);
            message = "Wrong password";
        }
        result.put("message", message);
        return result;
    }

    @GetMapping("/getprofile")
    public Object getProfile(HttpServletRequest request) {
        return request.getAttribute("user");
    }

    @PutMapping("/profile")
    public Map<String, Object> updateNameAndEmail(@RequestBody UpdateUserForm updateUserForm, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        try {
            User updatedUser = userService.updateUser(updateUserForm, request.getAttribute("user"));
            result.put("token", tokenService.createUserToken(updatedUser));
            result.put("user", userService.getUserJson(updatedUser));
        } catch (EmailAlreadyExistsException e) {
            response.setStatus(400);
            result.put("message", "Email already exists.");
        } catch (ErrorUpdatingException e) {
            response.setStatus(400);
            result.put("message", "Error updating user.");
        }
        return result;
    }

    @PutMapping("/profile/password")
    public Object updatePassword(@RequestBody ChangePasswordForm changePasswordForm, HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.updateUserPassword(changePasswordForm, request.getAttribute("user"));
            return true;
        } catch (WrongPasswordException e) {
            response.setStatus(400);
            result.put("message", "Your current password is wrong!");
        } catch (SamePasswordException e) {
            response.setStatus(400);
            result.put("message", "Your new password cannot be the same as the old password");
        } catch (ErrorUpdatingException e) {
            response.setStatus(400);
            result.put("message", "Error updating user.");
        }
        return result;
    }

}