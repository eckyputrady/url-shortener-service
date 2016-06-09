package com.eckyputrady.shorturl.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by eckyputrady on 6/9/16.
 */

@RunWith(value = Parameterized.class)
public class UrlShortenerTest {

    private String encoded;
    private long decoded;

    public UrlShortenerTest(String encoded, long decoded) {
        this.encoded = encoded;
        this.decoded = decoded;
    }

    @Parameterized.Parameters(name= "{index}: encoded({0})=decoded({1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "a", 0L },
                { "b", 1L },
                { "9", 61L },
                { "ba", 62L },
                { "xTC", 91230L },
                { "i-I6I", 129308102L },
            }
        );
    }

    @Test
    public void testEncode() throws Exception {
        assertEquals(encoded, UrlShortener.encode(decoded));
    }

    @Test
    public void testDecode() throws Exception {
        assertEquals(decoded, UrlShortener.decode(encoded));
    }
}