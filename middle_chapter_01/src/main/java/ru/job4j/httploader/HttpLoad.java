package ru.job4j.httploader;

import com.sun.scenario.Settings;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class HttpLoad implements Runnable {

    private final String address;
    private final int limit;

    public HttpLoad(String address, int limit) {
        this.address = address;
        this.limit = limit;
    }

    @Override
    public void run() {
        URL url = null;
        HttpURLConnection connection;
        String downloaded;
        File file;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            if (url != null) {
                connection = (HttpURLConnection) url.openConnection();
                if (connection != null) {
                    downloaded = this.getPathFromPropertise("forload");
                    file = this.createFile(downloaded);
                    this.download(connection, file);
                } else {
                    System.out.println("Not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка фаила.
     *
     * @param connection
     * @param file
     */
    private void download(HttpURLConnection connection, File file) throws IOException {
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
        }
    }

    /**
     * Создаёт фаил в который будет запись.
     *
     * @param downloaded
     * @return
     * @throws IOException
     */
    private File createFile(String downloaded) throws IOException {
        File result;
        int last = address.lastIndexOf('/') + 1;
        result = new File(downloaded + address.substring(last) + ".html");
        if (!result.exists()) {
            result.createNewFile();
        }
        return result;
    }

    /**
     * Получаем пути для файлов ответов и лога
     * из consoleChat.properties.
     *
     * @param key
     * @return
     * @throws IOException
     */
    private String getPathFromPropertise(String key) throws IOException {
        Properties prs = new Properties();
        ClassLoader loader = Settings.class.getClassLoader();
        try (InputStream in = loader.getResourceAsStream(
                "loaderprops/loader.properties")) {
            prs.load(in);
        }
        return prs.getProperty(key);
    }

    public static void main(String[] args) {
        HttpLoad load = new HttpLoad(
                args[0],
                Integer.parseInt(args[1]));
        new Thread(load).start();
    }
}