package com.eckyputrady.shorturl.url;

import com.eckyputrady.shorturl.generated.jooq.tables.records.ShortUrlRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eckyputrady.shorturl.generated.jooq.tables.ShortUrl.SHORT_URL;

/**
 * Created by eckyputrady on 6/9/16.
 */
@Service
public class UrlService {
    @Autowired
    DSLContext jooq;

    public ShortenedUrl shortenUrl(ShortenUrlForm form) {
        ShortUrlRecord record = jooq.newRecord(SHORT_URL);
        record.setLongUrl(form.getLongUrl());
        record.store();

        return new ShortenedUrl(UrlShortener.encode(record.getId()), record.getLongUrl(), null);
    }

    public ShortenedUrl expand(ShortUrlQueryParam param) throws NotFoundException {
        ShortUrlRecord record = jooq.fetchOne(SHORT_URL, SHORT_URL.ID.eq(UrlShortener.decode(param.getShortUrl())));
        if (record == null) throw new NotFoundException(param.getShortUrl());

        return new ShortenedUrl(
                UrlShortener.encode(record.getId()),
                record.getLongUrl(),
                new ShortenedUrl.Analytics(
                        new ShortenedUrl.AnalyticsData(0)
                )
        );
    }

    public String resolveLongUrl(String id) throws NotFoundException {
        ShortUrlRecord record = jooq.fetchOne(SHORT_URL, SHORT_URL.ID.eq(UrlShortener.decode(id)));
        if (record == null) throw new NotFoundException(id);
        return record.getLongUrl();
    }

    public static class NotFoundException extends Exception {
        public NotFoundException(String id) {
            super("Short URL with id \"" + id + "\" is not found.");
        }
    }
}
