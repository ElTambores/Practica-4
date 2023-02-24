package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.UserRepo;
import com.servidor.Practica4.Utils.HashUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    UserRepo userRepo;
    UserBuilder userBuilder = new UserBuilder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String createUser(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        User user = userBuilder.fromForm(signUpForm);
        List<User> users = userRepo.findByEmailEquals(user.getEmail());
        if (users.size() == 0) {
            userRepo.save(user);
            return "done";
        }
        return "This email is already registered";
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public String login(String email, String password) throws NoSuchAlgorithmException {
        List<User> users = userRepo.findByEmailEquals(email);
        HashUtils hashUtils = new HashUtils();
        String hashPass = hashUtils.getHashSHA256(password);
        if (users.size() == 0) return "This email is not registered";
        if (users.get(0).getPassword().equals(hashPass)) return "done";
        return "Wrong password";
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepo.findByEmailEquals(email);
        return users.size() == 0 ? null : users.get(0);
    }

    public Map<String, Object> getUserJson(String email) {
        return userBuilder.generateJson(getUserByEmail(email));
    }
}
