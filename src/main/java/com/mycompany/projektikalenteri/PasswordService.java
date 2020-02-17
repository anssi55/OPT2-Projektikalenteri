package com.mycompany.projektikalenteri;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordService {
    
    
    public static String generateSecurePassword(String password) {
    	return BCrypt.withDefaults().hashToString(12, password.toCharArray()); 
    }
    
    public static boolean verifyUserPassword(String providedPassword,
            String securedPassword)
    {
    	BCrypt.Result result = BCrypt.verifyer().verify(providedPassword.toCharArray(), securedPassword);
    	return result.verified;
    }
}