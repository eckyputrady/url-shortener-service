package com.eckyputrady.shorturl.url;

import lombok.*;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedUrl {
    private String id;
    private String longUrl;
    private Analytics analytics;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Analytics {
        private AnalyticsData allTime;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnalyticsData {
        private Long shortUrlClicks;
    }
}
