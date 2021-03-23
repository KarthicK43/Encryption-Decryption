import java.io.*;
import java.net.*;
// import java.lang.String.*;

public class VignereS {
    public static void main(String args[]) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dou = new DataOutputStream(s.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter Plain text and key : ");
            String plainText = bf.readLine();
            String key = bf.readLine();
            String cipherText = "";
            int i, k = 0, ch;

            plainText = plainText.toUpperCase();
            key = key.toUpperCase();
            for (i = 0; i < plainText.length(); i++) {
                if (k == key.length()) {
                    k = 0;
                }
                ch = (((((int) plainText.charAt(i)) - 65) + (((int) key.charAt(k++)) - 65)) % 26) + 65;
                cipherText += (char) ch;
            }

            System.out.println("Encrypted Cipher Text : " + cipherText);
            dou.writeUTF(cipherText);
            dou.writeUTF(key);
            dou.flush();
            dou.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
