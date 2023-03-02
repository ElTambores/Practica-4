package com.servidor.Practica4.Builders;

import com.auth0.jwt.interfaces.Claim;
import com.servidor.Practica4.Forms.SignUpForm;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Utils.HashUtils;
import com.servidor.Practica4.Utils.PermissionsUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    HashUtils hashUtils = new HashUtils();

    public User fromForm(SignUpForm signUpForm) throws NoSuchAlgorithmException {
        User user = new User();
        user.setEmail(signUpForm.getEmail());
        user.setName(signUpForm.getName());
        user.setPassword(hashUtils.getHashSHA256(signUpForm.getPassword()));
        user.setRole(signUpForm.getRole());
        user.setAvatarUrl("");
        user.set__v("0");
        return user;
    }

    public Map<String, Object> generateJson(User user) {
        Map<String, Object> userInfo = getUserInfo(user);
        Map<String, Object> permissions = getPermissions(user.getRole());
        userInfo.put("permissions", permissions);

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
        userInfo.put("__v", user.get__v());

        return userInfo;
    }

    public Map<String, Object> getPermissions(String role) {
        Map<String, Object> root = new HashMap<>();
        PermissionsUtils permissionsUtils = new PermissionsUtils();
        root.put("root", permissionsUtils.getPermissionsByRole(role));
        String[] categories = new String[0];
        root.put("categories", categories);
        return root;
    }

    public Map<String, Object> fromToken(Map<String, Claim> tokenInfo) {
        User user = new User();
        String role = (tokenInfo.get("role")).toString();
        String email = tokenInfo.get("email").toString();
        String name = tokenInfo.get("name").toString();
        String avatarUrl = tokenInfo.get("avatarUrl").toString();

        user.setRole(role.substring(1, role.length() - 1));
        user.setId(Long.parseLong(tokenInfo.get("_id").toString()));
        user.setEmail(email.substring(1, email.length() - 1));
        user.setName(name.substring(1, name.length() - 1));
        user.set__v("0");
        user.setAvatarUrl(avatarUrl);

        return generateJson(user);
    }

    public User fromUserInfo(Map<String, Object> tokenInfo) {
        User user = new User();
        String role = (tokenInfo.get("role")).toString();
        String email = tokenInfo.get("email").toString();
        String name = tokenInfo.get("name").toString();
        String avatarUrl = tokenInfo.get("avatarUrl").toString();
        String __v = tokenInfo.get("__v").toString();

        user.setRole(role);
        user.setId(Long.parseLong(tokenInfo.get("_id").toString()));
        user.setEmail(email);
        user.setName(name);
        user.set__v(__v);
        user.setAvatarUrl(avatarUrl);

        return user;
    }
}
