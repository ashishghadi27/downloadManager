package root.util;


import root.config.ThreadPoolCreator;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class InitiateDownload {

    private String url;

    public InitiateDownload(String url) {
        this.url = url;
        init();
    }

    private void init(){
        HttpURLConnection uc = null;
        try {
            URL url1 = new URL(url);
            uc = (HttpURLConnection) url1.openConnection();

            long fileSize = getFileSize(uc);
            String fileType = getFileType(uc);
            if(fileType.contains("iso")) fileType = "iso";
            String fileName = getFileName(uc, fileType);
            System.out.println("File Name: " + fileName + " FileType: " + fileType);
            File file = createFile(fileName);
            new ThreadPoolCreator(getNumberOfThreadsToBeCreated(uc) ,fileSize, url1, file, getOptimumBufferSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            Objects.requireNonNull(uc).disconnect();
        }

    }

    private int getNumberOfThreadsToBeCreated(HttpURLConnection uc){
        int n = 1;
        try {
            boolean support = uc.getHeaderField("Accept-Ranges").equals("bytes");
            if(support)
                n = getCores() - 1;
            return n;
        }
        catch (NullPointerException e) {
            return n;
        }
    }

    private long getFileSize(HttpURLConnection uc) {
        return uc.getContentLengthLong();
    }

    private String getFileType(HttpURLConnection uc){
        return uc.getContentType().split("/")[1];
    }

    private String getFileName(HttpURLConnection uc, String fileType){
        String fileName = uc.getHeaderField("Content-Disposition");
        if(fileName != null && fileName.contains("filename=")){
            fileName = fileName.substring(fileName.indexOf("filename=") + 9);
        }
        else fileName = fileType + "_download_" + System.currentTimeMillis() + "." + fileType;
        return fileName;
    }

    private File createFile(String fileName){
        return new File(fileName);
    }

    private int getCores(){
       return Runtime.getRuntime().availableProcessors();
    }

    private int getOptimumBufferSize(){
        long freeMemory = getFreeMemory();
        return (int)(freeMemory / getCores());
    }

    private long getFreeMemory(){
        return Runtime.getRuntime().freeMemory();
    }
}
