package nz.co.trademe.parcel.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParcelRequest(
        @NotNull(message = "Weight " + "{jakarta.validation.constraints.NotNull.message}")
        @DecimalMin(value = "0.001", message = "Weight " + "{jakarta.validation.constraints.DecimalMin.message}" + " kilo")
        @Max(value = 25, message = "Weight " + "{jakarta.validation.constraints.Max.message}" + " kilos")
        Double weight,
        @NotNull(message = "Length " + "{jakarta.validation.constraints.NotNull.message}")
        @Min(value = 1, message = "Length " + "{jakarta.validation.constraints.Min.message}" + " mm")
        @Max(value = 400, message = "Length " + "{jakarta.validation.constraints.Max.message}" + " mm")
        Double length,
        @NotNull(message = "Breadth " + "{jakarta.validation.constraints.NotNull.message}")
        @Min(value = 1, message = "Breadth " + "{jakarta.validation.constraints.Min.message}" + " mm")
        @Max(value = 600, message = "Breadth " + "{jakarta.validation.constraints.Max.message}" + " mm")
        Double breadth,
        @NotNull(message = "Height " + "{jakarta.validation.constraints.NotNull.message}")
        @Min(value = 1, message = "Height " + "{jakarta.validation.constraints.Min.message}" + " mm")
        @Max(value = 250, message = "Height " + "{jakarta.validation.constraints.Max.message}" + " mm")
        Double height) { }
