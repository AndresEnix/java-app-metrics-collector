package com.andres.metrics.collector.util;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Scanner;

public class FileUtils {

    public static String getContentAsSingleLine(String fileName) {
        InputStream in = FileUtils.class.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(in).useDelimiter(StringUtils.LF);
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNext()){
            stringBuilder.append(scanner.nextLine()).append(StringUtils.SPACE);
        }
        return stringBuilder.toString();
    }
}
