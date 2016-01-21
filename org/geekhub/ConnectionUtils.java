package org.geekhub;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
        List<byte[]> dataList = new ArrayList<>();
        byte[] buffer = new byte[BUFFER_SIZE];

        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();

        while ((stream.read(buffer)) != -1) {
            dataList.add(buffer);
            buffer = new byte[BUFFER_SIZE];
        }
        byte[] dataFinalArr = new byte[BUFFER_SIZE * dataList.size()];
        int length = 0;
        for (byte[] buf : dataList) {
            for (byte b : buf) {
                dataFinalArr[length] = b;
                length++;
            }
        }
        return dataFinalArr;
    }
}
