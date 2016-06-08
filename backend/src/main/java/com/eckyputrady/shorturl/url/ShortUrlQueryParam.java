package com.eckyputrady.shorturl.url;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlQueryParam {
    @NotNull
    private String shortUrl;
    
    private Projection projection;
}
