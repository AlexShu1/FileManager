package com.kylinno.legal.document.domain.untils;

public class FileUtil {

    /**
     * Let Hex string to chinese.
     */
    public static String hexStringToChinese(String hexString) {
        StringBuilder stringBuilder = new StringBuilder();
        hexString = hexString.replace("&#", "");
        String[] codes = hexString.split(";");
        for (int i = 0; i < codes.length; i++) {
            stringBuilder.append((char) Integer.parseInt(codes[i]));
        }

        return stringBuilder.toString();
    }
}
