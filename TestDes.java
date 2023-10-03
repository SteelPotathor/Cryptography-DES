import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TestDes {
    public Random random = new Random();
    public DES des = new DES();

    public void testStringToBitsToString() {
        String[] test = {"    ", "Hello world!", "Goodbye world!", "Network", "A9d,/;!§9813285498*-+.?", "INFINITYyyyyyY!!!!", "Hard Pill to Swallow", "I'm a little teapot short and stout", "Here is my handle here is my spout", "When I get all steamed up hear me shout", "Tip me over and pour me out", "I'm a very special teapot yes it's true", "Here's an example of what I can do", "I can turn my handle into a spout", "Beating a Dead Horse", "Vortex"};
        for (String s : test) {
            int[] tab = des.stringToBits(s);
            String decode = des.bitsToString(tab);
            System.out.println(decode.equals(s));
        }
    }


    // test sur les entrees
    public void testInvPermutation(int nombreTest) {
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
        testDes.testStringToBitsToString();
    }
}
