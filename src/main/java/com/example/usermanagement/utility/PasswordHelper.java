package com.example.usermanagement.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHelper {
    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean matchPassword(String password, String passwordToMatch) {
        return encoder.matches(password, passwordToMatch);
    }

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
}
