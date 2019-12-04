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
	
	// for both approximateFrequencies2 and arraySize7000
	// CMS does not give correct value while CountSketch does
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
	
	@Test
	void arraySize7000() {
		System.out.println("\n" + "arraySize7000");
		// comparing this with arraySize7000 in CountSketchTest
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
		
		float epsilon = (float) 0.00284;
		float delta = (float) 0.00005;
		CMS cms = new CMS(epsilon, delta, s);
		int l = cms.prime;
		int k = cms.k;
		System.out.println("L : " + l);
		System.out.println("k*L : " + k*l); // 7070
		
		System.out.println("count : " + count);
		int approx = cms.approximateFrequency(4);
		System.out.println("approx : " + approx);
	}
	
	@Test
	void heavyHitter() {
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
		
		float epsilon = (float) 0.013333;
		float delta = (float) 0.00005;
		
		CMS cms = new CMS(epsilon, delta, s);
		int[] hh = cms.approximateHH(2*epsilon, epsilon);
		System.out.println("numHeavyHitter : " + hh.length);
		for (int i : s) {
			if (cms.approximateFrequency(i)>=2*epsilon*size)
				assertTrue(arrayContains(hh, i));
			if (cms.approximateFrequency(i)<epsilon*size)
				assertTrue(!arrayContains(hh, i));
		}
		
		for (int i : hh) {
			int approx = cms.approximateFrequency(i);
			System.out.println("count : " + arr[i] + " approx : " + approx);
		}
	}
	
	@Test
	void testProbability() {
		for (int j = 0; j <100; j++) {

		}
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
		System.out.println("Prob : " + count/N);
	}
	
	boolean arrayContains(int[] arr, int x) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == x)
				return true;
		}
		
		return false;
	}
}
