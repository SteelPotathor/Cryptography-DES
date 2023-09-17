import java.math.BigInteger;
import java.util.Random;

public class DES {
    final int taille_bloc = 64;
    final int taille_sous_bloc = 32;
    final int nb_ronde = 1;
    final int[] tab_decalage = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    final int[][] perm_initiale = {
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7},
            {56, 48, 40, 32, 24, 16, 8, 0},
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6}
    };

    //final int[][] S;
    //final int[] E;
    int[] masterKey;
    int[] tab_cles;

    public DES() {
        Random random = new Random();
        this.masterKey = new int[64];
        for (int i = 0; i < 64; i++) {
            this.masterKey[i] = random.nextInt(0, 2);
        }
        this.tab_cles = new int[16];
    }

    int[] crypte(String message_clair) {
        String binary = new BigInteger(message_clair.getBytes()).toString(2);
        int[] res = new int[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            res[i] = Character.getNumericValue(binary.charAt(i));
        }
        return res;
    }

    String decrypte(int[] messageCodé) {
        String binary = new String();
        for (int i = 0; i < messageCodé.length; i++) {
            binary += (char) (messageCodé[i] + '0');
        }
        return new String(new BigInteger(binary, 2).toByteArray());
    }

    int[] xor(int[] tab1, int[] tab2) {
        int[] res = new int[tab1.length];
        for (int i = 0; i < tab1.length; i++) {
            res[i] = tab1[i] ^ tab2[i];
        }
        return res;
    }

    public static void main(String[] args) {
        DES d = new DES();
        /*
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(d.masterKey[i + j]);
            }
            System.out.println();
        }

         */
        /*
        int[] t1 = {1, 0, 1, 1};
        int[] t2 = {1, 1, 1, 1};
        int[] res = d.xor(t1, t2);
        for (int i = 0; i < 4; i++) {
            System.out.print(res[i]);
        }

         */
        int[] t = d.crypte("Bonjour! les amis   p");
        for (int i = 0; i < t.length; i++) {
            System.out.print(t[i]);
        }

        System.out.println(d.decrypte(t));

    }
}
