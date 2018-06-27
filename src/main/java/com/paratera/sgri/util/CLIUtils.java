package com.paratera.sgri.util;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CLIUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIUtils.class);

    public static String exec(String shell) {
        StringBuilder sb = new StringBuilder();
        try {
            String[] command = {"/bin/sh", "-c", shell};
            Process ps = Runtime.getRuntime().exec(command);
            InputStream is = ps.getInputStream();
            int nextChar;
            while (( nextChar = is.read() ) != -1) {
                sb.append((char) nextChar);
            }
        } catch (Exception e) {
            LOGGER.error("执行脚本失败:" + shell, e);
        }
        return sb.toString();
    }
}
