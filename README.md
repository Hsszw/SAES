# SAES
**S-AES算法实现**   
**简介**  
根据"信息安全导论"课程第8-9次课讲述的AES算法，在课外认真阅读教科书附录D的内容，学习了解S-AES算法，并且使用Java+Swing编程实现加、解密程序并且我们还通过BASE64编码将二进制的密文转换成了字符串形式进行存储和传输。  
**第一关：基本测试**  
提供GUI界面供用户交互，输入为16bit数据和密钥，输出为16bit的明文。  
输入的明文为：1011001101001101  
输入的密钥为：1001101010100010  
得出的明文为：lwdzNR1GoRY8w//EanCNKXcuCvanSO1nrGIdFCDWCLs=  
加密：对明文进行加密  
![image](https://github.com/Hsszw/SAES/assets/147220550/9c364e32-4f77-4f64-8fca-1cdc162ee35a)  
解密：将得到的密文使用相同的密钥进行解密。  

![image](https://github.com/Hsszw/SAES/assets/147220550/0a3829a2-ce95-4b03-b6c2-82e1821b9eec)  
可以看出经过该程序进行加密后得出的密文通过解密得到的明文与原明文相同。  
**第二关：交叉测试**    
我们设置一对明密文对。  
明文：0110111101101011  
密钥：1010011100111011  
密文：1111000110000110    
经过我们的结果测试：  
![image](https://github.com/Hsszw/SAES/assets/147220550/629a92aa-2503-4c3f-87dc-5aa246be57ab)  


另一个小组的结果测试：  
![image](https://github.com/Hsszw/SAES/assets/147220550/3413b6e7-eb1d-4b34-85ba-cff158ee33be)  

我们发现我们的结果相匹配  
**第三关：扩展功能**    
数据输入可以是ASII编码字符串(分组为2 Bytes)，对应地输出也可以是ACII字符串：  
明文：AB  
密钥：1001101010010110  
密文：KdyTMRTalT5o5MmWskqaHw==  
进行加密：  
![image](https://github.com/Hsszw/SAES/assets/147220550/6b091c84-12c6-499d-b689-2d75590daf42)  
进行解密：  
![image](https://github.com/Hsszw/SAES/assets/147220550/49b0012e-1b0f-4de9-894b-9ddaff1832ff)  
可以看到，该程序输入可以是ASII编码字符串(分组为2 Bytes)，对应地输出也可以是ACII字符串。且经过加解密得出的明文和密文相匹配。  
**第四关：多重加密。**
**4.1 双重加密：**
分组长度仍然是16 bits，但密钥长度为32 bits。  
明文：1001100110011101  
密钥：10011111010110100110011001100110  
密文：LuktcZdJ6uy+wRCRG0SiiLVkpPxKdUiJ8ZM0x1MpTqieYC7gGq7hOV68b5E2R4+z  
得出的结果如图：  
![image](https://github.com/Hsszw/SAES/assets/147220550/855783ac-d8e5-4bae-989a-1255da204f37)  
解密：对密文进行解密。  
![image](https://github.com/Hsszw/SAES/assets/147220550/436a54c1-7f05-470e-baef-85b2b90f01fc)  
**4.2 中间相遇攻击**  
找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用中间相遇攻击的方法找到正确的密钥Key(K1+K2)。   

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
使用上述中间相遇攻击算法，在已知一对或多对明密文对的情况下，可调用S-AES加解密算法模块进行中间相遇暴力破解。  
假设已知对应的明文p与密文c，使用加密算法与循环测试16bit所有密钥加密得到中间密文mid_c,再使用解密算法解密密文c得到中间明文mid_p。若mid_c=mid_p时，将进行加密与解密的密钥k1,k2组成K（k1+k2），即可中间相遇攻击得到密钥K。  
代码运行结果展示，最终结果显示加密程度高，难以在短时间内暴力破解。  
![Uploading image.png…]()    
![Uploading image.png…]()  




**4.3 三重加密**
将S-AES算法通过三重加密进行扩展，选择使用48bits(K1+K2+K3)的模式进行三重加解密。  
明文：1001101101101010  
密钥：100110011101101101011100011001100110011001101010  
密文：  
EpXGhlSAy1XCHzNK75MT2Bqjbk5cglHFKtEhTtE7Wu2ti2W5xVc7mRcDH/XVJmlwpMPTGd0TxrL2Jr/Udubllw==  
加密过程：  
![image](https://github.com/Hsszw/SAES/assets/147220550/60e869f8-b51f-4359-8647-c3e11f1bc98b)  
解密：将得到的密文经过解密得出。  
![image](https://github.com/Hsszw/SAES/assets/147220550/cb39517e-fccc-49d1-b0ad-97fe863c7e51)  
可以发现得出的明密文对相匹配。  
**第5关：工作模式**  
基于S-AES算法，使用密码分组链(CBC)模式对较长的明文消息进行加密。注意初始向量(16 bits) 的生成，并需要加解密双方共享。  
在CBC模式下进行加密，并尝试对密文分组进行替换或修改，然后进行解密，请对比篡改密文前后的解密结果。
设置： 
明文：1001100011001100  
密钥：100110101001011001101000001010110101101010101100  
初始向量：1001100111100100  
密文：  
EK3bh2n3dxXW3mOsFd1K/i4jkeBhf+iy39iP9GYgTo6arGAOzz9YtOyBFmVhdofBKZVPdE6wBMgCZeOZzlnXRQ==  
通过CBC模式进行加密：  
![image](https://github.com/Hsszw/SAES/assets/147220550/6e658dd3-d317-4539-aa90-53bb924990ad)  
尝试对密文分组进行篡改和替换进，进行解密。  
修改后的密文：  
EK5dhjn3dxXW3mOsFd1K/i4jkeBhf+iy39iP9GYgTo6arGAOzz9YtOyBFmVhdofBKZVPdE6wBMgCZeOZsdnXRQ==  
![image](https://github.com/Hsszw/SAES/assets/147220550/f9946aca-50a6-4746-ae07-5233b207b4c6)  
发现解密后的结果不匹配，可以知道该模式下的加密比较安全。  














