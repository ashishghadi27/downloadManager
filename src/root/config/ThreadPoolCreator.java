package root.config;

import root.util.DownloaderThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolCreator {

    public ThreadPoolCreator(int n, long fileSize, URL url, File file, int bufferSize) {
        try {
            createPool(n, fileSize, url, file, bufferSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createPool(int n, long fileSize, URL url, File file, int bufferSize) throws FileNotFoundException {
        long startFrom = 0;
        long range = fileSize / n;
        long endAt = range;
        System.out.println("FileSize: " + fileSize);
        ExecutorService executor = Executors.newFixedThreadPool(n);
        int i = 0;
        for (i = 0; i < n; i++) {
            Runnable worker = null;
            worker = new DownloaderThread(startFrom, endAt, url, file, bufferSize);
            startFrom = endAt + 1;
            endAt += range;
            executor.execute(worker);
            if(endAt > fileSize){
                endAt = fileSize;
                break;
            }
        }
        if(startFrom < fileSize && i < n){
            Runnable worker = new DownloaderThread(startFrom, endAt, url, file, bufferSize);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) { }
        System.out.println(System.currentTimeMillis());
        System.out.println("Download Completed!");
    }
}