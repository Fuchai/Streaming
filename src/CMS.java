import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CMS {
	
	int streamSize;
	float epsilon;
	float delta;
	ArrayList<Integer> stream;
	HashFunction[] hashFunctions;
	int[][] CMS;
	HashSet<Integer> set = new HashSet<Integer>();
	
	CMS(float epsilon, float delta, ArrayList<Integer> s) {
		this.epsilon = epsilon;
		this.delta = delta;
		this.stream = s;
		streamSize = approximateStreamSize();
		
		int k = (int) Math.round(Math.log(1/delta));
		
		CMS = processCMS(k);
		hashFunctions = new HashFunctionRan[k];
		for (int i = 0; i < k; i++) {
			hashFunctions[i] = new HashFunctionRan(streamSize);
		}
	}
	
	int approximateStreamSize() {
		return (int) (2/epsilon);
	}
	
	int[][] processCMS(int k) {
		int[][] ret = new int[k][getPrime(streamSize)];
		
		for (int i = 0; i < k; i++) {
			Arrays.fill(ret[i], 0);
		}
		
		for (int i = 0; i < streamSize; i++) {
			int x = stream.get(i);
			int hashValue = hashFunctions[i].hash(x + "");
			ret[i][hashValue]++;
			if (!set.contains(x))
				set.add(x);
		}
		
		return ret;
	}
	
	int approximateFrequency(int x) {
		int approx = getMinValue(x);
		
		return approx;
	}
	
	int getMinValue(int x) {
        int minValue = CMS[0][0];
        for (int i = 0; i < CMS.length; i++) {
        	int hashValue = hashFunctions[i].hash(x+"");
            
            if (CMS[i][hashValue] == 0)
            	continue;
            	
            if (CMS[i][hashValue] < minValue) 
            	minValue = CMS[i][hashValue];
        }
        return minValue ;
    }
	
	int[] approximateHH(float q, float r) {
		ArrayList<Integer> list = new ArrayList<>();
		for (Integer x : set) {
			int fx = approximateFrequency(x);
			if (fx >= q*streamSize && fx > r*streamSize)
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
