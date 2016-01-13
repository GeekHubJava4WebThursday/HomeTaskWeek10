package org.geekhub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
     * @param urlToPage
     * @throws IOException
     */
    public void downloadImages(String urlToPage) throws IOException {
        Page page = new Page(new URL(urlToPage));
        Collection<URL> imageLinks = page.getImageLinks();

        imageLinks.stream()
                .filter(imageLink -> isImageURL(imageLink))
                .forEach(imageLink -> executorService.execute(new ImageTask(imageLink,folder)));
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        List<String> imageExtensions = Arrays.asList("jpg","jpeg","gif","png");
        String imageExtension = getExtension(url).toLowerCase();

        return imageExtensions.contains(imageExtension);
    }

    private String getExtension(URL url) {
        String extension = "";
        String urlString = url.toString();
        int posDot = urlString.lastIndexOf(".");
        if (posDot > 0) extension = urlString.substring(posDot + 1);
        return extension;
    }

}
