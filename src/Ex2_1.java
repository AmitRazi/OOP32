
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {
    static Random rand;
    public static String[] createTextFiles(int n, int seed, int bound){
        rand = new Random(seed);
        String[] arr = new String[n];
        for(int i = 0 ; i < n ; i++) {
           File file = new File(String.format("file_%d.txt", i));
            fillTextFiles(file,bound);
           arr[i] = file.getName();

        }
        return arr;
    }

    private static void fillTextFiles(File file, int bound){
        try {
            FileWriter writer = new FileWriter(file);
            int lines = rand.nextInt(bound);
            for(int i = 0 ; i < lines ; i++){
                if(i == lines -1){
                    writer.append("Hello world");
                    break;
                }
                writer.append("Hello world\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static int getNumOfLines(String[] fileNames){
        int sumOfLines = 0;
        for(int i = 0 ; i < fileNames.length ; i++){
            try {
                FileReader file = new FileReader(fileNames[i]);
                BufferedReader reader = new BufferedReader(file);
                while(reader.readLine() != null) sumOfLines++;
                reader.close();
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sumOfLines;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        int sumOfLines = 0;
        LineReaderThread[] threads = new LineReaderThread[fileNames.length];
        for(int i = 0 ; i < fileNames.length ; i++){
            threads[i] = new LineReaderThread(fileNames[i]);
            threads[i].start();
    }
        for(int i = 0 ; i < fileNames.length ; i++){
            try {
                threads[i].join();
                sumOfLines+= threads[i].getCount();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    return sumOfLines;
    }

    public static int getNumOfLinesThreadPool(String[] fileNames){
        int sumOfLines = 0;
        ExecutorService service = Executors.newFixedThreadPool(fileNames.length);
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0 ; i < fileNames.length ; i++){
           Future<Integer> future = service.submit(new CallableLineReader(fileNames[i]));
           futures.add(future);
        }

        for(int i = 0 ; i < fileNames.length ; i++){
            try {
                sumOfLines+=futures.get(i).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        service.shutdown();
    return sumOfLines;
    }
}
