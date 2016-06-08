package com.eckyputrady.shorturl.url;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlQueryParam {
    @NotEmpty
    private String shortUrl;

    private Projection projection;
}
