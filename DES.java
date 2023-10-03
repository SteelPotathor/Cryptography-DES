import java.math.BigInteger;
import java.util.Random;

public class DES {
    public static final int TAILLE_BLOC = 64;
    public static final int TAILLE_SOUS_BLOC = 32;
    public static final int NB_RONDE = 1;
    public static final int[] TAB_DECALAGE = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    public static final int[][] PERM_INITIALE = {
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7},
            {56, 48, 40, 32, 24, 16, 8, 0},
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6}
    };


    public static final int[] PC1 = new int[]
            {56, 48, 40, 32, 24, 16, 8,
                    0, 57, 49, 41, 33, 25, 17,
                    9, 1, 58, 50, 42, 34, 26,
                    18, 10, 2, 59, 51, 43, 35,
                    62, 54, 46, 38, 30, 22, 14,
                    6, 61, 53, 45, 37, 29, 21,
                    13, 5, 60, 52, 44, 36, 28,
                    20, 12, 4, 27, 19, 11, 3};

    public static final int[] PC2 = new int[]
            {13, 16, 10, 23, 0, 4,
                    2, 27, 14, 5, 20, 9,
                    22, 18, 11, 3, 25, 7,
                    15, 6, 26, 19, 12, 1,
                    40, 51, 30, 36, 46, 54,
                    29, 39, 50, 44, 32, 47,
                    43, 48, 38, 55, 33, 52,
                    45, 41, 49, 35, 28, 31};


    public static final int[][][] S = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {

                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 6, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 6}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };
    public static final int[][] E = {
            {31, 0, 1, 2, 3, 4},
            {3, 4, 5, 6, 7, 8},
            {7, 8, 9, 10, 11, 12},
            {11, 12, 13, 14, 15, 16},
            {15, 16, 17, 18, 19, 20},
            {19, 20, 21, 22, 23, 24},
            {23, 24, 25, 26, 27, 28},
            {27, 28, 29, 30, 31, 0}
    };
    public int[] masterKey;
    public int[][] tab_cles;
    public Random random = new Random();

    public DES() {
        this.masterKey = new int[64];
        for (int i = 0; i < 64; i++) {
            this.masterKey[i] = random.nextInt(0, 2);
        }
        this.tab_cles = new int[16][];
    }

    /**
     * Translate a String in a String containing only 0 and 1 (it is the binary representation of the initial String). The translation is stored character by character in an array after being converted to int.
     *
     * @param message the string to be converted
     */
    public int[] stringToBits(String message) {
        // Translate the message in binary
        String binary = new BigInteger(message.getBytes()).toString(2);

        // Store the binary representation in an array
        int[] res = new int[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            // Convert the char to int
            res[i] = Character.getNumericValue(binary.charAt(i));
        }
        return res;
    }

    /**
     * Translate an array of int containing only 0 and 1 to a String. It concatenates all the elements of the array and converts the resulting binary String to a human-readable String. In fact, it is the reverse of the previous method (stringToBits).
     *
     * @param blocs the array of int to be converted
     */
    public String bitsToString(int[] blocs) {
        // Concatenate all the elements of the array
        StringBuilder binary = new StringBuilder();
        for (int bloc : blocs) {
            binary.append((char) (bloc + '0'));
        }

        // Convert the binary String to a human-readable String
        return new String(new BigInteger(binary.toString(), 2).toByteArray());
    }

    /**
     * Returns an array of int and its length is taille, each int is unique (from 0 to taille - 1 included) and is located at a random index.
     *
     * @param taille the size of the permuted array
     */
    public int[] generePermutation(int taille) {
        // Create an array of length taille and fill it with the numbers from 0 to taille - 1 included (array[i] = i)
        int[] res = new int[taille];
        for (int i = 0; i < taille; i++) {
            res[i] = i;
        }
        // Shuffle the array (randomly permute the elements of the array)
        for (int i = 0; i < taille; i++) {
            int randomIndex = random.nextInt(taille);
            int cache = res[randomIndex];
            res[randomIndex] = res[i];
            res[i] = cache;
        }
        return res;
    }

    /**
     * Returns an array of int which is the result of the array bloc permuted according to tab_permutation.
     *
     * @param tab_permutation an array created by the method generePermutation
     * @param bloc            an array of int
     */
    public int[] permutation(int[] tab_permutation, int[] bloc) {
        int[] res = new int[tab_permutation.length];
        // Permute the elements of bloc according to tab_permutation and store the result in res
        for (int i = 0; i < tab_permutation.length; i++) {
            res[i] = bloc[tab_permutation[i]];
        }
        return res;
    }

    /**
     * Returns an array of int which is the result of the reverse of the previous method (permutation).
     *
     * @param tab_permutation an array created by the method generePermutation
     * @param bloc            an array of int
     */
    public int[] invPermutation(int[] tab_permutation, int[] bloc) {
        int[] res = new int[bloc.length];
        for (int i = 0; i < bloc.length; i++) {
            res[tab_permutation[i]] = bloc[i];
        }
        return res;
    }

    /**
     * Cut an array bloc in nbBlocs of equal size.
     *
     * @param bloc    an array of int
     * @param nbBlocs an array of int
     */
    public int[][] decoupage(int[] bloc, int nbBlocs) {
        int taille_sous_bloc = bloc.length / nbBlocs;
        int[][] res = new int[nbBlocs][taille_sous_bloc];
        for (int i = 0; i < nbBlocs; i++) {
            for (int j = 0; j < taille_sous_bloc; j++) {
                res[i][j] = bloc[i * taille_sous_bloc + j];
            }
        }
        return res;
    }

    /**
     * Returns the transformation of a 2D array to a 1D array.
     *
     * @param blocs    a 2D array of int
     */
    public int[] recollage_bloc(int[][] blocs) {
        int nb_blocs = blocs.length;
        int taille_bloc = blocs[0].length;
        int[] res = new int[nb_blocs * taille_bloc];
        for (int i = 0; i < nb_blocs; i++) {
            for (int j = 0; j < taille_bloc; j++) {
                res[i * taille_bloc + j] = blocs[i][j];
            }
        }
        return res;
    }

    public void genereMasterKey(int n) {
        int[] permutation = generePermutation(masterKey.length);
        int[] clePermute = permutation(permutation, masterKey);
        int[] res = new int[56];
        int decalle1 = 0;
        for (int i = 0; i < masterKey.length; i++) {
            if (i != 8 || i != 16 || i != 24 || i != 32 || i != 40 || i != 48 || i != 56 || i != 64) {
                res[i - decalle1] = clePermute[i];
            } else {
                decalle1++;
            }

        }
        int[][] blocs_decoupes = decoupage(res, 2);
        int[] bloc1 = decalle_gauche(blocs_decoupes[0], this.TAB_DECALAGE[n]);
        int[] bloc2 = decalle_gauche(blocs_decoupes[1], this.TAB_DECALAGE[n]);
        int[][] blocs_groupe = new int[2][];
        blocs_groupe[0] = bloc1;
        blocs_groupe[1] = bloc2;
        int[] bloc_recolle = recollage_bloc(blocs_groupe);
        int[] bloc_48_bits = new int[48];
        int decalle2 = 0;
        for (int i = 0; i < 48; i++) {
            if (i != 9 || i != 18 || i != 22 || i != 25 || i != 35 || i != 38 || i != 43 || i != 54) {
                bloc_48_bits[i - decalle2] = bloc_recolle[i];
            } else {
                decalle2++;
            }
        }
        int[] finalKey = permutation(PC2, bloc_48_bits);
        this.tab_cles[0] = finalKey;
    }

    public int[] decalle_gauche(int[] bloc, int nbCran) {
        int[] res = new int[bloc.length];
        for (int i = 0; i < bloc.length; i++) {
            res[(bloc.length + (i - nbCran)) % bloc.length] = bloc[i];
        }
        return res;
    }

    public int[] fonction_S(int[] tab) {
        // à revoir

        if (tab.length != 6) {
            System.out.print("erreur");
        }
        int noLigne = tab[0] << 1 | tab[5];
        System.out.println(noLigne);
        int noColonne = tab[1] << 3 | tab[2] << 2 | tab[3] << 1 | tab[4];
        System.out.println(noColonne);
        int number = this.S[0][noLigne][noColonne];
        System.out.println(this.S[0][noLigne][noColonne]);
        int[] res = new int[4];
        int i = 0;
        while (number > 0) {
            res[i] = number % 2;
            number /= 2;
        }
        for (int j = 0; j < 4; j++) {
            System.out.print(res[j]);
        }
        return res;
    }

    public int[] fonction_F(int[] uneCle, int[] unD) {
        return null;
    }


    /**
     * Apply the bitwise operator XOR between the two arrays, indexes by indexes. It returns a new array containing the result of the XOR.
     *
     * @param tab1 the first array to be XORed with the second one
     * @param tab2 the second array to be XORed with the first one
     */
    public int[] xor(int[] tab1, int[] tab2) {
        int[] res = new int[tab1.length];
        // XOR between the two arrays, indexes by indexes and store it in the new array
        for (int i = 0; i < tab1.length; i++) {
            // ^ is the XOR operator
            res[i] = tab1[i] ^ tab2[i];
        }
        return res;
    }

    public int[] crypte(String message_clair) {
        return null;
    }

    public String decrypte(int[] messageCodé) {
        return null;
    }

    public String affiche_tableau(int[] tableau) {
        String s = new String();
        for (int i = 0; i < tableau.length; i++) {
            s += tableau[i] + " ";
        }
        return s;
    }


    public static void main(String[] args) {
        DES d = new DES();
        // Exceptions? respect des contraintes???
        // javadoc peut remplacer la plupart des commentaires
        int nb = 10;
        int[] bloc = d.generePermutation(64);
        int taille_sous_blocs = bloc.length / nb;
        int[][] res = d.decoupage(bloc, 10);
        System.out.println(d.affiche_tableau(bloc));
        for (int i = 0; i < nb; i++) {
            for (int j = 0; j < taille_sous_blocs; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(d.fonction_S(new int[]{1, 0, 1, 0, 1, 0}));
    }
}
