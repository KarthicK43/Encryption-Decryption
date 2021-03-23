import java.io.*;
import java.net.*;

import javafx.scene.effect.Light.Spot;

public class PlayfairReceiver {
    public static int[] search(char s, char arr[][]) {
        int i, j;
        int a[] = new int[2];
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (arr[i][j] == s) {
                    a[0] = i;
                    a[1] = j;
                    i = 6;
                    break;
                }
            }
        }
        return a;
    }

    public static void main(String args[]) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream di = new DataInputStream(s.getInputStream());
            // DataOutputStream dou = new DataOutputStream(s.getOutputStream());

            String plainText = "";
            String cipherText = (String) di.readUTF();
            String key = (String) di.readUTF();
            key = key.toUpperCase();
            char keya[] = key.toCharArray();
            int i, k, j, l, m, flag, n = key.length();

            char[][] keymatrix = new char[5][5];
            int len = cipherText.length() / 2;
            char[][] divided = new char[len][2];
            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    keymatrix[i][j] = '?';
                }
            }
            char ch = 'A';
            for (i = 0; i < n; i++) {
                for (j = i + 1; j < n; j++) {
                    if (keya[i] == keya[j]) {
                        for (k = j; k < n - 1; k++) {
                            keya[k] = keya[k + 1];
                        }
                        n--;
                    }
                }
            }
            k = 0;
            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    flag = 0;
                    if (k < n) {
                        keymatrix[i][j] = keya[k++];
                    } else {
                        for (l = 0; l < 5; l++) {
                            for (m = 0; m < 5; m++) {
                                if (keymatrix[l][m] == ch) {
                                    flag = 1;
                                    l = 6;
                                    break;
                                }
                            }
                        }
                        if (flag == 0 && ch != 'J') {
                            keymatrix[i][j] = ch;
                        } else {
                            j--;
                        }
                        ch++;
                    }
                }
            }
            // for (i = 0; i < 5; i++) {
            // for (j = 0; j < 5; j++) {
            // System.out.print(keymatrix[i][j]);

            // }
            // System.out.println();
            // }

            flag = 0;
            k = 0;

            for (i = 0; i < len; i++) {
                for (j = 0; j < 2; j++) {
                    divided[i][j] = cipherText.charAt(k++);
                }
            }
            int colflag = 0;
            for (i = 0; i < len; i++) {
                colflag = 0;
                int pos[] = search(divided[i][0], keymatrix);
                for (j = 0; j < 5; j++) {
                    if (keymatrix[pos[0]][j] == divided[i][1]) {
                        int col = ((pos[1] - 1) + 5) % 5;
                        plainText += keymatrix[pos[0]][col];
                        col = ((j - 1) + 5) % 5;
                        plainText += keymatrix[pos[0]][col];
                        colflag = 1;
                        break;
                    }
                }
                if (colflag == 0) {
                    for (j = 0; j < 5; j++) {
                        if (keymatrix[j][pos[1]] == divided[i][1]) {
                            int col = ((pos[0] - 1) + 5) % 5;
                            plainText += keymatrix[col][pos[1]];
                            col = ((j - 1) + 5) % 5;
                            plainText += keymatrix[col][pos[1]];
                            colflag = 2;
                            break;
                        }
                    }
                }
                if (colflag == 0) {
                    int pos1[] = search(divided[i][1], keymatrix);
                    plainText += keymatrix[pos[0]][pos1[1]];
                    plainText += keymatrix[pos1[0]][pos[1]];
                }

            }
            System.out.println("Received Cipher Text : " + cipherText);
            System.out.println("Plain Text : " + plainText);
            s.close();

            ss.close();
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
