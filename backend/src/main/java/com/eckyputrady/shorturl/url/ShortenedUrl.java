package com.eckyputrady.shorturl.url;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
public class ShortenedUrl {
    private String id;
    private String longUrl;
    private Analytics analytics;

    @Getter
    @Setter
    @ToString
    public static class Analytics {
        private AnalyticsData allTime;
    }

    @Getter
    @Setter
    @ToString
    public static class AnalyticsData {
        private long shortUrlClicks;
    }
}
