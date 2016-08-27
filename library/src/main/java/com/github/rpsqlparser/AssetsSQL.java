package com.github.rpsqlparser;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Roger Patiño on 26/08/2016.
 */

public class AssetsSQL {

    public static boolean existsFile(String fileName,  String path, AssetManager assetManager) throws IOException {
        if (!fileName.isEmpty()) {
            if (!path.isEmpty()) {
                if (assetManager == null) {
                    throw new NullPointerException("assetManager == null");
                }
                for (String currentFileName : assetManager.list(path)) {
                    if (currentFileName.equals(fileName)) {
                        return true;
                    }
                }
                return false;
            } else {
                throw new IllegalArgumentException("Path of file is emtry");
            }
        } else {
            throw new IllegalArgumentException("Name of file is emtry");
        }
    }

    public static String[] list(String path, AssetManager assetManager) throws IOException {
        if (!path.isEmpty()) {
            if (assetManager == null) {
                throw new NullPointerException("assetManager == null");
            }
            String[] files = assetManager.list(path);
            Arrays.sort(files);
            return files;
        } else {
            throw new IllegalArgumentException("Path of file is emtry");
        }
    }

    public static String readFile(String fileName, AssetManager assetManager) throws IOException {
        if (!fileName.isEmpty()) {
            if (assetManager == null) {
                throw new NullPointerException("assetManager == null");
            }

            InputStream input;
            String text;
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);
            return text;
        } else {
            throw new IllegalArgumentException("Name of file is emtry");
        }
    }
}