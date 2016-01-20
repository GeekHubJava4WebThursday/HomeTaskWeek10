package org.geekhub;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    public static final int BUFFER_SIZE = 8 * 1024;
    public static final int MAX_CONTENT_SIZE = 1024 * 1024;

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url to download content from
     * @return a byte array
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        byte[] data;
        try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream(), BUFFER_SIZE);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BUFFER_SIZE)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int num;
            int size = 0;
            while ((num = inputStream.read(buffer)) != -1 && size < MAX_CONTENT_SIZE) {
                outputStream.write(buffer, 0, num);
                size += num;
            }
            data = outputStream.toByteArray();
        }
        return data;
    }
}
