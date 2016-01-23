package org.geekhub;

import java.io.*;
import java.net.URL;

/**
 * Represents worker that downloads image from URL to specified folder.<br/>
 * Name of the image will be constructed based on URL. Names for the same URL will be the same.
 */
public class ImageTask implements Runnable {
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
        try (
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(folder + buildFileName(url)))
        ) {
            int b;
            while ((b = inputStream.read()) != -1) {
                outputStream.write(b);
            }
        } catch (IOException e) {
            System.out.println("Error while loading data from " + url);
        }
    }

    /**
     * Converts URL to unique file name
     */
    private String buildFileName(URL url) {
        return url.toString().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }

}
