package org.geekhub;

import java.io.*;
import java.net.URL;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();

        try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream())) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                data.write(buffer, 0, bytesRead);
            }
        }

        return data.toByteArray();
    }
}
