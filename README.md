# SAES
**S-AES算法实现** 
**简介**  
根据"信息安全导论"课程第8-9次课讲述的AES算法，在课外认真阅读教科书附录D的内容，学习了解S-AES算法，并且使用Java+Swing编程实现加、解密程序  
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
明文：  
密文：  
密钥：  
经过我们的结果测试：  

另一个小组的结果测试：  

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












