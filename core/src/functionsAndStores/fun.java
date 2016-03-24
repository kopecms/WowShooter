package functionsAndStores;

import java.nio.ByteBuffer;

/**
 * Created by kopec on 2016-03-22.
 */
public class fun {
    public static byte[] concat(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c= new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static int bytesToInt(byte[] bytes,int s, int e) {
        byte [] out = new byte[ e ];
        System.arraycopy(bytes,s,out,0,e);
        return ByteBuffer.wrap(out).getInt();
    }
}