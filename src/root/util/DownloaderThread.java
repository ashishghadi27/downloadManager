package root.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static java.lang.Thread.MAX_PRIORITY;

public class DownloaderThread implements Runnable {

    private long startFrom, endAt;
    private RandomAccessFile file;
    private URL url;
    private int arraySize;

    public DownloaderThread(long startFrom, long endAt, URL url, File file, int arraySize) throws FileNotFoundException {
        this.startFrom = startFrom;
        this.endAt = endAt;
        this.file = new RandomAccessFile(file, "rw");
        this.url = url;
        this.arraySize = arraySize;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(MAX_PRIORITY);
        System.out.println(Thread.currentThread().getName() + " -> " + " Download Started");
        HttpURLConnection uc = null;
        try{
            uc = (HttpURLConnection) url.openConnection();
            uc.setRequestProperty("Range", "bytes="+startFrom+"-"+endAt);
            uc.connect();
            System.out.println("Start: " + startFrom + " End: " + endAt);
            InputStream inputStream = uc.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[(int)(endAt - startFrom) < arraySize ? (int)(endAt - startFrom) : arraySize];
            file.seek(startFrom);
            while( (bytesRead = inputStream.read(buffer) ) != -1 ) {
                file.write(buffer, 0, bytesRead);
            }
        }
        catch (IOException e){
            System.out.println("Exception in " + Thread.currentThread().getName() + "\t Exception = " + e );
        }
        finally {
            Objects.requireNonNull(uc).disconnect();
        }
    }

}
