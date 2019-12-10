import org.junit.jupiter.api.Test;
import sun.awt.windows.WPrinterJob;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class AMSDimRedTest {

    @Test
    void reduceDim() {

        int all=100;
        int exceeding=0;
        for (int j = 0; j < all; j++) {
            int arrLen=200;
            int actualL2=0;
            float[] vecVal=new float[arrLen];
            for (int i = 0; i < arrLen; i++) {
                int num = ThreadLocalRandom.current().nextInt(0, 100);
                vecVal[i]=num;
                actualL2+=num*num;
            }
            Vector vec= new Vector(vecVal);
            float epsilon = 0.05f;
            float delta =0.05f;
            int k = (int) (Math.ceil(15 / epsilon / epsilon)*Math.log(1/delta))/4;
            System.out.println("k: "+k);
            Vector estimated=AMSDimRed.reduceDim(vec, epsilon, delta);

            double estimatedL2=0;
            for (int i = 0; i <estimated.getDim(); i++) {
                estimatedL2+=estimated.getIthCoor(i)*estimated.getIthCoor(i);
            }

            double diffrate=(estimatedL2-actualL2)/actualL2;
            System.out.println(diffrate);
            if (Math.abs(diffrate)>epsilon){
                exceeding++;
            }
        }
        System.out.println(exceeding);
        System.out.println(exceeding/(double) all);
    }
}