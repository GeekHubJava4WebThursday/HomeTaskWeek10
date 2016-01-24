package org.geekhub;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    public static final int BUFFER_SIZE = 16 * 1024;

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(BUFFER_SIZE);

        try (InputStream is = new BufferedInputStream(url.openConnection().getInputStream(), BUFFER_SIZE)) {
            int currentByte;
            while ((currentByte = is.read()) != -1) {
                outputStream.write(currentByte);
            }
        }

        return outputStream.toByteArray();

    }

}
