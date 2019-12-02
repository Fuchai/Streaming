import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class CountSketchTest {
	@Test
	void approximateFrequencies() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		int count = 0;
		int N = 1000;
		for (int i = 0; i < N; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,100);
			if (rand == 4)
				count++;
			s.add(rand);
		}
		
		float epsilon = (float) 0.33333;
		float delta = (float) 0.00005;
		int l = (int) (2/epsilon);
		
		int over = N/l;
		System.out.println(over);
		
		CountSketch countSketch = new CountSketch(epsilon, delta, s);
		System.out.println("count : " + count);
		int approx = countSketch.approximateFrequency(4);
		System.out.println(count + " : " + approx);
	}
}
