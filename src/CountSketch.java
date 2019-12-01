import java.util.ArrayList;

public class CountSketch {
    float epsilon;
    float delta;
    ArrayList<Integer> s;

    public CountSketch(float epsilon, float delta, ArrayList<Integer> s) {
        this.epsilon = epsilon;
        this.delta = delta;
        this.s = s;
    }

    double approximateFrequency(int x){
        return 0;
    }

}
