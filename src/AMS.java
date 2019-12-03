import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class AMS {
    public static double secondFreqMoment(ArrayList<Integer> s, float epsilon, float delta) {
        int k = (int) Math.ceil(15 / epsilon / epsilon);
        int totalB = 24* (int) Math.ceil(Math.log(2/delta));
//        int totalB = 1;
        Stream<Integer> stream = s.stream();
        B[] bs = new B[totalB];
        for (int i = 0; i < totalB; i++) {
            bs[i] = new B(k);
        }
        stream.forEach(num -> {
            for (int i = 0; i < totalB; i++) {
                bs[i].receive(num);
            }
        });

        double[] bOutput = new double[totalB];
        for (int i = 0; i < totalB; i++) {
            bOutput[i] = bs[i].output();
        }
        Arrays.sort(bOutput);
        if (totalB % 2 != 0) {
            return bOutput[totalB / 2];
        } else {
            return (bOutput[(totalB - 1) / 2] + bOutput[totalB / 2]) / 2;
        }
    }

    public void report(float epsilon, float delta) {
        int k = (int) Math.ceil(15 / epsilon / epsilon);
        int totalB = 24 * (int) Math.ceil(Math.log(2 / delta));
        System.out.println("k: " + k);
        System.out.println("total B: " + totalB);
    }
}

