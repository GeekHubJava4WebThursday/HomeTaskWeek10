package org.geekhub;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents worker that downloads image from URL to specified folder.<br/>
 * Name of the image will be constructed based on URL. Names for the same URL will be the same.
 */
public class ImageTask implements Runnable {

    public static final int BUFFER_SIZE = 8 * 1024;

    private URL url;
    private String folder;

    public ImageTask(URL url, String folder) {
        this.url = url;
        this.folder = folder;
    }

    /**
     * Inherited method that do main job - downloads the image and stores it at specified location
     */
    @Override
    public void run() {
        Path path = Paths.get(folder, buildFileName(url));
        try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream(), BUFFER_SIZE);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             FileOutputStream file = new FileOutputStream(path.toFile())){
            int bytes;
            while((bytes = inputStream.read()) >= 0) {
                outputStream.write(bytes);
            }
            outputStream.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //converts URL to unique file name
    private String buildFileName(URL url) {
        return url.toString().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}
