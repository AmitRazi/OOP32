import javax.annotation.processing.Filer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
       String[] str = Ex2_1.createTextFiles(5,2,1000);
        System.out.println(Arrays.toString(str));
        long start1 = System.currentTimeMillis();
        System.out.println(Ex2_1.getNumOfLines(str));
        long total1 = System.currentTimeMillis()-start1;
        System.out.println(total1+" mili seconds");
        long start2 = System.currentTimeMillis();
        System.out.println(Ex2_1.getNumOfLinesThreads(str));
        long total2 = System.currentTimeMillis()-start2;
        System.out.println(total2+" mili seconds");
        long start3 = System.currentTimeMillis();
        System.out.println(Ex2_1.getNumOfLinesThreadPool(str));
        long total3 = System.currentTimeMillis()-start3;
        System.out.println(total3+" mili seconds");

    }
}