import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ThreadLocalRandom;


class ATest {

    @Test
    void receive() {
        A a = new A();
        long actualF2=0;
        for (int i = 0; i < 100000; i++) {
            int insert = ThreadLocalRandom.current().nextInt(0, 1000000);
            int freq = ThreadLocalRandom.current().nextInt(0, 5);
//            int freq=1;
            for (int j = 0; j < freq; j++) {
                a.receive(insert);
//                System.out.println(a.hash(insert));
//                System.out.println();
            }
            actualF2+=freq*freq;
        }
        System.out.println(a.output());
        System.out.println(actualF2);
    }

    @Test
    void hash() {
        A a = new A();
        int neg=0;
        int pos=0;
        for (int i = 0; i < 10000; i++) {
            int freq = ThreadLocalRandom.current().nextInt(0, 1000);
            int ret= a.hash(freq);
            if (ret!=-1 && ret!=1){
                System.out.println(ret);
                throw new ValueException("WHAT");
            }
            if (ret==1){
                pos++;
            }else{
                neg++;
            }
        }
        System.out.println(pos);
        System.out.println(neg);
    }

    @Test
    void whatthehash(){
        A a = new A();
        for (int i = 0; i <10; i++) {
//            int freq = ThreadLocalRandom.current().nextInt(0, 1000);
            System.out.println(a.hash(i));
        }
    }
}