package services;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FormServiceTest {

    private HttpExchange exchange;

    @BeforeEach
    void setup() {
        exchange = mock(HttpExchange.class);
    }

    @ParameterizedTest
    @MethodSource
    void getInputsStringMap_KeyInMap_MapContainsValueUnderKey(String url, Map<String, String> expected) throws IOException {
        InputStream is = new ByteArrayInputStream(url.getBytes(StandardCharsets.UTF_8));
        when(exchange.getRequestBody()).thenReturn(is);

        Map<String, String> formMap = FormService.getInputsStringMap(exchange);

        for (String key : expected.keySet()) {
            assertEquals(expected.get(key), formMap.get(key));
        }
    }

    static Stream<Arguments> getInputsStringMap_KeyInMap_MapContainsValueUnderKey() {
        Map<String, String> expected1 = new HashMap<>();
        expected1.put("value", "myval");
        expected1.put("otherval", "other");
        String url1 = "value=myval&otherval=other";

        Map<String, String> expected2 = new HashMap<>();
        expected2.put("value", "value@value value value=value");
        expected2.put("otherval", "other");
        String url2 = "value=value%40value%20value%20value%3Dvalue&otherval=other";

        return Stream.of(
                Arguments.arguments(url1, expected1),
                Arguments.arguments(url2, expected2)
        );
    }

    @Test
    void getInputsStringMap_KeyNotInMap_MapNotContainsValueUnderKey() throws IOException {
        String url = "valueas=value&otherval=other";
        InputStream is = new ByteArrayInputStream(url.getBytes(StandardCharsets.UTF_8));
        when(exchange.getRequestBody()).thenReturn(is);

        Map<String, String> formMap = FormService.getInputsStringMap(exchange);

        assertNull(formMap.get("value"));
    }
}