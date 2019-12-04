import java.util.concurrent.ThreadLocalRandom;

class A {
    int c = 0;
    int offset;
    long a, b, d, e;
    int permSize=157;
//    int permSize=17;
//    boolean[] lookup;
//    int lookupSize;
    Permutation perm;
    long prime;

    public A() {
        this.offset = ThreadLocalRandom.current().nextInt(0, 2);
        this.a = ThreadLocalRandom.current().nextLong();
        this.b = ThreadLocalRandom.current().nextLong();
        this.d = ThreadLocalRandom.current().nextLong();
        this.e = ThreadLocalRandom.current().nextLong();
        this.prime =getPrime(10000000);

//        perm= new Permutation(permSize);

//        lookupSize=ThreadLocalRandom.current().nextInt(512, 1024);
//        lookup=new boolean[lookupSize];
//        for (int i = 0; i < lookupSize; i++) {
//            lookup[i]=ThreadLocalRandom.current().nextBoolean();
//        }
//        System.out.println(this.seed);
//        System.out.println("init");
    }

    void receive(int num) {
        c += hash(num);
//        System.out.println(c);
    }

    int output() {
        return c * c;
    }

//    int hash1(int num) {
//        String nn = "@" + num + "!";
////        System.out.println(hs);
//        int ret = (int) (Math.floorMod(FNV64.hash(nn), 2) * 2 - 1);
////        System.out.println(ret);
//        return ret;
//    }

    // lecture 4-independent hash
    int hash(int num) {
        long nn=(a*num*num*num+b*num*num+d*num+e)%prime;
        int ret= (int) ((Math.floorMod(nn,2))*2-1);
        return ret;
    }
//
//    // good hash
//    int hash(int num){
//        long nn=(a*num-b);
//        int ret= (int) Math.floorMod(nn, permSize);
//        ret=perm.to(ret);
//        ret=(Math.floorMod(ret,2))*2-1;
////        ret=(Math.floorMod(to,2))*2-1;
//        return ret;
//    }
//
//    // good hash
//    int hash(int num){
//        Integer nn= new Integer(a*num-b);
//        int ret=Math.floorMod(nn, permSize);
//        ret=perm.to(ret);
//        ret=(Math.floorMod(ret,2))*2-1;
////        ret=(Math.floorMod(to,2))*2-1;
//        return ret;
//    }
//    int hash(int num){
//        return (Math.floorMod(num*a-b, 2)*2)-1;
//    }

//    int hash(int num){
////        Integer nn= new Integer(a*num+b);
//        int ret=Math.floorMod(num, lookupSize);
//        boolean to=lookup[ret];
//        if (to){
//            return 1;
//        }else{
//            return -1;
//        }
//    }

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
    /**
     * get prime number bigger than n
     *
     * @param n
     * @return boolean
     */
    private int getPrime(int n) {
        boolean found = false;

        while (!found) {
            if (isPrime(n)) {
                found = true;
            } else {
                if (n == 1 || n % 2 == 0) {
                    n = n + 1;
                } else {
                    n = n + 2;
                }
            }
        }
        return n;
    }

    /**
     * return true if inputNum is prime
     *
     * @param inputNum
     * @return boolean
     */
    private boolean isPrime(int inputNum) {
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3;
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2;
        return inputNum % divisor != 0;
    }
}
