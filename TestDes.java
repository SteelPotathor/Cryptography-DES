import java.util.Arrays;
import java.util.Random;

public class TestDes {
    Random random = new Random();
    DES des = new DES();
    // test sur les entrees
    void testInvPermutation(int nombreTest) {
        //return ou affichage??? => demandez au prof
        // Affichage OK mais expliciter ce que l'on teste
        int i = 0;
        int cnt = 0;
        while (i < nombreTest) {
            int randomInt = random.nextInt(129);
            int[] tab_permutation = des.generePermutation(randomInt);
            int[] bloc = des.generePermutation(randomInt);
            int[] bloc_permute = des.permutation(tab_permutation, bloc);
            int[] bloc_invPermute = des.invPermutation(tab_permutation, bloc_permute);
            if (Arrays.equals(bloc_invPermute, bloc)) {
                cnt++;
            }
            i++;
        }
        System.out.println("Test de la méthode invPermutation: " + cnt + "/" + nombreTest + " tests réussis");
    }

    public static void main(String[] args) {
        TestDes testDes = new TestDes();
        testDes.testInvPermutation(1000);
    }
}
