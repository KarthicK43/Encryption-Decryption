import java.io.*;
import java.net.*;
import java.util.*;

public class HillR {

    public static int cofactor(int key[][], int row, int col, int n) {
        int c, m = 0, ni = 0;
        int a[][] = new int[2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != row && j != col) {
                    a[m][ni] = key[i][j];
                    ni++;
                    // System.out.println(ni);
                    if (ni == n - 1) {
                        // System.out.println("2");
                        ni = 0;
                        m++;
                    }
                }
            }
        }
        c = (int) (a[0][0] * a[1][1] - a[0][1] * a[1][0]) * (int) (Math.pow(-1, row + 1 + col + 1));
        return c;
    }

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

            int finalmat[][] = new int[n][n];
            int cofactor[][] = new int[n][n];
            int[][] keyarr = new int[n][n];
            int[][] vector = new int[(cipherText.length() / n)][n];
            int result[][] = new int[(cipherText.length() / n)][n];
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
                cofactor = keyarr;
                d = (keyarr[0][0] * keyarr[1][1]) - (keyarr[0][1] * keyarr[1][0]);
                d = (d % 26);
                System.out.println("Determinant : " + d);

            } else {

                x = (keyarr[0][0] * (keyarr[1][1] * keyarr[2][2] - keyarr[1][2] * keyarr[2][1]));
                y = (keyarr[0][1] * (keyarr[1][0] * keyarr[2][2] - keyarr[1][2] * keyarr[2][0]));
                z = (keyarr[0][2] * (keyarr[1][0] * keyarr[2][1] - keyarr[1][1] * keyarr[2][0]));
                d = x - y + z;
                d = d % 26;

                System.out.println("Determinant : " + d);

                for (i = 0; i < n; i++) {
                    for (j = 0; j < n; j++) {
                        cofactor[j][i] = cofactor(keyarr, i, j, n);
                    }
                }
            }
            int dval;
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    dval = d;
                    if (d < 0) {
                        dval = ~(dval - 1);
                        cofactor[i][j] = ~(cofactor[i][j] - 1);
                    }
                    if ((cofactor[i][j] % d) == 0) {
                        finalmat[i][j] = ((cofactor[i][j] / dval) % 26);
                        if (finalmat[i][j] < 0) {
                            finalmat[i][j] = finalmat[i][j] + 26;
                        }
                    } else {
                        int v = (cofactor[i][j] % 26);
                        for (k = 1; k <= 26; k++) {
                            int check = (v + (26 * k)) % dval;
                            if (check == 0) {
                                finalmat[i][j] = (v + (26 * k)) / dval;
                                break;
                            }
                        }
                    }
                }
            }
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    System.out.print(finalmat[i][j] + " ");
                }
                System.out.println();
            }
            for (i = 0; i < (cipherText.length() / n); i++) {
                int l = 0;
                for (j = 0; j < n; j++) {
                    for (k = 0; k < n; k++) {
                        result[i][l] += (finalmat[j][k] * vector[i][k]);

                    }
                    l++;
                }
            }
            for (i = 0; i < cipherText.length() / n; i++) {
                for (j = 0; j < n; j++) {
                    plainText += (char) (result[i][j] % 26 + 65);
                }
            }
            System.out.println("Plain Text : " + plainText);
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}