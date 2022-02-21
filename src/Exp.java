import java.util.Random;

public class Exp {
    
    private static Random rand = new Random();

    static double getExp(double lambda){
        //x = −ln(1−Y)/λ 
        double y = rand.nextDouble();
        return -1.0 * (Math.log(1.0 - y)) / lambda;
    }

    public static void main(String[] args) {
        double lambda = Double.parseDouble(args[0]);
        int n = Integer.parseInt(args[1]);
        //rand.setSeed(0);  //for debugging
        for(int i = 0; i < n; i++){
            System.out.println(getExp(lambda));
        }
    }
}
