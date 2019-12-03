import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class AMSDimRed {

    ArrayList<Vector> reduceDim(ArrayList<Vector> inputVectors, float epsilon, float delta){
        return null;
    }

    static Vector reduceDim(Vector input, float epsilon, float delta){
        // TODO what's the correct k and epislon?
        int k =  (int) (Math.ceil(15 / epsilon / epsilon)*Math.log(1/delta)) / 4;
//        int totalB = 1;
        B b = new B(k);
        for (int i = 0; i < input.vals.length; i++) {
            int num = ThreadLocalRandom.current().nextInt();
            for (int j = 0; j < input.vals[i]; j++) {
                b.receive(num);
            }
        }
        double[] reduced= new double[k];
        for (int i = 0; i < k; i++) {
            reduced[i]=Math.sqrt(b.as[i].output()/k);
        }
        return new Vector(reduced);
    }
}

class Vector{
    int[] vals;
    double[] ds;

    public Vector(int[] vals) {
        this.vals = vals;
    }

    public Vector(double[] vals){
        ds=vals;
    }
    //    ArrayList<Integer> toArrayList(){
//        return new ArrayList<Integer>(Arrays.asList(vals));
//    }
}