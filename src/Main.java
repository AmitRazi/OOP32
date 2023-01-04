
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
       String[] str = new String[10000];
       for(int i = 0 ; i < 10000 ; i++){
           str[i] = String.format("file_%d.txt",i);
       }
       long averageTime1[] = new long[50];
        long averageTime2[] = new long[50];
    for(int i = 0 ; i < 50 ; i++) {
        long start1 = System.currentTimeMillis();
        System.out.println(Ex2_1.getNumOfLinesThreadPool(str));
        long total1 = System.currentTimeMillis() - start1;
        System.out.println(total1 + " mili seconds");
        averageTime1[i]=total1;
    }
    int sum = 0;
    for(long i: averageTime1)
        sum+=i;
        System.out.println(sum/50);

    }
}