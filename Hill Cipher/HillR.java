import java.io.*;
import java.net.*;
import java.util.*;

public class HillR {
    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream di = new DataInputStream(s.getInputStream());
            // DataOutputStream dou = new DataOutputStream(s.getOutputStream());

            String cipherText = di.readUTF();
            String key = di.readUTF();
            String plainText = "";
            int n = di.readInt();
            int i, j, k = 0, d;
            int x, y, z;

            int[][] keyarr = new int[n][n];
            int[][] vector = new int[(cipherText.length() / n)][n];
            int result[][] = new int[(cipherText.length() / n)][n];
            int det[] = new int[3];
            for (int[] row : result) {
                Arrays.fill(row, 0);
            }
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    keyarr[i][j] = key.charAt(k++) % 65;
                }
            }
            k = 0;
            for (i = 0; i < cipherText.length() / n; i++) {
                for (j = 0; j < n; j++) {
                    vector[i][j] = cipherText.charAt(k++) % 65;
                }
            }
            if (n == 2) {
                keyarr[0][0] = keyarr[0][0] + keyarr[1][1] - (keyarr[1][1] = keyarr[0][0]);
                if (keyarr[0][1] < 0) {
                    keyarr[0][1] = Math.abs(keyarr[0][1]);
                } else {
                    keyarr[0][1] = (~(keyarr[0][1] - 1));
                }
                if (keyarr[1][0] < 0) {
                    keyarr[1][0] = Math.abs(keyarr[0][1]);
                } else {
                    keyarr[1][0] = (~(keyarr[1][0] - 1));
                }
                d = (keyarr[0][0] * keyarr[1][1]) - (keyarr[0][1] * keyarr[1][0]);
                d = d % 26;
            } else {
                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++) {
                        for (k = 0; k < n; k++) {
                            for (l = 0; l < n; l++) {
                                if (k != i && j != l) {
                                    temp[m1][m2++] = keyarr[k][l];
                                }
                            }
                            m1++;
                        }
                    }
                }
                x = (keyarr[0][0] * (keyarr[1][1] * keyarr[2][2] - keyarr[1][2] * keyarr[2][1]));
                y = (keyarr[0][1] * (keyarr[1][0] * keyarr[2][2] - keyarr[1][2] * keyarr[2][0]));
                z = (keyarr[0][2] * (keyarr[1][0] * keyarr[2][1] - keyarr[1][1] * keyarr[2][0]));
                d = x - y + z;
                d = d % 26;
            }
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}