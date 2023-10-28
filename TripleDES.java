import java.util.Arrays;

/**
 * This class is used to crypt and decrypt a message using the triple DES algorithm
 */
public class TripleDES {

    public DES des1;
    public DES des2;
    public DES des3;

    /**
     * Constructor of the TripleDES class
     */
    public TripleDES() {
        this.des1 = new DES();
        this.des2 = new DES();
        this.des3 = new DES();
    }

    /**
     * Crypt a message using the triple DES algorithm
     *
     * @param messageClair the message to crypt
     * @return the crypted message (array of int)
     * @throws NullPointerException if the messageClair is null
     */
    public int[] cryptage(String messageClair) {
        int[] premierCryptage = this.des1.crypte(messageClair);
        int[] deuxiemeCryptage = this.des2.crypte(Arrays.toString(premierCryptage));
        return this.des3.crypte(Arrays.toString(deuxiemeCryptage));
    }

    /**
     * Decrypt a message using the triple DES algorithm
     *
     * @param messageCrypte the message to decrypt
     * @return the decrypted message
     * @throws NullPointerException if the messageCrypte is null
     */
    public String decryptage(int[] messageCrypte) {
        String decryptage1 = this.des3.decrypte(messageCrypte);
        // This method converts a String to an array of ints
        int[] cryptage2 = Arrays.stream(decryptage1.substring(1, decryptage1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        String decryptage2 = this.des2.decrypte(cryptage2);
        // This method converts a String to an array of ints
        int[] cryptage1 = Arrays.stream(decryptage2.substring(1, decryptage2.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        return this.des1.decrypte(cryptage1);
    }

    public static void main(String[] args) {
        TripleDES tripleDES = new TripleDES();
        System.out.println(tripleDES.decryptage(tripleDES.cryptage(" 我喜欢冰淇淋 漢字  漢字   かんじ  Bonjour à tous, j'aime les frites et ouais mon gars d'ailleurs je suis le coiffeur le moins cher de l'île!")));
    }
}
