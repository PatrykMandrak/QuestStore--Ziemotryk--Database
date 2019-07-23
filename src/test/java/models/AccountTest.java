package models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void account_WithCorrectPermissions_AccountCreated(int permission) {
        Account account = new Account("Login", "Password", permission);

        assertEquals(permission, account.getPermission());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, 123, 33342, -123213, -1})
    void account_WithIncorrectPermissions_ExceptionThrown(int permission) {
        assertThrows(IllegalArgumentException.class, () -> new Account("Login", "Password", permission));
    }

}