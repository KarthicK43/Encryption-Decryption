import java.io.*;
import java.net.*;

public class PlayfairSender {
    public static int[] search(char s, char arr[][]) {
        if (s == 'J') {
            s = 'I';
        }
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
            Socket s = new Socket("localhost", 6666);
            DataOutputStream dou = new DataOutputStream(s.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter Message : ");
            String plainText = bf.readLine();
            System.out.println("Enter key for encryption : ");
            String key = bf.readLine();
            key = key.toUpperCase();
            char keya[] = key.toCharArray();
            String cipherText = "";
            int i, k, j, l, m, flag, n = key.length();

            char[][] keymatrix = new char[5][5];
            int len = (int) Math.ceil(plainText.length() / 2.0);
            char[][] divided = new char[len][2];
            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    keymatrix[i][j] = '?';
                }
            }
            char ch = 'A';

            plainText = plainText.toUpperCase();
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

            for (i = 0; i < 5; i++) {
                for (j = 0; j < 5; j++) {
                    System.out.print(keymatrix[i][j]);

                }
                System.out.println();
            }
            flag = 0;
            if (plainText.length() % 2 == 1) {
                plainText += 'X';
            }
            k = 0;
            System.out.println(len);
            for (i = 0; i < len; i++) {
                for (j = 0; j < 2; j++) {
                    divided[i][j] = plainText.charAt(k++);
                }
            }
            int colflag = 0;
            for (i = 0; i < len; i++) {
                colflag = 0;
                int pos[] = search(divided[i][0], keymatrix);
                for (j = 0; j < 5; j++) {
                    // System.out.println(divided[i][0] + "row" + divided[i][1]);
                    if (keymatrix[pos[0]][j] == divided[i][1]) {
                        int col = ((pos[1] + 1) + 5) % 5;
                        cipherText += keymatrix[pos[0]][col];
                        col = ((j + 1) + 5) % 5;
                        cipherText += keymatrix[pos[0]][col];
                        colflag = 1;
                        break;
                    }
                }
                if (colflag == 0) {
                    // System.out.println(divided[i][0] + "col" + divided[i][1]);
                    for (j = 0; j < 5; j++) {
                        if (keymatrix[j][pos[1]] == divided[i][1]) {
                            int col = ((pos[0] + 1) + 5) % 5;
                            cipherText += keymatrix[col][pos[1]];
                            col = ((j + 1) + 5) % 5;
                            cipherText += keymatrix[col][pos[1]];
                            colflag = 2;
                            break;
                        }
                    }
                }
                if (colflag == 0) {
                    // System.out.println(divided[i][0] + "other" + divided[i][1]);
                    int pos1[] = search(divided[i][1], keymatrix);
                    cipherText += keymatrix[pos[0]][pos1[1]];
                    cipherText += keymatrix[pos1[0]][pos[1]];
                }

            }
            cipherText += '\0';
            System.out.println("\nPlain Text : " + plainText);
            System.out.println("Cipher Text sent : " + cipherText);

            dou.writeUTF(cipherText);
            dou.writeUTF(key);
            dou.flush();
            dou.close();
            s.close();
        } catch (

        Exception e) {
            System.out.println(e);
        }
    }

}