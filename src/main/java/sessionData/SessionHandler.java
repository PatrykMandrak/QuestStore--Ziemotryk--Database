package sessionData;

import models.Account;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SessionHandler {
    private CookieHandler cookieHandler = new CookieHandler();
    private static SessionHandler instance = null;

    private Map<String, String[]> activeSessionList;

    private SessionHandler() {
        activeSessionList = new HashMap<>();
    }

    public static SessionHandler getInstance() {
        if (instance == null) {
            instance = new SessionHandler();
        }
        return instance;
    }

    public Map<String, String[]> getActiveSessionList() {
        return activeSessionList;
    }

    public void addSession(Account account, HttpExchange httpExchange) {
        String randomSessionId = getRandomSessionId();
        String accountId = String.valueOf(account.getAccountId());
        String permission = String.valueOf(account.getPermission());

        String[] accountData = {accountId, permission};
        System.out.println("randomSessionId |||" + randomSessionId + "|||"); // remove later
        activeSessionList.put(randomSessionId, accountData);
        cookieHandler.addSessionIdCookie(randomSessionId, httpExchange);
        System.out.println("ALIVE");
    }

    public int getPermissionFromCookie(Optional<HttpCookie> cookie) {
        return Integer.parseInt(getIndexValueFromActiveSessionId(cookie, 1));
    }

    public int getAccountIdFromCookie(Optional<HttpCookie> cookie) {
        return Integer.parseInt(getIndexValueFromActiveSessionId(cookie, 0));
    }

    public void removeActiveSessionWithCookie(HttpExchange httpExchange) {
        Optional<HttpCookie> cookie = cookieHandler.getSessionIdCookie(httpExchange);
        activeSessionList.remove(
                cookie.get().getValue().replaceAll("\"", "")
        );
        cookieHandler.removeCookie(cookie);
    }

    private String getIndexValueFromActiveSessionId(Optional<HttpCookie> cookie, int index) {
        String cookieSessionId = cookieHandler.getSessionIdCookieValue(cookie);
        return activeSessionList.get(cookieSessionId)[index];
    }

    String getRandomSessionId() {
        String randomId = "";
        Random random = new Random();

        int currentRandomNumber;
        do {
            for (int i = 0; i < 19; i++) {
                currentRandomNumber =
                        random.ints(35, 123)
                                .findFirst().getAsInt();
                randomId += (char) currentRandomNumber;
            }
        } while (activeSessionList.containsKey(randomId));

        return randomId;
    }
}
