import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SAES_3 {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为48位
        if (sKey.length() != 48) {
            System.out.print("Key长度不是48位");
            return null;
        }
        byte[] rawKey = sKey.getBytes(StandardCharsets.UTF_8);

        // 分割成三个16字节的子密钥
        byte[] key1 = new byte[16];
        byte[] key2 = new byte[16];
        byte[] key3 = new byte[16];

        System.arraycopy(rawKey, 0, key1, 0, 16);
        System.arraycopy(rawKey, 16, key2, 0, 16);
        System.arraycopy(rawKey, 32, key3, 0, 16);

        // 使用AES算法进行三次加密
        byte[] encrypted = sSrc.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 第一次加密
        SecretKeySpec skeySpec1 = new SecretKeySpec(key1, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec1);
        encrypted = cipher.doFinal(encrypted);

        // 第二次加密
        SecretKeySpec skeySpec2 = new SecretKeySpec(key2, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec2);
        encrypted = cipher.doFinal(encrypted);

        // 第三次加密
        SecretKeySpec skeySpec3 = new SecretKeySpec(key3, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec3);
        encrypted = cipher.doFinal(encrypted);

        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为48位
            if (sKey.length() != 48) {
                System.out.print("Key长度不是48位");
                return null;
            }
            byte[] rawKey = sKey.getBytes(StandardCharsets.UTF_8);

            // 分割成三个16字节的子密钥
            byte[] key1 = new byte[16];
            byte[] key2 = new byte[16];
            byte[] key3 = new byte[16];

            System.arraycopy(rawKey, 0, key1, 0, 16);
            System.arraycopy(rawKey, 16, key2, 0, 16);
            System.arraycopy(rawKey, 32, key3, 0, 16);

            // 使用AES算法进行三次解密
            byte[] decrypted = Base64.getDecoder().decode(sSrc);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            // 第三次解密
            SecretKeySpec skeySpec3 = new SecretKeySpec(key3, "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec3);
            decrypted = cipher.doFinal(decrypted);

            // 第二次解密
            SecretKeySpec skeySpec2 = new SecretKeySpec(key2, "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec2);
            decrypted = cipher.doFinal(decrypted);

            // 第一次解密
            SecretKeySpec skeySpec1 = new SecretKeySpec(key1, "AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec1);
            decrypted = cipher.doFinal(decrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为48位。
         */
        String cKey = "jkl;POIU1234++==jkl;POIU1234++==jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = "www.gowhere.so";
        System.out.println(cSrc);
        // 加密
        String enString = SAES_3.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = SAES_3.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
}
