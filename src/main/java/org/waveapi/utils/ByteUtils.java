package org.waveapi.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteUtils {


    public static byte[] encodeInteger(int integer) {
        return new byte[] {
                (byte)(integer >>> 24),
                (byte)(integer >>> 16),
                (byte)(integer >>> 8),
                (byte)integer
        };
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static int readInteger(FileInputStream input) throws IOException {
        byte[] b = new byte[4];
        input.read(b);
        return ByteBuffer.wrap(b).getInt();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static float readFloat(FileInputStream input) throws IOException {
        byte[] b = new byte[4];
        input.read(b);
        return ByteBuffer.wrap(b).getFloat();
    }

    public static byte[] encodeFloat(float f) {
        return ByteBuffer.allocate(4).putFloat(f).array();
    }

    public static byte[] encodeLong(long l) {
        return ByteBuffer.allocate(8).putLong(l).array();
    }

    public static byte[] encodeString (String str) {

        byte[] sb = str.getBytes();
        byte[] b = new byte[4+sb.length];
        System.arraycopy(encodeInteger(sb.length), 0, b, 0, 4);
        System.arraycopy(sb, 0, b, 4, sb.length);

        return b;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String readString(FileInputStream input) throws IOException {
        int size = readInteger(input);
        byte[] st = new byte[size];
        input.read(st);

        return new String(st);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static byte readByte(FileInputStream input) throws IOException {
        byte[] st = new byte[1];
        input.read(st);
        return st[0];
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static long readLong(FileInputStream input) throws IOException {
        byte[] b = new byte[8];
        input.read(b);
        return ByteBuffer.wrap(b).getLong();
    }

}
