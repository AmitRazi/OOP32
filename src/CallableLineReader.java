import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.Callable;

public class CallableLineReader implements Callable<Integer> {
    private String fileName;

    public CallableLineReader(String fileName){
        this.fileName= fileName;
    }
    @Override
    public Integer call() throws Exception {
       FileReader file = new FileReader(fileName);
       BufferedReader reader = new BufferedReader(file);
       Integer value = 0;
       while(reader.readLine() != null) value++;
       reader.close();
       file.close();
       return value;
    }
}
