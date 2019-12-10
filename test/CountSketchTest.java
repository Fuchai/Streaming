import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class CountSketchTest {
		
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
	
//	@Test
//	void arraySize7000() {
//		System.out.println("\n" + "arraySize7000");
//		ArrayList<Integer> s = new ArrayList<Integer>();
//		
//		int count = 0;
//		int size = 1000;
//		int N = 411;
//		for (int i = 0; i < size; i++) {
//			int rand = ThreadLocalRandom.current().nextInt(0,N);
//			if (rand == 4)
//				count++;
//			s.add(rand);
//		}
//		
//		float epsilon = (float) 0.0206667;
//		float delta = (float) 0.00005;
//		CountSketch countSketch = new CountSketch(epsilon, delta, s);
//		int l = countSketch.prime;
//		System.out.println("L : " + l); // 7027
//		
//		int approx = countSketch.approximateFrequency(4);
//		
//		System.out.println("count : " + count);
//		System.out.println("approx : " + approx);
//	}
	
//	@Test
//	void heavyHitter() {
//		System.out.println("\n" + "heavyHitter");
//		ArrayList<Integer> s = new ArrayList<Integer>();
//		
//		int size = 1000;
//		int N = 411000;
//		int[] arr = new int[N];
//		for (int i = 0; i < size; i++) {
//			int rand = ThreadLocalRandom.current().nextInt(0,N);
//			arr[rand]++;
//			s.add(rand);
//		}
//		
//		float epsilon = (float) 0.13333;
//		float delta = (float) 0.005;
//		
//		CountSketch countSketch = new CountSketch(epsilon, delta, s);
//		int[] hh = countSketch.approximateHH(2*epsilon, epsilon);
//		System.out.println("numHeavyHitter : " + hh.length);
//		for (int i : s) {
//			if (countSketch.approximateFrequency(i)>=2*epsilon*size)
//				assertTrue(arrayContains(hh, i));
//			if (countSketch.approximateFrequency(i)<epsilon*size)
//				assertTrue(!arrayContains(hh, i));
//		}
//		
//		double count = 0;
//		for (int i : hh) {
//			int approx = countSketch.approximateFrequency(i);
//			if (approx == arr[i])
//				count++;
//		}
//		System.out.println("Prob : " + count/hh.length);
//	}
	
	@Test
	void testProbability1() {
		System.out.println("\n" + "testProbability1");
		
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 1000;
		int N = 1000000;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.001;
		float delta = (float) 0.01;
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =countSketch.approximateFrequency(i);
			double actual=arr[i];
			if (aprox-actual >= epsilon*size) {
				count += 1;
			}
		}
		System.out.println("Estimate Over Prob : " + count/N);
	}
	
	@Test
	void testProbability2() {
		System.out.println("\n" + "testProbability2");
		
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 1000;
		int N = 1000000;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.0001;
		float delta = (float) 0.01;
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =countSketch.approximateFrequency(i);
			double actual=arr[i];
			if (aprox-actual >= epsilon*size) {
				count += 1;
			}
		}
		System.out.println("Estimate Over Prob : " + count/N);
	}
	
	@Test
	void testProbability3() {
		System.out.println("\n" + "testProbability3");
		
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 1000;
		int N = 1000000;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.0206667;
		float delta = (float) 0.00005;
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		int l = countSketch.prime;
		System.out.println("L : " + l); // 7027
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =countSketch.approximateFrequency(i);
			double actual=arr[i];
			if (aprox-actual >= epsilon*size) {
				count += 1;
			}
		}
		System.out.println("Estimate Over Prob : " + count/N);
	}
	
	boolean arrayContains(int[] arr, int x) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == x)
				return true;
		}
		
		return false;
	}
}
