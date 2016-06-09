package com.eckyputrady.shorturl.url;

import com.eckyputrady.shorturl.Application;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RedirectConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by ecky on 08/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
public class UrlControllerTest {

    // Will contain the random free port number
    @Value("${local.server.port}")
    private int port;

    private static final String LONG_URL = "https://cobacoba.com";

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.authentication = RestAssured.basic("short", "url");
    }

    //// Shorten endpoint

    @Test
    @FlywayTest
    public void testShortenUrl() throws Exception {
        ShortenUrlForm form = new ShortenUrlForm(LONG_URL);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(form)
                .post("/url")
                .then()
                    .statusCode(200)
                    .body("longUrl", equalTo(LONG_URL))
                    .body("id", equalTo("b"));
    }

    @Test
    public void testShortenUrl_NotUrl_Returns400() throws Exception {
        ShortenUrlForm form = new ShortenUrlForm("invalid_url goes here");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(form)
                .post("/url")
                .then()
                    .statusCode(400);
    }

    //// Resolve endpoint

    @Test
    @FlywayTest
    public void testResolve() throws Exception {
        String shortUrl = shortenUrl(LONG_URL);

        RestAssured
                .given()
                    .config(RestAssuredConfig.config().redirect(RedirectConfig.redirectConfig().followRedirects(false)))
                .get("/" + shortUrl)
                .then()
                    .statusCode(302)
                    .header("Location", LONG_URL);
    }

    @Test
    public void testResolve_NotFoundUrl_Returns404() throws Exception {
        RestAssured
                .given()
                    .config(RestAssuredConfig.config().redirect(RedirectConfig.redirectConfig().followRedirects(false)))
                .get("/invalid")
                .then()
                    .statusCode(404);
    }

    //// expands endpoint

    @Test
    @FlywayTest
    public void testExpand() throws Exception {
        String shortUrl = shortenUrl(LONG_URL);

        RestAssured
                .given()
                    .param("shortUrl", shortUrl)
                    .param("projection", "FULL")
                .get("/url")
                .then()
                    .statusCode(200)
                    .body("longUrl", equalTo(LONG_URL))
                    .body("analytics.allTime.shortUrlClicks", equalTo(0));
    }

    @Test
    public void testExpand_NotFoundUrl_Returns404() throws Exception {
        RestAssured
                .given()
                    .param("shortUrl", "unregistered_short_url")
                .get("/url")
                .then()
                    .statusCode(404);
    }

    @Test
    public void testExpand_InvalidParameter_Returns400() throws Exception {
        RestAssured
                .given()
                    .param("projection", "invalid_projection")
                .get("/url")
                .then()
                    .statusCode(400);
    }

    //// Helper

    private String shortenUrl(String longUrl) {
        ShortenUrlForm form = new ShortenUrlForm(longUrl);

        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(form)
                .post("/url")
                .then()
                    .extract()
                        .path("id");
    }
}