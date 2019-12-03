import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class CountSketchTest {
	
	@Test
	void approximateFrequencyMedian() {
		System.out.println("\n" + "approximateFrequencyMedian");
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int count = 0;
		int size = 1000;
		int N = 411;
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			if (rand == 4)
				count++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.0186667;
		float delta = (float) 0.00005;
		int l = (int) (3/Math.pow(epsilon,2.0));
		System.out.println("L : " + l);
		int k = (int) Math.round(Math.log(1/delta));
		
		CountSketch countSketch;
		int sum = 0;
		for (int i = 0; i < k; i++) {
			countSketch = new CountSketch(epsilon, delta, s);
			int approx = countSketch.approximateFrequency(4);
			sum += approx;
//			System.out.println(approx);
		}
		
		System.out.println("count : " + count);
		System.out.println("median estimate : " + sum/k);
	}
	
	@Test
	void approximateFrequencies1() {
		System.out.println("\n" + "approximateFrequencies1");
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int count = 0;
		int size = 1000;
		int N = 411;
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			if (rand == 4)
				count++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.013333;
		float delta = (float) 0.00005;
		int l = (int) (3/Math.pow(epsilon,2.0));
		System.out.println("L : " + l);
		
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		int approx = countSketch.approximateFrequency(4);
		
		System.out.println("count : " + count);
		System.out.println("approx : " + approx);
	}
	
	@Test
	void approximateFrequencies2() {
		System.out.println("\n" + "approximateFrequencies2");
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int count = 0;
		int size = 1000;
		int N = 411;
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			if (rand == 4)
				count++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.0186667;
		float delta = (float) 0.00005;
		int l = (int) (3/Math.pow(epsilon,2.0));
		System.out.println("L : " + l);
		
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		int approx = countSketch.approximateFrequency(4);
		
		System.out.println("count : " + count);
		System.out.println("approx : " + approx);
	}
	
	@Test
	void arraySize7000() {
		System.out.println("\n" + "arraySize7000");
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int count = 0;
		int size = 1000;
		int N = 411;
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			if (rand == 4)
				count++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.0206667;
		float delta = (float) 0.00005;
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		int l = countSketch.prime;
		System.out.println("L : " + l); // 7027
		
		int approx = countSketch.approximateFrequency(4);
		
		System.out.println("count : " + count);
		System.out.println("approx : " + approx);
	}
	
	@Test
	void heavyHitter() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 1000;
		int N = 411;
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			s.add(rand);
		}
		
		float epsilon = (float) 0.013333;
		float delta = (float) 0.00005;
		
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		int[] hh = countSketch.approximateHH(2*epsilon, epsilon);
		for (int i : s) {
			if (countSketch.approximateFrequency(i)>=2*epsilon*size)
				assertTrue(arrayContains(hh, i));
			if (countSketch.approximateFrequency(i)<epsilon*size)
				assertTrue(!arrayContains(hh, i));
		}
	}
	
	boolean arrayContains(int[] arr, int x) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == x)
				return true;
		}
		
		return false;
	}
}
