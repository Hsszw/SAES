import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class SAES_2 {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为32位
        if (sKey.length() != 32) {
            System.out.print("Key长度不是32位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec1 = new SecretKeySpec(raw, 0, 16, "AES");
        SecretKeySpec skeySpec2 = new SecretKeySpec(raw, 16, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec1);
        byte[] encrypted1 = cipher.doFinal(sSrc.getBytes("utf-8"));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec2);
        byte[] encrypted2 = cipher.doFinal(encrypted1);
        return new BASE64Encoder().encode(encrypted2);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为32位
            if (sKey.length() != 32) {
                System.out.print("Key长度不是32位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec1 = new SecretKeySpec(raw, 0, 16, "AES");
            SecretKeySpec skeySpec2 = new SecretKeySpec(raw, 16, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec2);
            byte[] decrypted1 = cipher.doFinal(new BASE64Decoder().decodeBuffer(sSrc));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec1);
            byte[] decrypted2 = cipher.doFinal(decrypted1);
            return new String(decrypted2, "utf-8");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "jkl;POIU1234++==jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = "www.gowhere.so";
        System.out.println(cSrc);
        // 加密
        String enString = SAES_2.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = SAES_2.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
}
