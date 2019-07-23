package services;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UrlIdServiceTest {

    @ParameterizedTest
    @CsvSource({"test/test/3, 3, 2",
            "test/test/test/test/5, 5, 4",
            "2, 2, 0"})
    void getIdByUrl_WhenUrlHasID_IdParsed(String path, int expectedId, int index) throws URISyntaxException {
        HttpExchange exchange = mock(HttpExchange.class);
        URI uri = new URI(path);
        when(exchange.getRequestURI()).thenReturn(uri);

        int id = UrlIdService.getIdByUrl(exchange, index);

        assertEquals(expectedId, id);
    }
}