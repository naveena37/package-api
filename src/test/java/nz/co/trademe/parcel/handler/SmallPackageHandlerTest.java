package nz.co.trademe.parcel.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageRecommendation;
import nz.co.trademe.parcel.model.PackageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class SmallPackageHandlerTest {

    private LargePackageHandler largePackageHandler;
    private MediumPackageHandler mediumPackageHandler;
    private SmallPackageHandler smallPackageHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        largePackageHandler = new LargePackageHandler(
                400.00, 600.00, 250.00, 8.50);
        mediumPackageHandler = new MediumPackageHandler(
                300.00, 400.00, 200.00, 7.50, largePackageHandler);
        smallPackageHandler = new SmallPackageHandler(
                200.00, 300.00, 150.00, 5.00, mediumPackageHandler);

        mediumPackageHandler.setNextHandler(largePackageHandler);
        smallPackageHandler.setNextHandler(mediumPackageHandler);
    }

    @Test
    void testRecommendPackageSuccess() throws Exception {
        ResponseEntity responseEntity = smallPackageHandler.recommendPackage(new Dimension(200.00, 300.00, 150.00));
        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK));

        PackageRecommendation actual = objectMapper.readValue((String) responseEntity.getBody(), PackageRecommendation.class);
        assertSame(actual.getPackageType(), PackageType.SMALL);
        assertEquals(5.0, actual.getCost());
    }

    @Test
    void testRecommendNextSizePackageSuccess() throws Exception{
        ResponseEntity responseEntity = smallPackageHandler.recommendPackage(new Dimension(400.00, 400.00, 200.00));
        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.OK));

        PackageRecommendation actual = objectMapper.readValue((String) responseEntity.getBody(), PackageRecommendation.class);
        assertSame(actual.getPackageType(), PackageType.LARGE);
        assertEquals(8.5, actual.getCost());
    }

    @Test
    void testRecommendPackageFailure() {
        ResponseEntity responseEntity = smallPackageHandler.recommendPackage(new Dimension(500.00, 600.00, 250.00));
        assertTrue(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST));
        assertEquals("No suitable package could be determined", (responseEntity.getBody()));
    }
}