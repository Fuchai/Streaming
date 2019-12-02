import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class AMSTest {

    @org.junit.jupiter.api.Test
    void secondFreqMoment() {
        AMS ams=new AMS();
        ArrayList<Integer> input = new ArrayList<>();
        long actualF2=0;
        float epsilon=0.2f;
        float delta =0.2f;
        ams.report(epsilon, delta);

        for (int k = 0; k < 10; k++) {
            for (int i = 0; i < 1000; i++) {
                int insert = ThreadLocalRandom.current().nextInt(0, 10000000);
                int freq = ThreadLocalRandom.current().nextInt(0, 10);
                for (int j = 0; j < freq; j++) {
                    input.add(insert);
                }
                actualF2+=freq*freq;
            }
            double ret=ams.secondFreqMoment(input, epsilon, delta);
            System.out.println("estimated: "+ret);
            System.out.println("actual: "+actualF2);
            System.out.println();
        }
    }
}