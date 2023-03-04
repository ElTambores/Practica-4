package com.servidor.Practica4.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.UserRepo;
import com.servidor.Practica4.Utils.PermissionsUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TokenService {
    @Value("${tokenSecret}")
    String tokenSecret;

    @Value("${expTokenTime}")
    int expTokenTime;

    UserRepo userRepo;

    public TokenService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String createUserToken(User user) {
        PermissionsUtils permissionsUtils = new PermissionsUtils();
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("root", permissionsUtils.getPermissionsByRole((user.getRole())));
        Date expDate = new Date(System.currentTimeMillis() + expTokenTime);
        return JWT.create()
                .withClaim("role", user.getRole())
                .withClaim("_id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("name", user.getName())
                .withClaim("__v", user.get__v())
                .withClaim("avatarUrl", user.getAvatarUrl())
                .withClaim("id", user.getId())
                .withClaim("permissions", permissions)
                .withExpiresAt(expDate)
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
    }

    public Map<String, Object> getUserFromToken(String token) {
        if (token.equals("null")) return null;
        UserBuilder userBuilder = new UserBuilder();
        Map<String, Claim> tokenInfo = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getClaims();
        long iat = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes())).build().verify(token).getExpiresAt().getTime();
        return userBuilder.fromToken(tokenInfo, iat);
    }
}
