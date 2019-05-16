package sessionData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CookieHandlerTest {

    private CookieHandler cookieHandler;

    @BeforeEach
    void setup() {
        cookieHandler = new CookieHandler();
    }

    @Test
    void parseCookies_CookieStringCorrect_CookiesReturned() {
        String cookieStr = "sessionId=djfijfijijajfsidjfisdfj;another=another";

        List<HttpCookie> cookies = cookieHandler.parseCookies(cookieStr);

        assertEquals("sessionId", cookies.get(0).getName());
        assertEquals("djfijfijijajfsidjfisdfj", cookies.get(0).getValue());
        assertEquals("another", cookies.get(1).getName());
        assertEquals("another", cookies.get(1).getValue());
    }

    @Test
    void parseCookies_CookieStringEmpty_EmptyListReturned() {
        String cookieStr = "";

        List<HttpCookie> cookies = cookieHandler.parseCookies(cookieStr);

        assertTrue(cookies.isEmpty());
    }

    @Test
    void findCookieByName_WhenNameInList_OptionalWithCookieValueReturned() {
        HttpCookie cookie = new HttpCookie("sessionId", "djfijfijijajfsidjfisdfj");
        List<HttpCookie> cookies = Arrays.asList(cookie);

        Optional<HttpCookie> parsedCookie = cookieHandler.findCookieByName("sessionId", cookies);

        assertTrue(parsedCookie.isPresent());
        assertEquals("sessionId", parsedCookie.get().getName());
        assertEquals("djfijfijijajfsidjfisdfj", parsedCookie.get().getValue());
    }

    @Test
    void findCookieByName_WhenNameNotInList_EmptyOptionalReturned() {
        HttpCookie cookie = new HttpCookie("sessionId", "djfijfijijajfsidjfisdfj");
        List<HttpCookie> cookies = Arrays.asList(cookie);

        Optional<HttpCookie> parsedCookie = cookieHandler.findCookieByName("session", cookies);

        assertFalse(parsedCookie.isPresent());
    }
}