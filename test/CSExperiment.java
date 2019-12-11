import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;

public class CSExperiment {

    Ret freqExperiment(float epsilon, float delta, int goalFreq) {
//		System.out.println("\n" + "approximateFrequencies2");
        ArrayList<Integer> s = new ArrayList<Integer>();

        int U = 1234411;
        int freqBound=1000;
        int initN=2000000;
        int N=initN-goalFreq;
        while(N>0){
            int rand = ThreadLocalRandom.current().nextInt(0,U);
            int freq = ThreadLocalRandom.current().nextInt(0,freqBound);
            if (N-freq<0){
                freq=N;
            }

            for (int j = 0; j < freq; j++) {
                s.add(rand);
            }
            N-=freq;
        }

        int rand = ThreadLocalRandom.current().nextInt(U,U+10000);

        for (int j = 0; j < goalFreq; j++) {
            s.add(rand);
        }


        int l = (int) (2/epsilon);
        int k = (int) Math.round(Math.log(1/delta));
//		System.out.println("L : " + l);
//		System.out.println("k*L : " + k*l);

        CountSketch cs = new CountSketch(epsilon, delta, s);
//		System.out.println("true frequency : " + goalFreq);
        int approx = cs.approximateFrequency(rand);
//		System.out.println("approx : " + approx);
//		System.out.println("Error bound:"+ (goalFreq+epsilon*N));
        boolean event=false;
        if (approx>=goalFreq+epsilon*initN){
            event=true;
        }
        return new Ret((Math.abs((double)approx-goalFreq))/initN, event);
    }

    void repeatExperiment(int goalFreq){
        float epsilon = (float) 0.2;
        float delta = (float) 0.5;
        int rep=100;
        double errorPerc=0;
        double events=0;
        for (int i = 0; i < rep; i++) {
            Ret ret=freqExperiment(epsilon, delta, goalFreq);
            errorPerc+=ret.aDouble;
            if (ret.aBoolean){
                events++;
            }
        }
        errorPerc/=rep;
        events/=rep;
        System.out.println("Goal frequency "+goalFreq+" with average actual epsilon "+errorPerc);
        System.out.println("Excessive error events, compare with delta: "+events);
    }

    @Test
    void differentFreq(){
        // 0
        int[] freqs={1, 10, 100, 1000, 10000, 100000, 1000000};
        for (int freq : freqs){
            repeatExperiment(freq);
        }
    }

}
