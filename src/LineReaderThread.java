import java.io.*;

public class LineReaderThread extends Thread{
    String fileName;
    private volatile int  count;

    public LineReaderThread(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void run(){
        count = 0;
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);
            while(reader.readLine() != null) count++;
            reader.close();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount(){
        return this.count;
    }
}
