package org.geekhub;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url url of web page to download
     * @return web page source as byte array
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream())) {
            int b;
            while ((b = in.read()) != -1) {
                outputStream.write(b);
            }
        }
        return outputStream.toByteArray();
    }

}
