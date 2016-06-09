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
        long id = UrlShortener.decode(param.getShortUrl());
        ShortUrlRecord record = jooq.fetchOne(SHORT_URL, SHORT_URL.ID.eq(id));
        if (record == null) throw new NotFoundException(param.getShortUrl());

        ShortenedUrl.AnalyticsData allTime = new ShortenedUrl.AnalyticsData();
        if (param.getProjection() == Projection.ANALYTICS_CLICKS ||
            param.getProjection() == Projection.FULL)
            allTime.setShortUrlClicks(record.getClickCount());

        ShortenedUrl.Analytics analytics = null;
        if (param.getProjection() != null)
            analytics = new ShortenedUrl.Analytics(allTime);

        return new ShortenedUrl(
                UrlShortener.encode(record.getId()),
                record.getLongUrl(),
                analytics
        );
    }

    public String resolveLongUrl(String shortUrl) throws NotFoundException {
        long id = UrlShortener.decode(shortUrl);
        ShortUrlRecord record = jooq.fetchOne(SHORT_URL, SHORT_URL.ID.eq(id));
        if (record == null) throw new NotFoundException(shortUrl);

        updateClickCount(record);

        return record.getLongUrl();
    }

    private void updateClickCount(ShortUrlRecord record) {
        jooq.update(SHORT_URL)
                .set(SHORT_URL.CLICK_COUNT, SHORT_URL.CLICK_COUNT.add(1))
                .where(SHORT_URL.ID.equal(record.getId()))
                .execute();
    }

    public static class NotFoundException extends Exception {
        public NotFoundException(String id) {
            super("Short URL with id \"" + id + "\" is not found.");
        }
    }
}
