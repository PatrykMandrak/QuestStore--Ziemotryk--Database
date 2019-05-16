package services;

import databaseAccess.AccountsDAO;
import models.Account;
import views.ResponseCreator;

import java.util.List;
import java.util.Map;

public class LoginService {
    private ResponseCreator responseCreator = new ResponseCreator();
    private AccountsDAO accountsDAO;

    public LoginService(AccountsDAO accountsDAO) {
        this.accountsDAO = accountsDAO;
    }

    public String getLoginPageRender() {
        return responseCreator.renderLoginPage();
    }

    public Account validateLogIn(Map<String, String> inputs) {
        List<Account> accountsList = accountsDAO.getAll();

        String login = inputs.get("login");
        String password = inputs.get("password");

        for (Account account : accountsList) {
            if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
                return account;
            }
        }

        return null;
    }
}
