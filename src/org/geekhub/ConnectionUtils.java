package org.geekhub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        byte[] bytes;
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()), BUFFER_SIZE)){
            String line;
            int size = 0;
            while ((line = reader.readLine()) != null && size < MAX_CONTENT_SIZE) {
                content.append(line);
                size += line.length();
            }
            bytes = content.toString().getBytes();
        }
        return bytes;
    }
}
