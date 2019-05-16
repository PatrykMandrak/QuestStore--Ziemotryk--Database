package controllers;

import databaseAccess.AccountsDAO;
import models.Account;
import services.LoginService;

import java.util.Map;

public class LoginController {
    private LoginService loginService = new LoginService(new AccountsDAO());

    public String getLoginPage() {
        return loginService.getLoginPageRender();
    }

    public Account logIn(Map<String, String> inputs) {
        return loginService.validateLogIn(inputs);
    }
}
