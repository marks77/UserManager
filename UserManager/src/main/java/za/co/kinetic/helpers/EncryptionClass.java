
package za.co.kinetic.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionClass {
    public static String encryptString(String data) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            byte[] messageDigest = msgDigest.digest(data.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashData = number.toString(16);

            while (hashData.length() < 32) {
                hashData = "0" + hashData;
            }
            return hashData;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
