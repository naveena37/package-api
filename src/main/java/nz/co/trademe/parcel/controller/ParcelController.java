package nz.co.trademe.parcel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nz.co.trademe.parcel.handler.SmallPackageHandler;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageRecommendation;
import nz.co.trademe.parcel.model.ParcelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parcel")
@Tag(name = "Parcel", description = "Parcel APIs")
public class ParcelController {

    private final SmallPackageHandler handler;

    public ParcelController(@Autowired SmallPackageHandler handler) {
        this.handler = handler;
    }
    @Operation(
            summary = "Recommends a parcel type for the given dimensions",
            description = "Recommends a parcel type for the given dimensions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PackageRecommendation.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "422", content = { @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json") }) })
    @PostMapping("/recommend")
    @Validated
    public ResponseEntity<?> recommendPackage(@Valid @RequestBody(required = false) ParcelRequest request) {

        return handler.recommendPackage(
                new Dimension(request.length(), request.breadth(), request.height()));
    }
}
