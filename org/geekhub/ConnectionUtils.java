package org.geekhub;

import java.io.*;
import java.net.URL;

/**
 * Utils class that contains useful method to interact with URLConnection
 */
public class ConnectionUtils {

    private static final int BUFFER_SIZE = 2048;

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        InputStream stream = url.openConnection().getInputStream();
        int nextData;
        while ((nextData = stream.read(buffer, 0, BUFFER_SIZE)) != -1) {
            byteArray.write(buffer, 0, nextData);
        }
        return byteArray.toByteArray();
    }
}
