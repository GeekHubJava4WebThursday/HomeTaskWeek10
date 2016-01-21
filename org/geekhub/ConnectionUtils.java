package org.geekhub;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
        List<byte[]> dataList = new ArrayList<>();
        byte[] buffer = new byte[1024];
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        connection.connect();
        while ((stream.read(buffer)) != -1) {
            dataList.add(buffer);
        }
        byte[] dataFinalArr = new byte[buffer.length * dataList.size()];
        int length = 0;
        for (byte[] buf : dataList) {
            for (byte b : buf) {
                dataFinalArr[length++] = b;
            }
        }
        return dataFinalArr;
    }
}
