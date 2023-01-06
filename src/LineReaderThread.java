import java.io.*;

/**
 * This class counts the number of lines in a text file.
 * It extends Thread to allow the operation to be performed on a branching thread.
 * To allow the caller to retrieve the number of lines, the class also a variable count, and a function getCount().
 */
public class LineReaderThread extends Thread{
    String fileName;
    private int  count;
    private boolean isDone = false;

    /**
     * Parametrized Constructor
     * @param fileName - The name of the text file.
     */
    public LineReaderThread(String fileName){
        this.fileName = fileName;
    }

    /**
     *The function the thread activates. It opens the file with the matching the fileName provided in the constructor,
     * counts the lines, and updates the class variable "count".
     */
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
        isDone = true;
    }

    /**
     * @return Returns the number of lines if the count has already been performed, otherwise, return -1;
     */
    public int getCount(){
        if(isDone == false) return -1;
        return this.count;
    }
}
