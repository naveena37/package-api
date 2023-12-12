package nz.co.trademe.parcel.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static nz.co.trademe.parcel.exception.CustomResponseEntityExceptionHandler.ERRORS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomResponseEntityExceptionHandlerTest {

    CustomResponseEntityExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CustomResponseEntityExceptionHandler();
    }

    @Test
    void handleValidationErrors() {
        FieldError error1 = new FieldError("weight", "weight", "Weight cannot be null");
        FieldError error2 = new FieldError("height", "height", "Height cannot be null");
        List<FieldError> fieldErrors = Arrays.asList(error1, error2);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult result = mock(BindingResult.class);
        when(exception.getBindingResult()).thenReturn(result);
        when(result.getFieldErrors()).thenReturn(fieldErrors);

        ResponseEntity<Map<String, List<String>>> responseEntity = handler.handleValidationErrors(exception);
        Map<String, List<String>> errorMap = responseEntity.getBody();
        List<String> errors = errorMap.get(ERRORS);

        assertSame(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(errors);
        assertTrue(errors.contains("Weight cannot be null"));
        assertTrue(errors.contains("Height cannot be null"));
    }
}