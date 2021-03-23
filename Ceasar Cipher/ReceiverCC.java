import java.io.*;
import java.net.*;

import javafx.scene.effect.Light.Spot;

public class ReceiverCC {
    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream di = new DataInputStream(s.getInputStream());
            // DataOutputStream dou = new DataOutputStream(s.getOutputStream());

            String cipherText = (String) di.readUTF();
            String plainText = "";
            int i, k;

            for (i = 0; i < cipherText.length(); i++) {
                k = cipherText.charAt(i) - 3;
                if (k < 65) {
                    k = 91 - (65 - k);
                }
                plainText += (char) k;
            }

            System.out.println("Received Cipher Text : " + cipherText);
            System.out.println("Decrypted Plain Text : " + plainText);

            ss.close();
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
