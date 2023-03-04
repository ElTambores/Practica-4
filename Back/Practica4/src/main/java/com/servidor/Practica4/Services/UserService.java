package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Exceptions.*;
import com.servidor.Practica4.Forms.ChangePasswordForm;
import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Forms.UpdateUserForm;
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
    HashUtils hashUtils = new HashUtils();

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

    public User login(String email, String password) throws NoSuchAlgorithmException {
        List<User> users = userRepo.findByEmailEquals(email);
        String hashPass = hashUtils.getHashSHA256(password);
        if (users.size() == 0) throw new EmailNotRegisteredException();
        if (users.get(0).getPassword().equals(hashPass)) return users.get(0);
        else throw new WrongPasswordException();
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepo.findByEmailEquals(email);
        return users.size() == 0 ? null : users.get(0);
    }

    public Map<String, Object> getUserJson(User user) {
        return userBuilder.generateJsonFullInfo(user);
    }

    public User updateUser(UpdateUserForm updateUserForm, Object userInfo) {
        List<User> users = userRepo.findByEmailEquals(updateUserForm.getEmail());
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        //Se comprueba que el email no esta repetido (obviando el caso del email actual del usuario)
        if (users.size() != 0 && !users.get(0).getId().equals(user.getId())) throw new EmailAlreadyExistsException();
        try {
            userRepo.updateUser(user.getId(), updateUserForm.getName(), updateUserForm.getEmail());
        } catch (Exception e) {
            throw new ErrorUpdatingException();
        }

        List<User> updatedUsers = userRepo.findByEmailEquals(updateUserForm.getEmail());
        if (updatedUsers.size() == 0) throw new ErrorUpdatingException();

        return updatedUsers.get(0);
    }

    public void updateUserPassword(ChangePasswordForm changePasswordForm, Object userInfo) throws NoSuchAlgorithmException {
        User userToken = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        User user = userRepo.getReferenceById(userToken.getId());
        if (!hashUtils.getHashSHA256(changePasswordForm.getCurrentPassword()).equals(user.getPassword())) {
            throw new WrongPasswordException();
        }
        if (hashUtils.getHashSHA256(changePasswordForm.getNewPassword()).equals(user.getPassword())) {
            throw new SamePasswordException();
        }
        try {
            userRepo.updateUserPassword(user.getId(), hashUtils.getHashSHA256(changePasswordForm.getNewPassword()));
        } catch (Exception e) {
            throw new ErrorUpdatingException();
        }
    }
}
