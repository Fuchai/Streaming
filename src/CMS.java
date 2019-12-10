import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class CMS {
	
	int l;
	int k;
	int prime;
	float epsilon;
	float delta;
	float q,r;
	ArrayList<Integer> stream;
	HashFunction[] hashFunctions;
	int[][] CMS;
	HashSet<Integer> set = new HashSet<Integer>();
	
	CMS(float epsilon, float delta, ArrayList<Integer> s, float q, float r) {
		this.epsilon = epsilon;
		this.delta = delta;
		this.stream = s;
		this.q = q;
		this.r = r;
		
		l = (int) (2/epsilon)+1;
		k = (int) Math.round(Math.log(1/delta))+1;
		prime = getPrime(l);
		CMS = new int[k][prime];
		for (int i = 0; i < k; i++) {
			Arrays.fill(CMS[i], 0);
		}
		
		hashFunctions = new HashFunctionRan[k];
		for (int i = 0; i < k; i++) {
			hashFunctions[i] = new HashFunctionRan(l);
		}
		
		processCMS();
	}
	
	void processCMS() {		
		for (Integer x : stream) {
			for (int i = 0; i < k; i++) {
				int hashValue = hashFunctions[i].hash(x + "");
				CMS[i][hashValue]++;
			}
			if (!set.contains(x))
				set.add(x);
		}
	}
	
	int approximateFrequency(int x) {
		int approx = getMinValue(x);
		
		return approx;
	}
	
	int getMinValue(int x) {
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
        	int hashValue = hashFunctions[i].hash(x+"");
            if (CMS[i][hashValue] < minValue) 
            	minValue = CMS[i][hashValue];
        }
        return minValue ;
    }
	
	int[] approximateHH() {
		// assume q >= r + epsilon
		ArrayList<Integer> list = new ArrayList<>();
		for (Integer x : set) {
			int fx = approximateFrequency(x);
			if (fx >= q*stream.size() && !(fx < r*stream.size()))
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
