import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SAESGUI {

    private static JTextArea textArea;
    private static JTextField inputField;
    private static JTextField keyField;

    public static void main(String[] args) {
        JFrame frame = new JFrame("S-AES GUI");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel inputLabel = new JLabel("输入要加密/解密的文本:");
        inputLabel.setBounds(10, 20, 200, 25);
        panel.add(inputLabel);

        inputField = new JTextField(20);
        inputField.setBounds(200, 20, 165, 25);
        panel.add(inputField);

        JLabel keyLabel = new JLabel("输入16位密钥:");
        keyLabel.setBounds(10, 50, 200, 25);
        panel.add(keyLabel);

        keyField = new JTextField(20);
        keyField.setBounds(200, 50, 165, 25);
        panel.add(keyField);

        JButton encryptButton = new JButton("加密");
        encryptButton.setBounds(50, 80, 80, 25);
        panel.add(encryptButton);

        JButton decryptButton = new JButton("解密");
        decryptButton.setBounds(200, 80, 80, 25);
        panel.add(decryptButton);

        textArea = new JTextArea();
        textArea.setBounds(10, 110, 380, 140);
        panel.add(textArea);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String key = keyField.getText();
                try {
                    String encryptedText = SAES.Encrypt(input, key);
                    textArea.setText("加密后的文本是：" + encryptedText);
                } catch (Exception exception) {
                    textArea.setText("加密出现错误：" + exception.getMessage());
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String key = keyField.getText();
                try {
                    String decryptedText = SAES.Decrypt(input, key);
                    textArea.setText("解密后的文本是：" + decryptedText);
                } catch (Exception exception) {
                    textArea.setText("解密出现错误：" + exception.getMessage());
                }
            }
        });
    }
}