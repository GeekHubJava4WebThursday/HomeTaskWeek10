package org.geekhub;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
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
    public static final int NUMBER_OF_PAGES = 100;

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
        List<URL> urlList = new ArrayList<>();
        Set<URL> imageUrlSet = new HashSet<>();
        urlList.add(new URL(urlToPage));
        for (int i = 0; i < urlList.size(); i++) {
            try {
                Page page = new Page(urlList.get(i));
                if (urlList.size() < NUMBER_OF_PAGES) {
                    page.getLinks().stream()
                            .filter(url1 -> !urlList.contains(url1))
                            .forEach(urlList::add);
                }
                page.getImageLinks().stream()
                        .filter(this::isImageURL)
                        .forEach(imageUrlSet::add);
            } catch (IOException e) {
                System.out.println("Error while reading page: " + e.getMessage());
            }
        }
        imageUrlSet.stream()
                .filter(this::isImageURL)
                .forEach(imageURL -> executorService.execute(new ImageTask(imageURL, folder)));
    }

    /**
     * Call this method before shutdown an application
     */
    public void stop() {
        executorService.shutdown();
    }

    //detects is current url is an image. Checking for popular extensions should be enough
    private boolean isImageURL(URL url) {
        return FilenameUtils.isExtension(url.toString(), Arrays.asList("jpeg", "jpg", "png", "gif", "bmp"));
    }
}
