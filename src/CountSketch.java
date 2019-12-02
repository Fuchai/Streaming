import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class CountSketch {
	
	int k;
	int l;
	float epsilon;
	float delta;
	ArrayList<Integer> stream;
	HashFunction hashFunction;
	SignHash signHash;
	int[] countSketch;
	HashSet<Integer> set = new HashSet<Integer>();
	
	CountSketch(float epsilon, float delta, ArrayList<Integer> s) {
		this.epsilon = epsilon;
		this.delta = delta;
		this.stream = s;

		l = (int) (2/epsilon);
		k = (int) Math.round(Math.log(1/delta));
		
		countSketch = new int[getPrime(l)];
		Arrays.fill(countSketch, 0);
		processCountSketch();
	}
	
	void processCountSketch() {
		for (Integer x : stream) {
			int h = hashFunction.hash(x + "");
			int g = signHash.hash(x + "");
			countSketch[h]+=g;
			if (!set.contains(x))
				set.add(x);
		}
	}
	
	int approximateFrequency(int x) {
		return countSketch[hashFunction.hash(x + "")]*signHash.hash(x + "");
	}
	
	int[] approximateHH(float q, float r) {
		ArrayList<Integer> list = new ArrayList<>();
		for (Integer x : set) {
			int fx = approximateFrequency(x);
			if (fx >= q*stream.size() && fx > r*stream.size())
				list.add(x);
		}
		int[] ret = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			ret[i] = list.get(i);
		return ret;
	}
	
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

class SignHash extends HashFunction {
	int a;
    int b;
	
	public SignHash() {
		this.a=ThreadLocalRandom.current().nextInt(0, 2);
        this.b=ThreadLocalRandom.current().nextInt(0, 2);
	}
	
	@Override
	public int hash(String s) {
        return hash(s.hashCode());
    }

    private int hash(int x) {
    	int ret = mod((a * x) + b, 2);
    	if (ret == 0)
    		ret = -1;
        return ret;
    }
}