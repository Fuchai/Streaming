import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class CMSTest {
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
		int l = (int) (2/epsilon);
		int k = (int) Math.round(Math.log(1/delta));
		System.out.println("L : " + l);
		System.out.println("k*L : " + k*l);
		
		CMS cms = new CMS(epsilon, delta, s);
		System.out.println("count : " + count);
		int approx = cms.approximateFrequency(4);
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
		int l = (int) (2/epsilon);
		int k = (int) Math.round(Math.log(1/delta));
		System.out.println("L : " + l);
		System.out.println("k*L : " + k*l);
		
		CMS cms = new CMS(epsilon, delta, s);
		System.out.println("count : " + count);
		int approx = cms.approximateFrequency(4);
		System.out.println("approx : " + approx);
	}
	
//	@Test
//	void arraySize7000() {
//		System.out.println("\n" + "arraySize7000");
//		// comparing this with arraySize7000 in CountSketchTest
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
//		float epsilon = (float) 0.000319;
//		float delta = (float) 0.00005;
//		CMS cms = new CMS(epsilon, delta, s);
//		int l = cms.prime;
//		int k = cms.k;
//		System.out.println("L : " + l);
//		System.out.println("k*L : " + k*l); // 7070
//		
//		System.out.println("count : " + count);
//		int approx = cms.approximateFrequency(4);
//		System.out.println("approx : " + approx);
//	}
	
	@Test
	void heavyHitter() {
		System.out.println("\n" + "heavyHitter");
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 100;
		int N = 123;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.00284;
		float delta = (float) 0.02;
		
		CMS cms = new CMS(epsilon, delta, s);
		int[] hh = cms.approximateHH(2*epsilon, epsilon);
		System.out.println("numHeavyHitter : " + hh.length);
		for (int i : s) {
			if (cms.approximateFrequency(i)>=2*epsilon*size)
				assertTrue(arrayContains(hh, i));
			if (cms.approximateFrequency(i)<epsilon*size)
				assertTrue(!arrayContains(hh, i));
		}
		
		double count = 0;
		double countErr = 0;
		for (int i : hh) {
			int approx = cms.approximateFrequency(i);
			if (approx - arr[i] >= epsilon*size)
				count++;
			if (arr[i] < epsilon*size)
				countErr++;
		}
		System.out.println("HeavyHitter Err : " + countErr/hh.length);
		System.out.println("HeavyHitter Estimate Over : " + count/hh.length);
		System.out.println("HeavyHitter ratio : " + (hh.length + 0.0)/N);
	}
	
	// testProbability1 & 2 has same epsilon and delta value as the ones in CountSketchTest
	// testProbability3 has similar size of array
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
		CMS cms = new CMS(epsilon, delta, s);
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =cms.approximateFrequency(i);
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
		CMS cms = new CMS(epsilon, delta, s);
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =cms.approximateFrequency(i);
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
		int N = 411;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.00314;
		float delta = (float) 0.00005;
		CMS cms = new CMS(epsilon, delta, s);
		int l = cms.prime;
		int k = cms.k;
		System.out.println("L : " + l);
		System.out.println("k*L : " + k*l); // 7051
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =cms.approximateFrequency(i);
			double actual=arr[i];
			if (aprox-actual >= epsilon*size) {
				count += 1;
			}
		}
		System.out.println("Estimate Over Prob : " + count/N);
	}
	
	@Test
	void testProbability4() {
		System.out.println("\n" + "testProbability4");
		
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int size = 1000;
		int N = 10000;
		int[] arr = new int[N];
		Arrays.fill(arr, 0);
		for (int i = 0; i < size; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,N);
			arr[rand]++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.00063667;
		float delta = (float) 0.01;
		CMS cms = new CMS(epsilon, delta, s);
		int l = cms.prime;
		int k = cms.k;
		System.out.println("L : " + l);
		System.out.println("k*L : " + k*l);
		
		double count = 0;
		for (int i = 0; i < N; i++) {
			double aprox =cms.approximateFrequency(i);
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
