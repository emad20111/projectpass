package Classes;

import javax.crypto.Cipher;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
    
private static final String SECRET = "cdbhwj38Jdbwhe23"; 
private static final SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "AES");
    


   public static String encrypt(String plainText) {
    try {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
        System.out.println(e);
        return null;
    }
}

public static String decrypt(String encryptedText) {
    try {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    } catch (Exception e) {
         System.out.println(e);
        return null;
    }
}

}
