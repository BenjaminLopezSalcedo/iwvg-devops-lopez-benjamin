package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionHandlerTest {

    @Test
    void testNoResourceFoundRequest() {
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ErrorMessage result = handler.noResourceFoundRequest(
                new NoResourceFoundException(HttpMethod.GET, "/ruta-inexistente")
        );

        assertEquals(404, result.getCode());
    }

    @Test
    void testException() {
        ApiExceptionHandler handler = new ApiExceptionHandler();

        ErrorMessage result = handler.exception(new Exception("ERROR"));

        assertEquals(500, result.getCode());
    }
}
