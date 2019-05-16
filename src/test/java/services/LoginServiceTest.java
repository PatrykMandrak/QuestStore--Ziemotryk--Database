package services;

import databaseAccess.AccountsDAO;
import models.Account;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    @Test
    void validateLogIn_WithCorrectCredentials_PresentUserParsed() {
        AccountsDAO accountsDAO = mock(AccountsDAO.class);
        Account account = new Account("user", "user", 1);
        when(accountsDAO.getAll()).thenReturn(Arrays.asList(account));
        LoginService loginService = new LoginService(accountsDAO);
        Map<String, String> loginData = new HashMap<>();
        loginData.put("login", "user");
        loginData.put("password", "user");

        Account actual = loginService.validateLogIn(loginData);

        assertEquals("user", account.getLogin());
        assertEquals("user", account.getPassword());
    }

    @Test
    void validateLogIn_WithIncorrectCredentials_NullReturned() {
        AccountsDAO accountsDAO = mock(AccountsDAO.class);
        Account account = new Account("user2", "user2", 1);
        when(accountsDAO.getAll()).thenReturn(Arrays.asList(account));
        LoginService loginService = new LoginService(accountsDAO);
        Map<String, String> loginData = new HashMap<>();
        loginData.put("login", "user");
        loginData.put("password", "user");

        Account actual = loginService.validateLogIn(loginData);

        assertNull(actual);
    }
}