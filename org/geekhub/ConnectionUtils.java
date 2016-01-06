package org.geekhub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8 * 1024];
        try (InputStream is = url.openConnection().getInputStream()) {
            int count;
            while ((count = is.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
        }
        return baos.toByteArray();
    }
}
