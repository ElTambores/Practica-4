package com.servidor.Practica4.Utils;

import java.util.Arrays;
import java.util.List;

public class PermissionsUtils {
    public List<String> getPermissionsByRole(String role) {
        return switch (role) {
            case "admin" -> Arrays.asList("own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete",
                    "categories:write",
                    "categories:delete");
            case "user" -> Arrays.asList(
                    "own_topics:write",
                    "own_topics:delete"
            );
            case "moderator" -> Arrays.asList(
                    "own_topics:write",
                    "own_topics:delete",
                    "own_replies:write",
                    "own_replies:delete"
            );
            default -> null;
        };
    }
}
