public class Main {


    public static void main(String[] args) {
        DES des = new DES();
        int[] tab = des.stringToBits("Hello world");
        String decode = des.bitsToString(tab);
        System.out.println(decode);
        System.out.println(des.affiche_tableau(tab));
    }
}
