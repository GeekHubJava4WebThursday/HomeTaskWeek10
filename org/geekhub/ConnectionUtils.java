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

    /**
     * Downloads content for specified URL and returns it as a byte array.
     * Should be used for small files only. Don't use it to download big files it's dangerous.
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] getData(URL url) throws IOException {
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        while (-1!=(len=in.read(bytes))){
            out.write(bytes,0,len);
        }
        in.close();
        out.close();

        return out.toByteArray();
    }
}
