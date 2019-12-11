import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DimRed {
    @Test
    void reduce(){
        String folderPath = "D:\\Git\\MultisetJaccard\\resources\\space";
        MinHash min = new MinHash(folderPath, 400);
        float epsilon = 0.05f;
        float delta =0.05f;

        int[][] tdm=min.termDocumentMatrix;
        Vector[] reduced=new Vector[tdm.length];
        Vector[] original=new Vector[tdm.length];

        for (int i = 0; i < tdm.length; i++) {
            int[] tdf= tdm[i];
            float[] content= new float[tdf.length];
            for (int j = 0; j < tdf.length; j++) {
                content[j]=tdf[j];
            }
            Vector vec = new Vector(content);
            original[i]=vec;
            Vector estimated=AMSDimRed.reduceDim(vec, epsilon, delta);
            reduced[i]=estimated;
        }

        float totalEpsilon=0;
        float event=0;
        for (int i = 0; i < tdm.length; i++) {
            for (int j = 0; j < tdm.length; j++) {
                float originalDistance;
                float reducedDistance;
                originalDistance=original[i].distance(original[j]);
                reducedDistance=reduced[i].distance(reduced[j]);
                reducedDistance=reducedDistance*reducedDistance;
                originalDistance=originalDistance*originalDistance;
                float estimationDiff=Math.abs(reducedDistance-originalDistance);
                float actualEpsilon;
                if (originalDistance!=0){
                    actualEpsilon=estimationDiff/originalDistance;
                    totalEpsilon+=actualEpsilon;
                    if (actualEpsilon>epsilon){
                        event++;
                    }
                }
            }
        }
        totalEpsilon/=(tdm.length*tdm.length);
        event/=(tdm.length*tdm.length);
        System.out.println("Experiment epsilon: "+totalEpsilon);
        System.out.println("Experiment delta: "+event);

        float[] oz= new float[tdm[0].length];
        int k =  (int) (Math.ceil(15 / epsilon / epsilon)*Math.log(1/delta)) / 4;
        float[] rz= new float[k];
        Arrays.fill(oz,0);
        Arrays.fill(rz, 0);
        Vector originalZero = new Vector(oz);
        Vector reducedZero = new Vector(rz);

        event=0;
        for (int i = 0; i < tdm.length; i++) {
            float originalDistance;
            float reducedDistance;
            originalDistance=original[i].distance(originalZero);
            reducedDistance=reduced[i].distance(reducedZero);
            reducedDistance=reducedDistance*reducedDistance;
            originalDistance=originalDistance*originalDistance;
            float estimationDiff=Math.abs(reducedDistance-originalDistance);
            float actualEpsilon;
            if (originalDistance!=0){
                actualEpsilon=estimationDiff/originalDistance;
                totalEpsilon+=actualEpsilon;
                if (actualEpsilon>epsilon){
                    event++;
                }
            }
        }
        totalEpsilon/=(tdm.length*tdm.length);
        event/=(tdm.length*tdm.length);
        System.out.println("Experiment epsilon: "+totalEpsilon);
        System.out.println("Experiment delta: "+event);
    }
}
