import java.util.Arrays;

public class TripleDES {

    public DES des1;
    public DES des2;
    public DES des3;

    public TripleDES() {
        this.des1 = new DES();
        this.des2 = new DES();
        this.des3 = new DES();
    }

    // TODO: implementer le tripleDES
    public int[] cryptage(String messageClair) {
        int[] premierCryptage = this.des1.crypte(messageClair);
        int[] deuxiemeCryptage = this.des2.crypte(Arrays.toString(premierCryptage));
        return this.des3.crypte(Arrays.toString(deuxiemeCryptage));
    }

    public String decryptage(int[] messageCrypte) {
        String decryptage1 = this.des3.decrypte(messageCrypte);
        // This method converts a String to an array of ints
        int[] cryptage2 = Arrays.stream(decryptage1.substring(1, decryptage1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        // This method converts a String to an array of ints
        String decryptage2 = this.des2.decrypte(cryptage2);
        int[] cryptage1 = Arrays.stream(decryptage2.substring(1, decryptage2.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        return this.des1.decrypte(cryptage1);
    }

    public static void main(String[] args) {
        TripleDES tripleDES = new TripleDES();
        System.out.println(tripleDES.decryptage(tripleDES.cryptage("Bonjour à tous, j'aime les frites et ouais mon gars d'ailleurs je suis le coiffeur le moins cher de l'île!")));

    }
}
