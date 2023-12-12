package nz.co.trademe.parcel.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageRecommendation;
import nz.co.trademe.parcel.model.PackageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public interface PackageRecommendationHandler {
    ResponseEntity recommendPackage(Dimension dimension);

    void setNextHandler(PackageRecommendationHandler nextHandler);

    PackageRecommendationHandler getNextHandler();

    default ResponseEntity determinePackage(Dimension dimension, Dimension maxDimension, PackageType packageType,
                                            Double cost) {
        if (checkDimension(dimension, maxDimension)) {
            try {
                return new ResponseEntity(new ObjectMapper().writeValueAsString(
                        new PackageRecommendation(packageType, cost)), HttpStatus.OK);
            } catch (JsonProcessingException jpe) {
                return new ResponseEntity(jpe.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        if (getNextHandler() != null) {
            return getNextHandler().recommendPackage(dimension);
        }

        return new ResponseEntity("No suitable package could be determined", HttpStatus.BAD_REQUEST);
    }

    default boolean checkDimension(Dimension dimension, Dimension maxDimension) {
        return dimension.length() <= maxDimension.length() &&
                dimension.breadth() <= maxDimension.breadth() &&
                dimension.height() <= maxDimension.height();
    }
}
