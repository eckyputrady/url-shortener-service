package com.eckyputrady.shorturl.url;

import lombok.*;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@Builder
public class ShortenUrlForm {
    private String longUrl;
}
