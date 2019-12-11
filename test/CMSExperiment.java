import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;

public class CMSExperiment {

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

        CMS cms = new CMS(epsilon, delta, s, 2*epsilon, epsilon);
//		System.out.println("true frequency : " + goalFreq);
        int approx = cms.approximateFrequency(rand);
//		System.out.println("approx : " + approx);
//		System.out.println("Error bound:"+ (goalFreq+epsilon*N));
        boolean event=false;
        if (approx>=goalFreq+epsilon*initN){
            event=true;
        }
        return new Ret(((double)approx-goalFreq)/initN, event);
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

    @Test
    void hh() {
        System.out.println("\n" + "HH experiment");
        ArrayList<Integer> s = new ArrayList<Integer>();

        int N = 1000000;
        int U = 1234411;
        int freqBound=1000;
        int[] arr = new int[U];
        Arrays.fill(arr, 0);
        int remN=N;
        while(remN>0){
            int rand = ThreadLocalRandom.current().nextInt(0,U);
            int freq = ThreadLocalRandom.current().nextInt(0,freqBound);
            if (remN-freq<0){
                freq=remN;
            }
            arr[rand]+=freq;
            for (int i = 0; i < freq; i++) {
                s.add(rand);
            }
            remN-=freq;
        }

        float epsilon = (float) 0.001;
        float delta = (float) 0.5;
        float q = 2*epsilon;
        float r = epsilon;

        CMS cms = new CMS(epsilon, delta, s, q, r);
        int[] hh = cms.approximateHH();
        System.out.println("numHeavyHitter : " + hh.length);
//        for (int i : s) {
////			System.out.println(cms.approximateFrequency(i) + " : " + arr[i]);
//            if (cms.approximateFrequency(i)>=2*epsilon*N)
//                assertTrue(arrayContains(hh, i));
//            if (cms.approximateFrequency(i)<epsilon*N)
//                assertTrue(!arrayContains(hh, i));
//        }

        for (int i = 0; i < arr.length; i++) {
            if(arr[i]>=q*N){
                assertTrue(arrayContains(hh, i));
            }
//            if(arr[i]<r*epsilon){
//                assertTrue(arrayContains(hh, i));
//            }
        }
        double countH = 0;
        double countL = 0;
        for (int i : hh) {
            if (arr[i] >= q*N)
                countH++;
            if (arr[i] < r*N)
                countL++;
        }
        double recallFailure=0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]>= q*N){
                boolean miss=true;
                if (arrayContains(hh, i)){
                    miss=false;
                }
                if (miss){
                    recallFailure++;
                }
            }
        }
        System.out.println("h : " + countH + "  l : " + countL);
        System.out.println("HeavyHitter Precision: " + (countH)/hh.length);
        System.out.println("HeavyHitter Err, compare with delta : " + (countL)/N);
        System.out.println("HeavyHitter Recall Failure, needs to be zero: " + recallFailure);
        System.out.println("HeavyHitter ratio : " + (hh.length + 0.0)/N);
    }

    boolean arrayContains(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x)
                return true;
        }

        return false;
    }

}

class Ret{
    double aDouble;
    boolean aBoolean;

    public Ret(double aDouble, boolean aBoolean) {
        this.aDouble = aDouble;
        this.aBoolean = aBoolean;
    }
}