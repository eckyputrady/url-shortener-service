package com.eckyputrady.shorturl.url;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * Created by ecky on 07/06/16.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlForm {
    @NotNull
    @URL
    private String longUrl;
}
