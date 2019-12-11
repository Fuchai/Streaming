import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SameMemory {

    float csDelta(float cmsEpsilon, float cmsDelta, float csEpsilon){
        int mem= (int) ((Math.round(Math.log(1/cmsDelta))+1)*((int) (2/cmsEpsilon)+1));
        int csl = (int) (3/Math.pow(csEpsilon,2.0)) + 1;
        float ret=(float) (1/Math.exp((double) mem/csl-1));
        return ret;
    }

    @Test
    void csDeltaTest(){
        float cmsEpsilon = (float) 0.0015;
        float cmsDelta = (float) 0.046;
        int cmsMem= (int) ((Math.round(Math.log(1/cmsDelta))+1)*((int) (2/cmsEpsilon)+1));
        System.out.println(cmsMem);

        float csEpsilon=0.053f;
        float csDelta=csDelta(cmsEpsilon, cmsDelta, csEpsilon);
        int csMem=((int) (3/Math.pow(csEpsilon,2.0)) + 1)*((int) Math.round(Math.log(1/csDelta)) + 1);
        System.out.println("csdelta "+csDelta);
        System.out.println(csMem);
    }

    Ret[] freqExperiment(float cmsEpsilon, float cmsDelta, float csEpsilon, int goalFreq) {
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

        CMS cms = new CMS(cmsEpsilon, cmsDelta, s, 2*cmsEpsilon, cmsEpsilon);
        int cmsApprox = cms.approximateFrequency(rand);
        boolean event=false;
        if (cmsApprox>=goalFreq+cmsEpsilon*initN){
            event=true;
        }
        Ret cmsRet= new Ret((Math.abs((double)cmsApprox-goalFreq))/initN, event);

        CountSketch cs = new CountSketch(csEpsilon, csDelta(cmsEpsilon, cmsDelta, csEpsilon), s);
        int csApprox = cs.approximateFrequency(rand);
        event=false;
        if (csApprox>=goalFreq+cmsEpsilon*initN){
            event=true;
        }
        Ret csRet= new Ret((Math.abs((double)csApprox-goalFreq))/initN, event);
        return new Ret[]{cmsRet, csRet};
    }

    void repeatExperiment(int goalFreq){
        float cmsEpsilon = (float) 0.001;
        float cmsDelta = (float) 0.05;
        float csEpsilon=0.1f;

        int rep=100;
        double errorPercCMS=0;
        double eventsCMS=0;
        double errorPercCS=0;
        double eventsCS=0;
        for (int i = 0; i < rep; i++) {
            Ret[] rets=freqExperiment(cmsEpsilon, cmsDelta, csEpsilon, goalFreq);
            Ret cmsRet = rets[0];
            Ret csRet = rets[1];
            errorPercCMS+=cmsRet.aDouble;
            if (cmsRet.aBoolean){
                eventsCMS++;
            }
            errorPercCS+=csRet.aDouble;
            if (csRet.aBoolean) {
                eventsCS++;
            }
        }
        errorPercCMS/=rep;
        eventsCMS/=rep;
        errorPercCS/=rep;
        eventsCS/=rep;
        System.out.println("Goal frequency "+goalFreq+" with average actual epsilon "+errorPercCMS);
        System.out.println("Excessive error events, compare with delta: "+eventsCMS);
        System.out.println("Goal frequency "+goalFreq+" with average actual epsilon "+errorPercCS);
        System.out.println("Excessive error events, compare with delta: "+eventsCS);
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
