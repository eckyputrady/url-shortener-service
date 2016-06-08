package com.eckyputrady.shorturl.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ecky on 07/06/16.
 */
@RestController
@Slf4j
public class UrlController {

    @RequestMapping(path = "/url", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShortenedUrl shortenUrl(@RequestBody ShortenUrlForm form) {
        log.info("Shortening form: {}", form);
        return new ShortenedUrl();
    }

    @RequestMapping("/{id}")
    public void resolve(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        log.info("Resolving id {}", id);
        response.sendRedirect("/" + id);
    }

    @RequestMapping("/url")
    public ShortenedUrl expand(ShortUrlQueryParam param) {
        log.info("Expanding {}", param);
        return new ShortenedUrl();
    }
}
