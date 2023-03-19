package org.waveapi.utils;

import java.io.File;

public class FileUtil {
    public static void recursivelyDelete(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                recursivelyDelete(f);
            }
        }
        dir.delete();
    }
}
