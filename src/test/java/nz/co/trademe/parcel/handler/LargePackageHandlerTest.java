package nz.co.trademe.parcel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageRecommendation;
import nz.co.trademe.parcel.model.PackageType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class LargePackageHandlerTest {
    private final LargePackageHandler handler = new LargePackageHandler(
            400.00, 600.00, 250.00, 8.50);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRecommendPackageSuccess() throws Exception {
        ResponseEntity responseEntity = handler.recommendPackage(new Dimension(400.00, 600.00, 250.00));
        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK));

        PackageRecommendation actual = objectMapper.readValue((String) responseEntity.getBody(), PackageRecommendation.class);
        assertSame(actual.getPackageType(), PackageType.LARGE);
        assertEquals(8.5, actual.getCost());
    }

    @Test
    void testRecommendPackageFailure() {
        ResponseEntity responseEntity = handler.recommendPackage(new Dimension(500.00, 600.00, 250.00));
        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        assertEquals("No suitable package could be determined", (responseEntity.getBody()));
    }
}