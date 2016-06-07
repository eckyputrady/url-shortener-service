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
public class ShortUrlQueryParam {
    private String shortUrl;
    private Projection projection;
}
