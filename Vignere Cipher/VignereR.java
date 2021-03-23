import java.io.*;
import java.net.*;

import javafx.scene.effect.Light.Spot;

public class VignereR {
    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream di = new DataInputStream(s.getInputStream());
            // DataOutputStream dou = new DataOutputStream(s.getOutputStream());

            String cipherText = (String) di.readUTF();
            String key = (String) di.readUTF();
            String plainText = "";
            int i, k = 0, ch;
            for (i = 0; i < cipherText.length(); i++) {
                if (k == key.length()) {
                    k = 0;
                }
                ch = ((((((int) cipherText.charAt(i)) - 65) - (((int) key.charAt(k++)) - 65)) + 26) % 26) + 65;
                plainText += (char) ch;
            }
            System.out.println("Plain Text : " + plainText);
            ss.close();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

}
