package com.foodmobile.server.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Hash and salts a password
 */
public class PasswordHasher {
    private static String SALT = "FOOD_MOBILE-SALT-GOES-HERES1i23u1i23oi1u23u1u23u1oo32u1o3ui21u";

    /**
     * Performs the hash and salt on a specified password
     * @param password Users password
     * @return Hash and salt of the password
     */
    public static String hash(String password) {
        try {
            var saltedPassword = password + SALT;
            var hashedBytes = MessageDigest.getInstance("SHA-256").digest(saltedPassword.getBytes(Charset.forName("UTF-8")));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception ex) {
            throw new RuntimeException("This should never happen");
        }
    }
}
