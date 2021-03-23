import java.io.*;
import java.net.*;
// import java.lang.String.*;

public class SenderCC {
    public static void main(String args[]) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dou = new DataOutputStream(s.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            String plainText = bf.readLine();
            String cipherText = "";
            int i, k;

            plainText = plainText.toUpperCase();
            for (i = 0; i < plainText.length(); i++) {
                if (plainText.charAt(i) == ' ') {
                    continue;
                }
                k = plainText.charAt(i) + 3;
                if (k > 90) {
                    k = 65 + (k % 91);
                }
                cipherText += (char) k;
            }

            System.out.println("Encrypted Cipher Text : " + cipherText);
            dou.writeUTF(cipherText);
            dou.flush();
            dou.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
