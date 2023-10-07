import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        DES des = new DES();
        int[] tab = {0, 1, 2, 3, 4, 5};
        //des.fonction_S(new int[]{1, 0, 1, 0, 1, 0});
        System.out.println(Arrays.toString(des.decalle_gauche(new int[]{},-10)));
    }
}
