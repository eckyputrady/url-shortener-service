package com.eckyputrady.shorturl.url;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlForm {
    @NotEmpty
    @URL
    private String longUrl;
}
