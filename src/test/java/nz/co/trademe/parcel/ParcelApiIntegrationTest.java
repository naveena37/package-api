/*
 * Copyright 2014-2023 MyWave Limited. All rights reserved.
 */

package nz.co.trademe.parcel;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.trademe.parcel.model.PackageRecommendation;
import nz.co.trademe.parcel.model.PackageType;
import nz.co.trademe.parcel.model.ParcelRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringIntegrationTest
class ParcelApiIntegrationTest {

    private static final String API_PATH = "/api/v1/parcel/recommend";
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Return 200 OK when recommend endpoint is invoked")
    @Test
    void testValidRequest() throws Exception {
        ParcelRequest request = new ParcelRequest(20.00, 100.00, 100.00, 100.00);

        MvcResult response = mockMvc.perform(post(API_PATH)
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(request)))
				        .andExpect(status().isOk()).andReturn();

        PackageRecommendation actual = objectMapper.readValue(response.getResponse().getContentAsString(), PackageRecommendation.class);
        assertSame(actual.getPackageType(), PackageType.SMALL);
        assertEquals(5.0, actual.getCost());
    }

    @DisplayName("Return 400 Bad Request when dimensions exceeds maximum allowed values")
    @Test
    void testInvalidRequestExceedingMaxDimensions() throws Exception {
        ParcelRequest request = new ParcelRequest(26.00, 401.00, 601.00, 251.00);

        MvcResult response = mockMvc.perform(post(API_PATH)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Weight must be less than or equal to 25 kilos");
        assertThat(response.getResponse().getContentAsString()).contains("Length must be less than or equal to 400 mm");
        assertThat(response.getResponse().getContentAsString()).contains("Breadth must be less than or equal to 600 mm");
        assertThat(response.getResponse().getContentAsString()).contains("Height must be less than or equal to 250 mm");
    }

    @DisplayName("Return 400 Bad Request when dimensions are null")
    @Test
    void testInvalidRequestWithNullDimensions() throws Exception {
        ParcelRequest request = new ParcelRequest(null, null, null, null);

        MvcResult response = mockMvc.perform(post(API_PATH)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Weight must not be null");
        assertThat(response.getResponse().getContentAsString()).contains("Length must not be null");
        assertThat(response.getResponse().getContentAsString()).contains("Breadth must not be null");
        assertThat(response.getResponse().getContentAsString()).contains("Height must not be null");
    }

    @DisplayName("Return 400 Bad Request when dimensions are zero")
    @Test
    void testInvalidRequestWithZeroValues() throws Exception {
        ParcelRequest request = new ParcelRequest(0.0, 0.0, 0.0, 0.0);

        MvcResult response = mockMvc.perform(post(API_PATH)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Weight must be greater than or equal to 0.001 kilo");
        assertThat(response.getResponse().getContentAsString()).contains("Length must be greater than or equal to 1 mm");
        assertThat(response.getResponse().getContentAsString()).contains("Breadth must be greater than or equal to 1 mm");
        assertThat(response.getResponse().getContentAsString()).contains("Height must be greater than or equal to 1 mm");
    }
}
