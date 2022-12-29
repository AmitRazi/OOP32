import java.io.*;
import java.nio.Buffer;

public class LineReaderThread extends Thread{
    String fileName;
    private volatile int value;

    public LineReaderThread(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void run(){
        value = 0;
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            while(reader.readLine() != null) value++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue(){
        return this.value;
    }
}
