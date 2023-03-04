package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Exceptions.EmailAlreadyExistsException;
import com.servidor.Practica4.Exceptions.EmailNotRegisteredException;
import com.servidor.Practica4.Exceptions.WrongPasswordException;
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
        List<User> users = userRepo.findByEmailEquals(signUpForm.getEmail());
        if (users.size() == 0) {
            userRepo.save(userBuilder.fromForm(signUpForm));
            return "done";
        }
        throw new EmailAlreadyExistsException();
    }

    public String login(String email, String password) throws NoSuchAlgorithmException {
        List<User> users = userRepo.findByEmailEquals(email);
        HashUtils hashUtils = new HashUtils();
        String hashPass = hashUtils.getHashSHA256(password);
        if (users.size() == 0) throw new EmailNotRegisteredException();
        if (users.get(0).getPassword().equals(hashPass)) return "done";
        else throw new WrongPasswordException();
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepo.findByEmailEquals(email);
        return users.size() == 0 ? null : users.get(0);
    }

    public Map<String, Object> getUserJson1(String email) {
        return userBuilder.generateJsonFullInfo(getUserByEmail(email));
    }
}
