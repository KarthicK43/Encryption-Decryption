import java.io.*;
import java.net.*;
import java.util.*;

public class HillS {
    public static void main(String args[]) {
        try {
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dou = new DataOutputStream(s.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            Scanner in = new Scanner(System.in);

            System.out.println("Enter Message : ");
            String plainText = bf.readLine();
            // String plainText = "friday";
            String cipherText = "";
            int i, j, k = 0, n = 3, l;
            plainText = plainText.toUpperCase();
            System.out.println("Key matrix length : ");
            n = in.nextInt();
            if (plainText.length() % n != 0) {
                if (n == 2) {
                    plainText += 'X';
                } else {
                    if (plainText.length() % n == 1) {
                        plainText += "XY";
                    } else {
                        plainText += "X";
                    }
                }
            }
            int[][] keyarr = new int[n][n];
            int[][] vector = new int[(plainText.length() / n)][n];
            int result[][] = new int[(plainText.length() / n)][n];
            for (int[] row : result) {
                Arrays.fill(row, 0);
            }
            // String key = "RRFVSVCCT";
            // String key = "HILL";
            // String key = "GYBNQKURP";
            String key = "HITD";
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    keyarr[i][j] = key.charAt(k++) % 65;
                }
            }
            k = 0;
            for (i = 0; i < plainText.length() / n; i++) {
                for (j = 0; j < n; j++) {
                    vector[i][j] = plainText.charAt(k++) % 65;
                }
            }
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    System.out.print(keyarr[i][j] + " ");
                }
                System.out.println();
            }
            for (i = 0; i < (plainText.length() / n); i++) {
                l = 0;
                for (j = 0; j < n; j++) {
                    for (k = 0; k < n; k++) {
                        result[i][l] += (keyarr[j][k] * vector[i][k]);
                    }
                    l++;
                }
            }
            for (i = 0; i < plainText.length() / n; i++) {
                for (j = 0; j < n; j++) {
                    cipherText += (char) (result[i][j] % 26 + 65);
                }
            }
            System.out.println("Cipher Text : " + cipherText);

            dou.writeUTF(cipherText);
            dou.writeUTF(key);
            dou.writeInt(n);
            dou.flush();
            dou.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}