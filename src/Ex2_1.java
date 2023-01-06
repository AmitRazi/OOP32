
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {
    private static Random rand;

    /**
     * Creates text files and fills them with a random number of lines of "Hello Word".
     * @param n - The number of files to user wishes to create.
     * @param seed - seed for the geneartion of "random" numbers.
     * @param bound - The largest integer the random number generator may return.
     * @return A string array with the file names of all the files created.
     */
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

    /**
     * Helper function for createTextFiles. It fills the text files with the generated random number of lines.
     * @param file - Text file to be filled.
     * @param bound - The largest integer the random number generator may return.
     */
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

    /**
     *This function performs the count on a single thread - the thread of the caller.
     * @param fileNames - String array of file names to be counted.
     * @return Summation of all the lines.
     */
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

    /**
     *This function performs the count on fileNames.length number of threads, using instances of LineReaderThread.
     * @param fileNames - String array of file names to be counted.
     * @return Summation of all the lines.
     */
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

    /**
     *This function uses to ExecutorService to initiate a fixed ThreadPool of size fileNmaes.length.
     * Instances of the class CallableLineReader are submited to the Executor.
     * @param fileNames - String array of file names to be counted.
     * @return Summation of all the lines.
     */
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
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        service.shutdown();
    return sumOfLines;
    }
}
