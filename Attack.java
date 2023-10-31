public class Attack {
    public static void main(String[] args) {
        String plaintext = "www.gowhere.so"; // 要加密的明文
        String ciphertext = "Oa1NPBSarXrPH8wqSRhh3g==";//要解密的密文
        // 穷举16位密钥k1
        for (int k1 = 0; k1 <= 65535; k1++) {
            String k1Str = Integer.toBinaryString(k1);
            while (k1Str.length() < 16) {
                k1Str = "0" + k1Str;
            }

            try {
                String ciph_mid = SAES.Encrypt(plaintext, k1Str); // 使用k1加密明文得到密文m

                // 穷举16位密钥k2
                for (int k2 = 0; k2 <= 65535; k2++) {
                    String k2Str = Integer.toBinaryString(k2);
                    while (k2Str.length() < 16) {
                        k2Str = "0" + k2Str;
                    }

                    try {
                        String dec_mid = SAES.Decrypt(ciphertext, k2Str); // 使用k2解密密文得到明文n

                        if (dec_mid.equals(ciph_mid)) {
                            String key = k1Str + k2Str; // 组成K（k1+k2）
                            System.out.println("Found key: " + key);
                            return; // 找到满足条件的k1和k2，结束穷举
                        }
                    } catch (Exception e) {
                        // 解密过程中出现异常，继续穷举下一个k2
                    }
                }
            } catch (Exception e) {
                // 加密过程中出现异常，继续穷举下一个k1
            }
        }

        System.out.println("No matching key found.");
    }
}