import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public void testIntToBinaryArray() {
        int[][] result = {
                {0, 0, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0},
                {0, 0, 1, 1},
                {0, 1, 0, 0},
                {0, 1, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 0, 0, 0},
                {1, 0, 0, 1},
                {1, 0, 1, 0},
                {1, 0, 1, 1},
                {1, 1, 0, 0},
                {1, 1, 0, 1},
                {1, 1, 1, 0},
                {1, 1, 1, 1}
        };
        for (int i = 0; i < result.length; i++) {
            System.out.println(Arrays.equals(des.intToBinaryArray(i, 4), result[i]));
        }
    }

    public static void main(String[] args) {
        TestDes testDes = new TestDes();
        testDes.testPermutationInvPermutation(1000);
        //testDes.testStringToBitsToString(100);
        //testDes.testExceptionStringToBits();
        //testDes.testExceptionBitsToString();
        //testDes.testIntToBinaryArray();
    }
}
