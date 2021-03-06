package router;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.AdminController;
import controllers.CodecoolerController;
import controllers.LoginController;
import controllers.MentorController;
import models.Account;
import services.FormService;
import sessionData.SessionHandler;
import sessionData.CookieHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.*;

public class Router implements HttpHandler {
    private SessionHandler sessionHandler;
    private CookieHandler cookieHandler;
    private LoginController loginController;
    private CodecoolerController codecoolerController;
    private MentorController mentorController;
    private AdminController adminController;


    public Router() {
        this.sessionHandler = SessionHandler.getInstance();
        this.cookieHandler = new CookieHandler();
        this.loginController = new LoginController();
        this.codecoolerController = new CodecoolerController();
        this.mentorController = new MentorController();
        this.adminController = new AdminController();

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Request...");
        Optional<HttpCookie> cookie = cookieHandler.getSessionIdCookie(httpExchange);
        String method = httpExchange.getRequestMethod();
        String response = "";

        if (!cookie.isPresent() && method.equals("GET")) {
            response = loginController.getLoginPage();
        } else if (!cookie.isPresent() && method.equals("POST")) {
            // check inputs
            Map<String, String> loginDataMap = getFormInputsMap(httpExchange);
            // validate inputs && send response
            Account account = loginController.logIn(loginDataMap);

            if (account == null || loginDataMap.isEmpty()) {
                response = loginController.getLoginPage();
            } else {
                sessionHandler.addSession(account, httpExchange);
                response = connectToControllerBy(account, httpExchange);
            }
        } else if (cookie.isPresent()) {
            int cookiePermissionLevel = sessionHandler.getPermissionFromCookie(cookie);

            System.out.println(cookieHandler.getSessionIdCookieValue(cookie));
            String[] requestPathArray = httpExchange.getRequestURI().toString().split("/");
            String userRequestedPermissions = requestPathArray[2];
            String userPageRequest = requestPathArray[3];

            response = getResponseByCookieAndUrl(httpExchange,
                    cookiePermissionLevel,
                    userRequestedPermissions,
                    userPageRequest);
        } else {
            System.out.println("Not found");
            response = "ERROR 404";
        }
        sendResponse(httpExchange, response, cookie);
        System.out.println("response.....");
    }

    private String getResponseByCookieAndUrl(HttpExchange httpExchange,
                                             int cookiePermissionLevel,
                                             String userRequestedPermissions,
                                             String userPageRequest) throws IOException {
        String response;
        if (userRequestedPermissions.equals("admin") && cookiePermissionLevel == 3) {
            response = adminController.getAdminResponse(httpExchange, userPageRequest);
        } else if (userRequestedPermissions.equals("mentor") && cookiePermissionLevel == 2) {
            response = mentorController.getMentorResponse(httpExchange, userPageRequest);
        } else if (userRequestedPermissions.equals("codecooler") && cookiePermissionLevel == 1) {
            response = codecoolerController.getCodecoolerResponse(httpExchange, userPageRequest);
        } else {
            response = "Error 404";
        }
        return response;
    }

    private String connectToControllerBy(Account account, HttpExchange httpExchange) {
        int permission = account.getPermission();
        int accountId = account.getAccountId();

        switch (permission) {
            case 3: // Admin
                return adminController.getIndexPage();
            case 2: // Mentor
                return mentorController.getIndexPage(accountId);
            case 1: // Codecooler
                return codecoolerController.getIndexPage(accountId);
        }
        return null;
    }

    private void sendResponse(HttpExchange httpExchange, String response, Optional<HttpCookie> cookie) throws IOException {
        cookieHandler.setResponseCookieIfPresent(httpExchange, cookie);
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Cache-Control", "no-cache");
        headers.set("Cache-Control", "no-store");
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> getFormInputsMap(HttpExchange httpExchange) throws IOException {
        return FormService.getInputsStringMap(httpExchange);
    }
}
