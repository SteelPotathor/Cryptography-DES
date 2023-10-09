import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        DES des = new DES();
        int[] tab = {0, 1, 2, 3, 4, 5};
        des.fonction_F(null, des.generePermutation(32), 0);
        // COMM fonction F
    }
}
