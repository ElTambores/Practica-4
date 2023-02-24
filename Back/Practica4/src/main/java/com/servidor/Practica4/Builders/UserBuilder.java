package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Utils.HashUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    HashUtils hashUtils = new HashUtils();

    public User createUserFromForm(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        User user = new User();
        user.setEmail(signUpForm.getEmail());
        user.setName(signUpForm.getName());
        user.setPassword(hashUtils.getHashSHA256(signUpForm.getPassword()));
        user.setRole(signUpForm.getRole());
        return user;
    }

    public Map<String, Object> createJson(User user) {
        Map<String, Object> userInfo = getUserInfo(user);
        userInfo.put("permissions", getRootByRole(user.getRole()));

        return userInfo;
    }

    private Map<String, Object> getUserInfo(User user) {
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("_id", user.getId());
        userInfo.put("email", user.getEmail());
        userInfo.put("name", user.getName());
        userInfo.put("avatarUrl", "");
        userInfo.put("id", user.getId());
        userInfo.put("role", user.getRole());

        return userInfo;
    }

    public Map<String, Object> getRootByRole(String role) {
        Map<String, Object> root = new HashMap<>();
        root.put("root", getPermissionsByRole(role));
        return root;
    }

    private String[] getPermissionsByRole(String role) {
        return switch (role) {
            case "admin" -> new String[]{
                    "own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete",
                    "categories:write",
                    "categories:delete"
            };
            case "user" -> new String[]{
                    "own_topics:write",
                    "own_topics:delete",
            };
            case "moderator" -> new String[]{
                    "own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete",
            };
            default -> null;
        };
    }

}
