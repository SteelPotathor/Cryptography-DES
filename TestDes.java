import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestDes {
    public Random random = new Random();
    public DES des = new DES();

    public void testExceptionStringToBits() {
        System.out.println("Test de la méthode stringToBits avec les Strings vides et nulles:");

        String exception = "";
        try {
            des.stringToBits(exception);
        } catch (NumberFormatException e) {
            System.out.println("Exception prévue: La méthode stringToBits ne fonctionne pas avec les String vides.");
        }

        String exception2 = null;
        try {
            des.stringToBits(exception2);
        } catch (NullPointerException e) {
            System.out.println("Exception prévue: La méthode stringToBits ne fonctionne pas avec les String nulles.");
        }
        System.out.println();
    }

    public void testExceptionBitsToString() {
        System.out.println("Test de la méthode bitsToStrings avec des tableaux de taille 0 et nuls:");

        int[] exception = new int[0];
        try {
            des.bitsToString(exception);
        } catch (NumberFormatException e) {
            System.out.println("Exception prévue: La méthode bitsToString ne fonctionne pas avec les tableaux de taille 0.");
        }

        int[] exception2 = null;
        try {
            des.bitsToString(exception2);
        } catch (NullPointerException e) {
            System.out.println("Exception prévue: La méthode bitsToString ne fonctionne pas avec les tableaux nuls.");
        }
        System.out.println();
    }

    /**
     * Generate random sentences. The sentences are composed of random characters between 32 and 128 (ASCII CODE).
     *
     * @param sentenceLength    the sentence length
     * @param numberOfSentences the number of sentences
     */
    public ArrayList<String> generateRandomSentences(int sentenceLength, int numberOfSentences) {
        ArrayList<String> sentences = new ArrayList<>();
        for (int i = 0; i < numberOfSentences; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < sentenceLength; j++) {
                sb.append((char) random.nextInt(32, 128));
            }
            sentences.add(sb.toString());
        }
        return sentences;
    }

    /**
     * Test of stringToBits and bitsToString methods. We generate random sentences of 50 characters and we check that the conversion String -> bits -> String is equal to the initial String.
     *
     * @param nombreTest the number of tests
     */
    public void testStringToBitsToString(int nombreTest) {
        // Generate an arraylist containing random sentences of 50 characters
        ArrayList<String> stringArrayList = generateRandomSentences(50, nombreTest + 1);
        int i = 0;
        String s = stringArrayList.get(i);
        // We check that the conversion String -> bits -> String is equal to the initial String
        while (i < nombreTest && des.bitsToString(des.stringToBits(s)).equals(s)) {
            i++;
            s = stringArrayList.get(i);
        }
        if (i == nombreTest) {
            System.out.println("La méthode stringToBits et bitsToString fonctionnent correctement. (Une String s encodée puis décodée est égale à s). \nLe nombre de tests reposant sur des phrases aléatoires de 50 caractères est de " + nombreTest + ".");
        } else {
            System.out.println("Erreur de d'encodage / décodage de la String: " + s);
        }
        System.out.println();
    }


    /**
     * Test of permutation and invPermutation methods. We generate random permutations, and we check that the permutation of the permutation is equal to the initial permutation. The maximum size of the permutation is 1024 bits.
     *
     * @param nombreTest the number of tests
     */
    public void testPermutationInvPermutation(int nombreTest) {
        int i = 0;
        int maxBits = 1024;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            int randomInt = random.nextInt(maxBits);
            // We generate a random tab_permutation of size randomInt
            int[] tab_permutation = des.generePermutation(randomInt);
            // We generate a random bloc (which will be permuted) of size randomInt
            int[] bloc = des.generePermutation(randomInt);

            // We permute the bloc
            int[] bloc_permute = des.permutation(tab_permutation, bloc);
            // We inverse permute the permuted bloc
            int[] bloc_permuteInvPermute = des.invPermutation(tab_permutation, bloc_permute);
            // We check that the permutation of the permutation is equal to the initial permutation
            if (!Arrays.equals(bloc, bloc_permuteInvPermute)) {
                System.out.println("Erreur de permutation / invPermutation sur le tableau " + Arrays.toString(bloc) + " et " + Arrays.toString(bloc_permuteInvPermute) + ".");
                testOk = false;
            } else {
                i++;
            }
        }
        if (i == nombreTest) {
            System.out.println("Les méthodes permutation et invPermutation fonctionnent correctement. \nLe nombre de tests reposant sur des permutations aléatoires de tableaux d'au maximum " + maxBits + "bits est de " + nombreTest + ".");
        }
        System.out.println();
    }

    /**
     * Test of decoupage and recollage methods. We generate random permutations of length 64, and we check that the decoupage and recollage of the random permutations are equal to the initial permutations. We check bloc sizes of 1, 2, 4, 8, 16 and 32.
     *
     * @param nombreTest the number of tests
     */
    public void testDecoupageRecollage(int nombreTest) {
        // Generate an arraylist of random permutations of length 64
        ArrayList<int[]> randomPermutations = new ArrayList<>();
        for (int i = 0; i < nombreTest; i++) {
            randomPermutations.add(des.generePermutation(64));
        }
        // We check bloc sizes of 1, 2, 4, 8, 16 and 32
        int[] taille_blocs = {1, 2, 4, 8, 16, 32};
        int i = 0;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            int[] tab = randomPermutations.get(i);
            for (int j = 0; j < taille_blocs.length; j++) {
                // We decoupe the permutation
                int[][] tab_decoupe = des.decoupage(tab, taille_blocs[j]);
                // We recollage the permutation
                int[] res = des.recollage_bloc(tab_decoupe);
                // Check that the decoupage and recollage of the random permutations are equal to the initial permutations
                if (!Arrays.equals(tab, res)) {
                    System.out.println("Erreur de découpage / recollage sur le tableau " + Arrays.toString(tab) + ", " + Arrays.deepToString(tab_decoupe) + " et " + Arrays.toString(res) + ".");
                    testOk = false;
                }
            }
            if (testOk) {
                i++;
            }
        }
        if (i == nombreTest) {
            System.out.println("Les méthodes decoupage et recollage fonctionnent correctement. \nLe nombre de tests reposant sur des permutations aléatoires de tableaux d'au maximum 64 bits est de " + nombreTest + ".");
        }
        System.out.println();
    }

    /**
     * Test of decalle_gauche method. We generate random permutations with random shift, and we check that the shift is correct. We check each shift to 1 to 64.
     */
    public void testDecallageGauche() {
        int nbCranMax = 64;
        int nbCran = 1;
        boolean testOk = true;
        int[] tab = null;
        int[] res = null;
        while (nbCran < nbCranMax && testOk) {
            tab = des.generePermutation(nbCranMax);
            res = des.decalle_gauche(tab, nbCran);
            // We check that the length - nbCran first bits are equal to the nbCran last bits of the initial permutation
            for (int ind = 0; ind < nbCranMax - nbCran; ind++) {
                if (res[ind] != tab[ind + nbCran]) {
                    testOk = false;
                }
            }
            // We check that the nbCran last bits are equal to the length - nbCran first bits of the initial permutation
            for (int ind = tab.length - nbCran; ind < tab.length; ind++) {
                if (res[ind] != tab[ind - tab.length + nbCran]) {
                    testOk = false;
                }
            }
            nbCran++;
        }
        if (testOk) {
            System.out.println("La méthode decalle_gauche fonctionne correctement. \nLe nombre de decallage maximum effectué est de " + nbCranMax + ".");
        } else {
            System.out.println("Erreur de decallage gauche sur le tableau " + Arrays.toString(tab) + " et " + Arrays.toString(res) + ".");
        }
        System.out.println();
    }

    /**
     * Test of xor method. We generate random permutations of length 64, and we check that the double xor is equal to the initial permutation.
     *
     * @param nombreTest the number of tests
     */
    public void testXor(int nombreTest) {
        int i = 0;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            // We generate two random permutations
            int[] tab = des.generePermutation(64);
            int[] tab2 = des.generePermutation(64);

            // We check that the double xor is equal to the initial permutation
            int[] doubleXor = des.xor(des.xor(tab, tab2), tab2);
            if (!Arrays.equals(tab, doubleXor)) {
                System.out.println("Erreur de xor / unxor sur le tableau " + Arrays.toString(tab) + " et " + Arrays.toString(tab2) + ".");
                testOk = false;
            } else {
                i++;
            }
        }
        if (i == nombreTest) {
            System.out.println("La méthode xor fonctionne correctement (le double xor renvoie le tableau initial). \nLe nombre de tests de la fonction xor est de " + nombreTest + ".");
        }
        System.out.println();
    }


    /**
     * Test of fonction_S method. We check that the function S works correctly on the 64 possibilities of 6 bits on the table S[0].
     */
    public void testFonctionS() {
        // We check that the function S works correctly on the 64 possibilities of 6 bits on the table S[0]
        int[][] test = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1},
                {0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 1, 0},
                {0, 0, 0, 1, 1, 1},
                {0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1, 1},
                {0, 0, 1, 1, 0, 0},
                {0, 0, 1, 1, 0, 1},
                {0, 0, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1},
                {0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1},
                {0, 1, 0, 0, 1, 0},
                {0, 1, 0, 0, 1, 1},
                {0, 1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0, 1},
                {0, 1, 0, 1, 1, 0},
                {0, 1, 0, 1, 1, 1},
                {0, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 0, 1},
                {0, 1, 1, 0, 1, 0},
                {0, 1, 1, 0, 1, 1},
                {0, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 0, 1},
                {0, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 1, 1},
                {1, 0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 1},
                {1, 0, 0, 1, 1, 0},
                {1, 0, 0, 1, 1, 1},
                {1, 0, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 1},
                {1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 1},
                {1, 0, 1, 1, 0, 0},
                {1, 0, 1, 1, 0, 1},
                {1, 0, 1, 1, 1, 0},
                {1, 0, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 1},
                {1, 1, 0, 0, 1, 0},
                {1, 1, 0, 0, 1, 1},
                {1, 1, 0, 1, 0, 0},
                {1, 1, 0, 1, 0, 1},
                {1, 1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 0, 1},
                {1, 1, 1, 0, 1, 0},
                {1, 1, 1, 0, 1, 1},
                {1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1},
        };
        boolean testOk = true;
        int i = 0;
        while (i < des.S[0][0].length * 2 && testOk) {
            int[] array = des.fonction_S(test[i], 0);
            int[] tab = des.intToBinaryArray(des.S[0][i % 2][i / 2], 4);
            int[] array2 = des.fonction_S(test[i + des.S[0][0].length * 2], 0);
            int[] tab2 = des.intToBinaryArray(des.S[0][i % 2 + 2][i / 2], 4);
            if (!Arrays.equals(array, tab)) {
                testOk = false;
                System.out.println("Erreur de fonction S sur le tableau " + Arrays.toString(test[i]) + " et " + Arrays.toString(array) + ".");
            }
            if (!Arrays.equals(array2, tab2)) {
                testOk = false;
                System.out.println("Erreur de fonction S sur le tableau " + Arrays.toString(test[i + des.S[0][0].length * 2]) + " et " + Arrays.toString(array2) + ".");
            }
            i++;
        }
        if (testOk) {
            System.out.println("La méthode fonction_S fonctionne correctement. Méthode testée sur tout le premier tableau S.");
        }
        System.out.println();
    }

    /**
     * Test of crypte and decrypte methods. We generate random sentences of 100 characters, and we check that the decryption of the encryption is equal to the initial sentence.
     *
     * @param nombreTest the number of tests
     */
    public void testCrypteDecrypte(int nombreTest) {
        // Generate an arraylist containing random sentences of 1000 characters
        ArrayList<String> randomSentences = generateRandomSentences(1000, nombreTest);
        int i = 0;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            // We check that the decryption of the encryption is equal to the initial sentence
            if (randomSentences.get(i).equals(des.decrypte(des.crypte(randomSentences.get(i))))) {
                i++;
            } else {
                System.out.println("Erreur de cryptage / decryptage sur la phrase " + randomSentences.get(i) + ".");
                testOk = false;
            }
        }
        if (i == nombreTest) {
            System.out.println("La méthode crypte et decrypte fonctionnent correctement. \nLe nombre de tests reposant sur des phrases aléatoires de 100 caractères est de " + nombreTest + ".");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TestDes testDes = new TestDes();

        // Test of a few exceptions
        testDes.testExceptionStringToBits();
        testDes.testExceptionBitsToString();

        // Test of all the methods
        testDes.testStringToBitsToString(1000);
        testDes.testPermutationInvPermutation(1000);
        testDes.testDecoupageRecollage(1000);
        testDes.testDecallageGauche();
        testDes.testXor(1000);
        testDes.testFonctionS();
        testDes.testCrypteDecrypte(100);

    }
}
