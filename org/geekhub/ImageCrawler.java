package org.geekhub;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
     *
     * @param urlToPage
     * @throws IOException
     */
    public void downloadImages(String urlToPage) throws IOException {
        Page page = new Page(new URL(urlToPage));

        for (URL url : page.getImageLinks())
            if (isImageURL(url)) {
                ImageTask imageTask = new ImageTask(url, folder);
                executorService.execute(imageTask);
            }
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        String[] fileExtensions = {"jpg", "png"};
        String fileName = url.toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        for (String extension : fileExtensions) {
            if (extension.equalsIgnoreCase(fileExtension))
                return true;
        }
        return false;
    }


}
