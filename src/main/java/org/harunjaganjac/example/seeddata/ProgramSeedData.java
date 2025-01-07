package org.harunjaganjac.example.seeddata;

import org.harunjaganjac.example.services.UserService;

public final class ProgramSeedData {
    public static void seedSuperAdmin() {
        UserService userService = new UserService();
        userService.seedSuperAdmin();
    }
}
