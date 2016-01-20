package org.geekhub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ImageCrawler downloads all images to specified folder from specified resource.
 * It uses multi threading to make process faster. To start download images you should call downloadImages(String urlToPage) method with URL.
 * To shutdown the service you should call stop() method
 */
public class ImageCrawler {

    private List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "bmp", "gif", "png", "tiff", "tif");

    //number of threads to download images simultaneously
    public static final int NUMBER_OF_THREADS = 15;

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
        Collection<URL> urls = new Page(new URL(urlToPage)).getImageLinks();
        urls
                .stream()
                .filter(this::isImageURL)
                .forEach(link -> executorService.execute(new ImageTask(link, folder)));
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    /**
     *  Method waits while all tasks have finished
     */
    public void awaitTermination() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        String path = url.getFile();
        int index = path.lastIndexOf(".");
        String ext;
        if (index > 0) {
            ext = path.substring(index + 1);
        } else {
            return false;
        }
        return imageExtensions
                .stream()
                .anyMatch(item -> item.equalsIgnoreCase(ext));
    }
}
