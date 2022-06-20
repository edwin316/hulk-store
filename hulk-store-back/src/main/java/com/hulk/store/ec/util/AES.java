package com.hulk.store.ec.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encriptación y desencriptación
 * 
 * @author edwin
 */
public class AES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public static Integer id;
    public static Integer rolId;
    public static String tuser;
    public static String semm;
    public static boolean bdecr = false;
 
    /**
     * Init key SHA1
     * 
     * @param myKey 
     */
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Encrypt string with dymamic secret string
     * 
     * @param strToEncrypt
     * @param secret
     * @return 
     */
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException 
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    /**
     * Decrypt string, with dynamic secret string
     * 
     * @param strToDecrypt
     * @param secret
     * @return 
     */
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException 
                | IllegalBlockSizeException | NoSuchPaddingException e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    /**
     * Seem all request on life cicle
     * 
     * @param ctime Datetime
     * @return String seem
     */
    public static String seem(long ctime) {
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                        "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        double base = 3.14;
        double incr = 0.01;
        String cad = String.valueOf(ctime);
        int tstr = cad.length();
        String sem = "";
        double tot = 0;
        
        for (int i = 0; i < tstr; i += 3) {
            int fin = i + 3 > tstr - 1 ? tstr - 1 : i + 3;
            if(cad.substring(i, fin).isEmpty()) {
                continue;
            }
            int val =  Integer.valueOf(cad.substring(i, fin));
            tot += val * base;
            base += incr; 
            int sumd = 0;
            while (val != 0) {
                sumd += val % 10;
                val = val / 10;
            }  
            if(sumd > 25) {
                sumd = 25;
            }
            sem += str[sumd];
        }
        
        return String.valueOf(tot) + sem;
    }
    
    /**
     * Decode parameter
     * 
     * @param value
     * @return string decode
     */
    public static String sdecode(String value) {
        try {
            String urls = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
            return urls;
        } catch (Exception ex) {
            return value;
        }
    }
    
    /**
     * Encode parameter
     * 
     * @param value
     * @return string encode
     */
    public static String sencode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return value;
        }
    }
}

