package com.eckyputrady.shorturl.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by ecky on 07/06/16.
 */
@RestController
@Slf4j
public class UrlController {

    @Autowired
    private UrlService service;

    @RequestMapping(path = "/url", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShortenedUrl shortenUrl(@Valid @RequestBody ShortenUrlForm form) {
        return service.shortenUrl(form);
    }

    @RequestMapping("/url")
    public ShortenedUrl expand(@Valid ShortUrlQueryParam param) throws UrlService.NotFoundException {
        return service.expand(param);
    }

    @RequestMapping("/{id}")
    public void resolve(@PathVariable("id") String id, HttpServletResponse response) throws UrlService.NotFoundException, IOException {
        String longUrl = service.resolveLongUrl(id);
        response.sendRedirect(longUrl);
    }
}
