package org.geekhub;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            if (!Files.exists(Paths.get(folder))) {
                new File(folder).mkdir();
            }
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(ConnectionUtils.getData(url)));
            String fileName = folder + buildFileName(url);
            ImageIO.write(image, "jpg", new File(fileName));
        } catch (IOException e) {
            System.out.println("Error while download image: " + e.getMessage());
        }
    }

    //converts URL to unique file name
    private String buildFileName(URL url) {
        return url.toString().replaceAll("[^a-zA-Z0-9-_\\.]", "_");
    }
}
