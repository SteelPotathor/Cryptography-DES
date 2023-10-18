import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        DES des = new DES();
        DESJAWAD des2 = new DESJAWAD();
        des.tab_cles[0] = new int[]{1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1};
        // COMM fonction F précisions => mr pouit
        // test de contenance 0/1?
        String init = "Zoublarg eazouyaeziuytgaezuy!àç&159789";
        System.out.println(des.decrypte(des.crypte(init)));
    }
}
