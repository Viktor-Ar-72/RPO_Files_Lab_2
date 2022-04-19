package ru.iu3.backend.tools;

//import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;

/**
 * Класс - полезные дополнительные утилиты
 * В данном случае - кодирование HEX строк (для security)
 */
public class Utils {

    public static String ComputeHash(String pwd, String salt) {
        MessageDigest digest;

        //System.out.println("pwd - " + pwd.getBytes());
        // IntelliSence требует обёртки в виде String.wrap

        byte[] w = Hex.decode(new String(Hex.encode(pwd.getBytes())) + salt);
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (Exception error) {
            return new String();
        }

        return new String(Hex.encode(digest.digest(w)));
    }
}