import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

public class CMSTest {
	@Test
	void approximateFrequencies() {
		ArrayList<Integer> s = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0,100);
			s.add(rand);
		}
		int epsilon;
		int delta;
		CMS cms = new CMS(epsilon, delta, s);
	}
}
