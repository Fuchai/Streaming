import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class BTest {

    @Test
    void receive() {
        B b = new B(100);
        long actualF2=0;
        for (int i = 0; i < 1000; i++) {
            int freq = ThreadLocalRandom.current().nextInt(0, 100);
//            freq=1;
            for (int j = 0; j < freq; j++) {
                b.receive(i);
//                for (int k = 0; k < 10; k++) {
//                    System.out.println(b.as[k].hash(i));
//                }
//                System.out.println();
            }
            actualF2+=freq*freq;
        }
        System.out.println(b.output());
        System.out.println(actualF2);
    }
}