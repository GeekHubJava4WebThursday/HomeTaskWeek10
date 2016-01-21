package org.geekhub;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ImageCrawler downloads all images to specified folder from specified resource.
 * It uses multi threading to make process faster. To start download images you should call downloadImages(String urlToPage) method with URL.
 * To shutdown the service you should call stop() method
 */
public class ImageCrawler {

    //number of threads to download images simultaneously
    public static final int NUMBER_OF_THREADS = 15;
    private Pattern pattern = Pattern.compile(".+\\.(jpg|jpeg|png|tif|gif|bmp)");

    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private String folder;

    public ImageCrawler(String folder) throws MalformedURLException {
        this.folder = folder;
    }

    /**
     * Call this method to start download images from specified URL.
     * @param urlToPage
     * @throws IOException
     */
    public void downloadImages(String urlToPage) throws IOException {
        Page page = new Page(new URL(urlToPage));
        Collection<URL> images = page.getImageLinks();
        images.stream()
                .filter(this::isImageURL)
                .forEach(img -> executorService.submit(new ImageTask(img, folder)));
    }

    public void await() throws InterruptedException {
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        Matcher m = pattern.matcher(url.toString());
        return m.matches();
    }
}
