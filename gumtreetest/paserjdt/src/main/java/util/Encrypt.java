package util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.security.interfaces.RSAKey;

import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Created with IntelliJ IDEA.
 * User: shi
 * Date: 12/29/17
 * Time: 9:53 PM
 * Description:
 */
public class Encrypt {
    public static void main(String[] args) {
        String key = "123ccc122333";
        String output = encrpyt("helcccloword", key);
        System.out.println(output);
        String decrypt = decrypt(output, key);
        System.out.println(decrypt);
    }
    public static String encrpyt(String input, String key) {
        byte[] result = encrypt(input, key);
        return new String(result);
    }

    public static String decrypt(String input, String key) {
        if (input == null) {
            return null;
        }
        String result = decrypt(input.getBytes(), key);
        return result;
    }

    /**
     * 加密
     *
     * @param content
     *            待加密内容
     * @param key
     *            加密的密钥
     * @return
     */
    public static byte[] encrypt(String content, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(ENCRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(content.getBytes());
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content
     *            待解密内容
     * @param key
     *            解密的密钥
     * @return
     */
    public static String decrypt(byte[] content, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
