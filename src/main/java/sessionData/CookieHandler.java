package sessionData;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CookieHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";

    public void setResponseCookieIfPresent(HttpExchange httpExchange, Optional<HttpCookie> cookie) {
        if (cookie.isPresent()) {
            httpExchange.getResponseHeaders().add("Set-cookie", cookie.toString());
        }
    }

    public Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = parseCookies(cookieStr);
        return findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    public String getSessionIdCookieValue(Optional<HttpCookie> cookie) {
        return cookie.get().getValue().replaceAll("\"", "");
    }

    List<HttpCookie> parseCookies(String cookieString) {
        List<HttpCookie> cookies = new ArrayList<>();
        if (cookieString == null || cookieString.isEmpty()) { // what happens if cookieString = null?
            return cookies;
        }
        for (String cookie : cookieString.split(";")) {
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1, cookie.length());
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    public Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies) {
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals(name))
                return Optional.ofNullable(cookie);
        }
        return Optional.empty();
    }

    public void addSessionIdCookie(String sessionId, HttpExchange httpExchange) {
        System.out.println("im lost here");
        HttpCookie cookie = new HttpCookie(SESSION_COOKIE_NAME, sessionId);
        cookie.setMaxAge(90 * 80); // 2 hours
        System.out.println("cookieSessionId|||" + cookie.getValue() + "|||");
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
    }

    public void removeCookie(Optional<HttpCookie> cookie) {
        cookie.get().setMaxAge(-1);
    }
}
