import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class DES {
    public static final int TAILLE_BLOC = 64;
    public static final int TAILLE_SOUS_BLOC = 32;
    public static final int NB_RONDE = 16;
    public static final int[] TAB_DECALAGE = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };
    public static final int[] PERM_INITIALE = {
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7,
            56, 48, 40, 32, 24, 16, 8, 0,
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6
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
    public static final int[] E = {
            31, 0, 1, 2, 3, 4,
            3, 4, 5, 6, 7, 8,
            7, 8, 9, 10, 11, 12,
            11, 12, 13, 14, 15, 16,
            15, 16, 17, 18, 19, 20,
            19, 20, 21, 22, 23, 24,
            23, 24, 25, 26, 27, 28,
            27, 28, 29, 30, 31, 0
    };

    public static final int[] P = {
            15, 6, 19, 20, 28, 11, 27, 16,
            0, 14, 22, 25, 4, 17, 30, 9,
            1, 7, 23, 13, 31, 26, 2, 8,
            18, 12, 29, 5, 21, 10, 3, 24
    };
    public int[] masterKey;
    public int[][] tab_cles;
    public Random random = new Random();


    /**
     * Constructor of the class DES. It generates a random master key of 64 bits (containing only 0 or 1). It also creates an array (tab_cles) of 16 sub-keys of 48 bits each.
     */
    public DES() {
        // Create a random master key of 64 bits
        this.masterKey = new int[64];
        // Fill each index of the master key with 0 or 1
        for (int i = 0; i < 64; i++) {
            this.masterKey[i] = random.nextInt(0, 2);
        }
        // Create an array to store keys
        this.tab_cles = new int[16][48];
        // Generate the 16 sub-keys
        for (int i = 0; i < 16; i++) {
            genereCle(i);
        }
    }

    /**
     * Translate a String in a String containing only 0 and 1 (it is the binary representation of the initial String). The translation is stored character by character in an array after being converted to int.
     *
     * @param message the string to be converted.
     * @return an array of int containing only 0 and 1 (it is the binary representation of the initial String).
     * @throws NumberFormatException if message is an empty string.
     * @throws NullPointerException  if message is null.
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
     * @param blocs the array of int to be converted into String.
     * @return a String translation of the array blocs (binary) in human-readable format.
     * @throws NumberFormatException if blocs is an array of length 0.
     * @throws NullPointerException  if blocs is null.
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
     * DEVIENT GENEREMASTERKEY mais garder celle ci et creer une autre methode
     * Create an array of length taille and fill it with the numbers from 0 to taille - 1 included (array[i] = i). Then, it randomly permutes the elements of the array and returns it.
     *
     * @param taille the size of the permuted array.
     * @return an array of int (length = taille), each int is unique (from 0 to taille - 1 both included) and is located at a random index.
     * @throws IllegalArgumentException if taille is negative.
     */
    public int[] generePermutation(int taille) {
        // Preconditions
        if (taille < 0) {
            throw new IllegalArgumentException("The size of the array must be positive: " + taille + " < 0.");
        }
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
     * Shuffle bloc according to tab_permutation (The element at the index i of bloc will be moved to the index tab_permutation[i] of the returning array). The two arrays must be of equal size.
     *
     * @param tab_permutation the array created by the method generePermutation.
     * @param bloc            the array of int to be permuted.
     * @return an array of int resulting from the permutation of the elements of the array bloc according to tab_permutation.
     * @throws NullPointerException if tab_permutation or bloc is null.
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
     * The reverse of the previous method (permutation). It cancels the permutation of the array bloc according to tab_permutation. The two arrays must be of equal size.
     *
     * @param tab_permutation the array created by the method generePermutation.
     * @param bloc            the array of int to be reverse-permuted.
     * @return an array where the permutation has been reversed.
     * @throws ArrayIndexOutOfBoundsException if tab_permutation[i] >= bloc.length || tab_permutation[i] < 0.
     * @throws NullPointerException           if tab_permutation or bloc is null.
     * @throws IllegalArgumentException       if tab_permutation.length != bloc.length.
     */
    public int[] invPermutation(int[] tab_permutation, int[] bloc) {
        // Preconditions
        if (tab_permutation.length != bloc.length) {
            throw new IllegalArgumentException("The length of tab_permutation must be equal to the length of bloc: " + tab_permutation.length + " != " + bloc.length + ".");
        }

        int[] res = new int[bloc.length];
        // Reverse-permute the elements of bloc according to tab_permutation and store the result in res
        for (int i = 0; i < bloc.length; i++) {
            res[tab_permutation[i]] = bloc[i];
        }
        return res;
    }

    /**
     * Cut an array bloc in nbBlocs of "equal size" and store sub-blocs in a 2D array. This method will only be used if nbBlocs is a multiple of bloc.length. Moreover, nbBlocs must be positive and less than the length of bloc.
     *
     * @param bloc        the array of int to be cut.
     * @param tailleBlocs the length of the sub-blocs.
     * @return a 2D array of int containing nbBlocs sub-blocs of equal size (tailleBlocs) such that the index i of the 2D array represents the sub-bloc i.
     * @throws IllegalArgumentException if tailleBlocs <= 0.
     */
    public int[][] decoupage(int[] bloc, int tailleBlocs) {
        // Preconditions
        if (tailleBlocs <= 0) {
            throw new IllegalArgumentException("The length of blocs must be positive: " + tailleBlocs + " <= " + 0 + ".");
        }
        int nbBlocs = bloc.length / tailleBlocs; // Precondition ou bloc % tailleBlocs = 0 potentiellement
        int[][] res = new int[nbBlocs][tailleBlocs];
        // Cut the array bloc in nbBlocs of equal size and store every sub-bloc in the 2D array res
        for (int i = 0; i < nbBlocs; i++) {
            // Copy the bloc from bloc[i * tailleBlocs] to bloc[i * tailleBlocs + tailleBlocs - 1] included and store it in res[i]
            System.arraycopy(bloc, i * tailleBlocs, res[i], 0, tailleBlocs);
        }
        return res;
    }

    /**
     * The reverse of the previous method (decoupage). It concatenates all the arrays into one.
     *
     * @param blocs the 2D array of int to be concatenated.
     * @return a 1D array which is the concatenation of all the arrays contained in blocs.
     * @throws NullPointerException if blocs is null.
     */
    public int[] recollage_bloc(int[][] blocs) {
        int nb_blocs = blocs.length;
        int taille_bloc = blocs[0].length;
        int[] res = new int[nb_blocs * taille_bloc];
        for (int i = 0; i < nb_blocs; i++) {
            // Copy the bloc from blocs[i][0] to blocs[i][taille_bloc - 1] included and store it in res[i * taille_bloc]
            System.arraycopy(blocs[i], 0, res, i * taille_bloc, taille_bloc);
        }
        return res;
    }


    /**
     * Generate the key at the index n of tab_cles. The master key is permuted according to PC1 and then split into two halves. Each half is rotated to the left by a certain number of bits (TAB_DECALAGE[n]) depending on the round. The two halves are then concatenated and permuted according to PC2. The result is stored in tab_cles at the index n.
     *
     * @param n the index of the key to be generated.
     * @throws IllegalArgumentException if n < 0 || n > 15.
     */
    public void genereCle(int n) {
        // Preconditions
        if (n < 0 || n > 15) {
            throw new IllegalArgumentException("The index of the key must be between 0 and 15 included: " + n + " < 0 || " + n + " > 15.");
        }
        // Permute the master key according to PC1
        int[] permutedKey = permutation(PC1, masterKey);
        // Split the array permutedKey into two halves
        int[][] split_permuted_key = decoupage(permutedKey, permutedKey.length / 2);
        // Rotate the two halves to the left by a certain number of bits (TAB_DECALAGE[n])
        split_permuted_key[0] = decalle_gauche(split_permuted_key[0], TAB_DECALAGE[n]);
        split_permuted_key[1] = decalle_gauche(split_permuted_key[1], TAB_DECALAGE[n]);
        // Concatenate the two halves
        int[] bloc_recolle = recollage_bloc(split_permuted_key);
        // Permute the result according to PC2 and store it in tab_cles at the index n
        tab_cles[n] = permutation(PC2, bloc_recolle);
    }

    /**
     * Shift/rotate bloc by nbCran to the left.
     *
     * @param bloc   the array of int to be shifted
     * @param nbCran the number of shift to the left
     * @return a 1D array which is the result of the array bloc shifted/rotated nbCran to the left.
     * @throws NullPointerException     if bloc is null.
     * @throws IllegalArgumentException if bloc.length < 1.
     */
    public int[] decalle_gauche(int[] bloc, int nbCran) {
        // Preconditions
        if (bloc.length < 1) {
            throw new IllegalArgumentException("The length of the array must be positive: " + bloc.length + " < 1.");
        }
        nbCran = nbCran % bloc.length;
        int[] res = new int[bloc.length];
        // Shift the elements of bloc nbCran to the left and store the result in res
        for (int i = 0; i < bloc.length; i++) {
            // The modulo is used to avoid an index out of bounds
            res[(bloc.length + (i - nbCran)) % bloc.length] = bloc[i];
        }
        return res;
    }

    /**
     * Converts a positive int n into its binary representation and store each bit in the returning 1D array. The first element of the array is the most significant bit and the last one is the least significant bit ([1, 0, 1, 0] = 10 for example). We will only use this method with size = 4, but it can be used with any size.
     *
     * @param n    the int to be converted (must be positive or equal to 0).
     * @param size the size of the returning array
     * @return a 1D array of length size containing the binary representation of n.
     * @throws IllegalArgumentException if size < 0 or if n < 0.
     */
    public int[] intToBinaryArray(int n, int size) {
        // Preconditions
        if (n < 0) {
            throw new IllegalArgumentException("The int to be converted must be positive: " + n + " < 0.");
        }
        if (size < 0) {
            throw new IllegalArgumentException("The size of the array must be positive: " + size + " < 0.");
        }

        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[size - 1 - i] = n & 1;
            n >>= 1;
        }
        return res;
    }

    /**
     * Calculate the value of the array S corresponding to the 6 bits of tab. The first and last bits of tab are used to determine the line of the array S and the 4 bits in the middle are used to determine the column of the S-Box. The result is an int which is then converted in binary and stored in a 1D array of length 4.
     *
     * @param tab     the array of int to be converted.
     * @param noRonde the index of the round.
     * @return a 1D array of length 4 containing the binary representation of the int corresponding to a certain line (first and last bit) and column (every other) of the array S at the round noRonde.
     * @throws NullPointerException     if tab is null.
     * @throws IllegalArgumentException if tab.length != 6 or noRonde < 0 || noRonde > 15.
     */
    public int[] fonction_S(int[] tab, int noRonde) {
        // Preconditions
        if (tab.length != 6) {
            throw new IllegalArgumentException("The length of tab must be equal to 6: " + tab.length + " != " + 6 + ".");
        }
        if (noRonde < 0 || noRonde > 15) {
            throw new IllegalArgumentException("The round must be between 0 and 15 (included): 0 <= " + noRonde + " < 16.");
        }
        // The first and last bits of tab are used to determine the line of the array S
        // We shift the first bit of tab to the left by 1. Then we add the last bit of tab
        int noLigne = tab[0] << 1 | tab[5];
        // The 4 bits in the middle are used to determine the column of the array S
        // We shift the second bit of tab to the left by 3. Then we shift the third bit of tab to the left by 2. Then we shift the fourth bit of tab to the left by 1. Then we add the fifth bit of tab
        int noColonne = tab[1] << 3 | tab[2] << 2 | tab[3] << 1 | tab[4];
        // Get the int corresponding to the line noLigne and the column noColonne of the array S[noRonde]
        int numberToConvert = S[noRonde][noLigne][noColonne];
        // Convert the int to binary and store it in a 1D array of length 4
        return intToBinaryArray(numberToConvert, 4);
    }


    /**
     * Apply the function F to unD. The array unD is first permuted according to E. Then, XOR is applied between uneCle and unD. The result is split into 8 sub-blocs of length 6. Each sub-bloc is then used as a parameter for the fonction_S. The result of each fonction_S is stored in a 2D-array which will be concatenated into a 1D-array. This array is permuted according to P and the result is returned.
     *
     * @param uneCle  a key contained in tab_cles (an array of int of length 48), it contains only 0 or 1.
     * @param unD     the array of int to be used in the function F (length of the array = 32), it contains only 0 or 1.
     * @param noRonde the index of the round.
     * @return an array of length 32 (result of the function F to unD).
     * @throws NullPointerException     if uneCle or unD is null.
     * @throws IllegalArgumentException if unD.length != 32.
     */
    public int[] fonction_F(int[] uneCle, int[] unD, int noRonde) {
        // Preconditions
        if (unD.length != 32) {
            throw new IllegalArgumentException("The length of unD must be equal to 32: " + unD.length + " != " + 32 + ".");
        }
        int[] unDprime = permutation(E, unD);
        // Xor between uneCle and unD
        int[] resXor = xor(uneCle, unDprime);
        // Split the array resXor into 8 sub-blocs of length 6
        int[][] subBlocs = decoupage(resXor, 6);
        int[][] res = new int[8][4];
        // Apply the fonction_S to each sub-bloc and store the result in res
        for (int i = 0; i < 8; i++) {
            res[i] = fonction_S(subBlocs[i], noRonde % 8);
        }
        // Concatenate all the arrays of res into one
        int[] resConcat = recollage_bloc(res);
        // Permute the array resConcat according to P
        return permutation(P, resConcat);
    }


    /**
     * Apply the bitwise operator XOR between the two arrays, indexes by indexes. It returns a new array containing the result of the XOR.
     *
     * @param tab1 the first array to be XORed with the second one.
     * @param tab2 the second array to be XORed with the first one.
     * @return a new array containing the result of the XOR between tab1 and tab2.
     * @throws NullPointerException     if tab1 or tab2 is null.
     * @throws IllegalArgumentException if tab1.length != tab2.length.
     */
    public int[] xor(int[] tab1, int[] tab2) {
        // Preconditions
        if (tab1.length != tab2.length) {
            throw new IllegalArgumentException("The length of tab1 must be equal to the length of tab2: " + tab1.length + " != " + tab2.length + ".");
        }

        int[] res = new int[tab1.length];
        // XOR between the two arrays, indexes by indexes and store it in the new array
        for (int i = 0; i < tab1.length; i++) {
            // ^ is the XOR operator
            res[i] = tab1[i] ^ tab2[i];
        }
        return res;
    }

    /**
     * Crypt a String message_clair with the DES algorithm.
     *
     * @param message_clair the string to be crypted.
     * @return an array of int containing the crypted message.
     * @throws NullPointerException if message_clair is null.
     */
    public int[] crypte(String message_clair) {
        // Convert the String message_clair to binary
        int[] text = stringToBits(message_clair);
        //System.out.println(Arrays.toString(text));
        // Create an array of length multiple of TAILLE_BLOC (64) and fill it with 0s
        int[] text64 = new int[(text.length / 64) * 64 + 64];
        // Copy the array text in the array text64 (with 0s at the beginning) in order to have an array of length multiple of TAILLE_BLOC (64)
        System.arraycopy(text, 0, text64, 64 - text.length % 64, text.length);
        //System.out.println("Message binaire = " + Arrays.toString(text));
        //System.out.println("Message avec des 0 au début = " +Arrays.toString(text64));
        // Split the array text into sub-blocs of length TAILLE_BLOC (64).
        int[][] split = decoupage(text64, TAILLE_BLOC);

        for (int i = 0; i < split.length; i++) {
            // Initial permutation
            split[i] = permutation(PERM_INITIALE, split[i]);
            // Split the array split[i] into two halves
            int[][] GD = decoupage(split[i], TAILLE_SOUS_BLOC);
            int[] G = GD[0];
            int[] D = GD[1];
            for (int j = 0; j < NB_RONDE; j++) {
                int[] tmp = D;
                D = xor(G, fonction_F(tab_cles[j], D, j));
                G = tmp;
            }
            // Concatenate the two halves (G, D)
            split[i] = recollage_bloc(new int[][]{G, D});
            // Final permutation (cancel the initial permutation)
            split[i] = invPermutation(PERM_INITIALE, split[i]);
        }
        return recollage_bloc(split);
    }

    /**
     * Decrypt a message codé with the DES algorithm.
     *
     * @param messageCodé the array of int to be decrypted.
     * @return a String containing the decrypted message.
     */
    public String decrypte(int[] messageCodé) {
        // Split the array messageCodé into sub-blocs of length TAILLE_BLOC (64).
        //System.out.println(Arrays.toString(messageCodé));
        int[][] split = decoupage(messageCodé, 64);
        //System.out.println(Arrays.deepToString(split));
        // For each sub-bloc
        for (int i = 0; i < split.length; i++) {
            // Initial permutation
            split[i] = permutation(PERM_INITIALE, split[i]);
            // Split the array split[i] into two halves
            int[][] GD = decoupage(split[i], TAILLE_SOUS_BLOC);
            int[] G = GD[0];
            int[] D = GD[1];
            for (int j = NB_RONDE - 1; j > -1; j--) {
                int[] tmp = D;
                D = G;
                G = xor(tmp, fonction_F(tab_cles[j], D, j));
            }
            // Concatenate the two halves (G, D)
            split[i] = recollage_bloc(new int[][]{G, D});
            // Final permutation (cancel the initial permutation)
            split[i] = invPermutation(PERM_INITIALE, split[i]);
        }
        int[] messageBinary = recollage_bloc(split);
        //System.out.println(Arrays.toString(messageBinary));
        return bitsToString(messageBinary);
    }

}
