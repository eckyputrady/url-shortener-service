package com.eckyputrady.shorturl.url;

/**
 * Created by eckyputrady on 6/9/16.
 */
public class UrlShortener {
    private static final String DIGITS = "abcdefghijklmnopqrst_vwxyzABCDEFGHIJKLMNOPQRST-VWXYZ0123456789";
    private static final int BASE = DIGITS.length();

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int idx = (int)(num % BASE);
            sb.append(DIGITS.charAt(idx));
            num /= BASE;
            if (num <= 0) break;
        }
        return sb.reverse().toString();
    }

    public static long decode(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++)
            num = num * BASE + DIGITS.indexOf(str.charAt(i));
        return num;
    }
}