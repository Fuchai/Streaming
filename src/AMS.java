import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class AMS {
    public double secondFreqMoment(ArrayList<Integer> s, float epsilon, float delta) {
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

class A {
    long c = 0;
    int seed;
    int a;
    int b;
    int permSize=179;
    Permutation perm= new Permutation(permSize);

    public A() {
        this.seed = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        this.a = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        this.b = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE/1024);
//        System.out.println(this.seed);
    }

    void receive(int num) {
        c += hash(num);
//        System.out.println(c);
    }

    long output() {
        return c * c;
    }

//    int hash1(int num) {
//        String nn = "@" + num + "!";
////        System.out.println(hs);
//        int ret = (int) (Math.floorMod(FNV64.hash(nn), 2) * 2 - 1);
////        System.out.println(ret);
//        return ret;
//    }


    int hash(int num){
        Integer nn= new Integer(a*num+b);
        int ret=Math.floorMod(nn, permSize);
        int to=perm.to(ret);
        ret=Math.floorMod(to,2)*2-1;
        return ret;
    }

//    int hash(int num){
////        int ret =  num*(int) 2654435761L;
//        int res=1;
//        for (int i = 0; i < 32; i++) {
//            res=res^(num >> i) & 1;
//        }
//        int ret= res*2-1;
//        return ret;
//    }

//    int hash(int num){
//        Integer nn= new Integer(num+b);
////        System.out.println(hs);
//        boolean ret=(nn.hashCode() & 1)>0;
//        if (ret){
//            return 1;
//        }else{
//            return -1;
//        }
//    }

//    int hash(int num){
//        return Math.floorMod(num*a+b, 2)*2 -1;
//    }
}

class B {
    int k;
    A[] as;

    public B(int k) {
        this.k = k;
        as = new A[k];
        for (int i = 0; i < k; i++) {
            as[i] = new A();
        }
    }

    void receive(int num) {
        for (int i = 0; i < k; i++) {
            as[i].receive(num);
        }
    }

    double output() {
        double c = 0;
        for (int i = 0; i < k; i++) {
            c += as[i].output();
//            System.out.println(as[i].output());
        }
        return c / k;
    }
}