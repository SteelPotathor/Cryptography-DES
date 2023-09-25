public class Main {
    public static void main(String[] args) {
        DES des = new DES();
        int[][] blocs = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        int[] res = des.recollage_bloc(blocs);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}
