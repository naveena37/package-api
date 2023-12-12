package nz.co.trademe.parcel.controller;

import nz.co.trademe.parcel.handler.SmallPackageHandler;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.ParcelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParcelControllerTest {

    ParcelController controller;
    SmallPackageHandler handler;

    @BeforeEach
    void setUp() {
        handler = mock(SmallPackageHandler.class);
        controller = new ParcelController(handler);
    }

    @Test
    void recommendPackage() {
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        when(handler.recommendPackage(any(Dimension.class))).thenReturn(responseEntity);
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        ParcelRequest request = mock(ParcelRequest.class);

        ResponseEntity<?> actual = controller.recommendPackage(request);
        assertSame(HttpStatus.OK, actual.getStatusCode());
    }
}