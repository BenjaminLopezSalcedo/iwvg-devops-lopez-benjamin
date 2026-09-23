package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessageTest {

    @Test
    void testToString() {
        Exception exception = new IllegalArgumentException("Invalid argument");
        ErrorMessage errorMessage = new ErrorMessage(exception, 400);

        assertEquals(
                "ErrorMessage{error='IllegalArgumentException', message='Invalid argument', code=400}",
                errorMessage.toString()
        );
    }
}