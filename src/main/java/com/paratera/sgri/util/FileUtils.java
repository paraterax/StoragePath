package com.paratera.sgri.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String getConent(String path) throws IOException {
        InputStream in = FileUtils.class.getResourceAsStream(path);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        while (( tmp = bfr.readLine() ) != null) {
            if (!tmp.trim().startsWith("#")) {
                sb.append(tmp).append("\n");
            }
        }
        bfr.close();
        return sb.toString();
    }

}
