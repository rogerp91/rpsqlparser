package com.github.rpsqlparser;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Roger Patiño on 26/08/2016.
 */

public class Assets {

    public static boolean existsFile(@NonNull String fileName, @NonNull String path, @NonNull AssetManager assetManager) throws IOException {
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Name of file is emtry");
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Path of file is emtry");
        }
        if (assetManager == null) {
            throw new NullPointerException("assetManager == null");
        }
        for (String currentFileName : assetManager.list(path)) {
            if (currentFileName.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public static String[] list(String path, AssetManager assetManager) throws IOException {
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Path of file is emtry");
        }
        if (assetManager == null) {
            throw new NullPointerException("assetManager == null");
        }
        String[] files = assetManager.list(path);
        Arrays.sort(files);
        return files;
    }

    public static String readFile(String fileName, AssetManager assetManager) throws IOException {
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Name of file is emtry");
        }
        if (assetManager == null) {
            throw new NullPointerException("assetManager == null");
        }

        InputStream input;
        String text = null;
        input = assetManager.open(fileName);

        int size = input.available();
        byte[] buffer = new byte[size];
        input.read(buffer);
        input.close();

        // byte buffer into a string
        text = new String(buffer);
        return text;
    }
}