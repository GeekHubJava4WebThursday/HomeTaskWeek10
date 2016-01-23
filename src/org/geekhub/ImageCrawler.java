package org.geekhub;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ImageCrawler downloads all images to specified folder from specified resource.
 * It uses multi threading to make process faster. To start download images you should call downloadImages(String urlToPage) method with URL.
 * To shutdown the service you should call stop() method
 */
public class ImageCrawler {

    //number of threads to download images simultaneously
    public static final int NUMBER_OF_THREADS = 10;

    private ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private String folder;

    public ImageCrawler(String folder) throws MalformedURLException {
        this.folder = folder;
    }

    /**
     * Call this method to start download images from specified URL.
     * @param urlToPage url to web page
     * @throws IOException
     */
    public void downloadImages(String urlToPage) throws IOException {
        Page page = new Page(new URL(urlToPage));
        page.getImageLinks().stream()
                .filter(this::isImageURL)
                .forEach(url -> executorService.submit(new ImageTask(url, folder)));
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    private boolean isImageURL(URL url) {
        String u = url.toString();
        return u.endsWith(".png") || u.endsWith(".jpg") || u.endsWith(".jpeg") ||
                u.endsWith(".gif") || u.endsWith(".ico") || u.endsWith(".svg") ||
                u.endsWith(".bmp");
    }

}
