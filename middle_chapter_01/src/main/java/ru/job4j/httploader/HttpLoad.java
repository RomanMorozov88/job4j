package ru.job4j.httploader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpLoad implements Runnable {

    private URL url;
    private HttpURLConnection connection = null;
    private String downloaded = "./middle_chapter_01/src/main/java/ru/job4j/httploader/";
    private String addres;
    private int limit;

    public HttpLoad(String addres, int limit) {
        this.addres = addres;
        this.limit = limit;
    }

    @Override
    public void run() {
        try {
            this.url = new URL(addres);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            this.connection = (HttpURLConnection) this.url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.connection != null) {
            int last = addres.lastIndexOf('/') + 1;
            File file = new File(this.downloaded + addres.substring(last) + ".html");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try (
                    BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                    FileOutputStream out = new FileOutputStream(file)
            ) {
                byte[] buffer = new byte[1024];
                int loadedCount = 0;
                long startTime = System.currentTimeMillis();
                long timeForSpeed;
                while (in.read(buffer) != -1) {
                    out.write(buffer);
                    loadedCount = loadedCount + buffer.length / 1024;
                    timeForSpeed = System.currentTimeMillis() - startTime;
                    if (timeForSpeed < 1000 && loadedCount == limit) {
                        System.out.println("Download speed is limited.");
                        Thread.sleep(1000);
                        startTime = System.currentTimeMillis();
                        loadedCount = 0;
                    }
                }
                System.out.println("The download is complete.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Not found.");
        }
    }

    public static void main(String[] args) {
        HttpLoad load = new HttpLoad(
                args[0],
                Integer.parseInt(args[1]));
        new Thread(load).start();
    }
}