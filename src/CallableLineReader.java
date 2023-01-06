import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * This class counts the number of lines in a text file.
 * It implements Callable to allow the operation to be performed on a branching thread, and return a value.
 */
public class CallableLineReader implements Callable<Integer> {
    private final String fileName;

    /**
     * Parametrized Constructor
     * @param fileName - The name of the text file.
     */
    public CallableLineReader(String fileName){
        this.fileName= fileName;
    }

    /**
     *The function the thread activates. It opens the file with the matching the fileName provided in the constructor,
     * counts the lines, and returns the result.
     * @return Returns the number of lines in the text file.
     */
    @Override
    public Integer call()  {
        FileReader file;
        Integer value = 0;
        try {
            file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            while(reader.readLine() != null) value++;
            reader.close();
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return value;
    }
}
