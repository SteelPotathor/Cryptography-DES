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
    }

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

    public void testStringToBitsToString(int nombreTest) {
        ArrayList<String> stringArrayList = generateRandomSentences(50, nombreTest + 1);
        int i = 0;
        String s = stringArrayList.get(i);
        while (i < nombreTest && des.bitsToString(des.stringToBits(s)).equals(s)) {
            i++;
            s = stringArrayList.get(i);
        }
        if (i == nombreTest) {
            System.out.println("La méthode stringToBits et bitsToString fonctionnent correctement. (Une String s encodée puis décodée est égale à s). \nLe nombre de tests reposant sur des phrases aléatoires de 50 caractères est de " + nombreTest + ".");
        } else {
            System.out.println("Erreur de d'encodage / décodage de la String: " + s);
        }
    }


    public void testPermutationInvPermutation(int nombreTest) {
        int i = 0;
        int maxBits = 1024;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            int randomInt = random.nextInt(maxBits);
            int[] tab_permutation = des.generePermutation(randomInt);
            int[] bloc = des.generePermutation(randomInt);
            int[] bloc_permute = des.permutation(tab_permutation, bloc);
            int[] bloc_permuteInvPermute = des.invPermutation(tab_permutation, bloc_permute);
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
    }


    public void testXor(int nombreTest) {
        int i = 0;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
            int[] tab = des.generePermutation(64);
            int[] tab2 = des.generePermutation(64);
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
    }

    public void testDecallageGauche() {
        int nbCranMax = 64;
        int nbCran = 1;
        boolean testOk = true;
        int[] tab = null;
        int[] res = null;
        while (nbCran < nbCranMax && testOk) {
            tab = des.generePermutation(nbCranMax);
            res = des.decalle_gauche(tab, nbCran);
            for (int ind = 0; ind < nbCranMax - nbCran; ind++) {
                if (res[ind] != tab[ind + nbCran]) {
                    testOk = false;
                }
            }
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
    }

    public void testFonctionS() {
        // On regarde si la fonction S fonctionne correctement sur les 64 possibilités de 6 bits sur le tableau S[0].
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
        for (int i = 0; i < des.S[0][0].length * 2; i++) {
            int[] array = des.fonction_S(test[i], 0);
            int[] tab = des.intToBinaryArray(des.S[0][i % 2][i / 2], 4);
            int[] array2 = des.fonction_S(test[i + des.S[0][0].length * 2], 0);
            int[] tab2 = des.intToBinaryArray(des.S[0][i % 2 + 2][i / 2], 4);
            if (!Arrays.equals(array, tab)) {
                System.out.println("Erreur de fonction S sur le tableau " + Arrays.toString(test[i]) + " et " + Arrays.toString(array) + ".");
            }
            if (!Arrays.equals(array2, tab2)) {
                System.out.println("Erreur de fonction S sur le tableau " + Arrays.toString(test[i + des.S[0][0].length * 2]) + " et " + Arrays.toString(array2) + ".");
            }
        }

    }

    public void testCrypteDecrypte(int nombreTest) {
        ArrayList<String> randomSentences = generateRandomSentences(100, nombreTest);
        int i = 0;
        boolean testOk = true;
        while (i < nombreTest && testOk) {
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
    }

    public static void main(String[] args) {
        TestDes testDes = new TestDes();
        //testDes.testPermutationInvPermutation(1000);
        //testDes.testStringToBitsToString(100);
        //testDes.testExceptionStringToBits();
        //testDes.testExceptionBitsToString();
        //testDes.testIntToBinaryArray();
        //testDes.testXor(100);
        testDes.testDecallageGauche();
        testDes.testCrypteDecrypte(100);
        testDes.testFonctionS();
    }
    /* test =>
    conversion ok
    perm/invperm ok
    decallage gauche ok
    xor => xor xor = base ok
    fonction s not ok
    crypte decrypte ok
    gerer exceptions tests à la fin
     */
}
