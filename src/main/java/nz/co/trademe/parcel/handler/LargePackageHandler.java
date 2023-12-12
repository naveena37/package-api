package nz.co.trademe.parcel.handler;

import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class LargePackageHandler implements PackageRecommendationHandler {
    private PackageRecommendationHandler nextHandler;
    private final Double cost;
    private final PackageType packageType = PackageType.LARGE;
    private final Dimension maxDimension;

    public LargePackageHandler(@Value("${large.package.max.length}") Double maxLength,
                               @Value("${large.package.max.breadth}") Double maxBreadth,
                               @Value("${large.package.max.height}") Double maxHeight,
                               @Value("${large.package.cost}") Double cost) {
        this.cost = cost;
        this.maxDimension = new Dimension(maxLength, maxBreadth, maxHeight);
    }

    @Override
    public void setNextHandler(PackageRecommendationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public ResponseEntity recommendPackage(Dimension dimension) {
        return determinePackage(dimension, maxDimension, packageType, cost);
    }

    @Override
    public PackageRecommendationHandler getNextHandler() {
        return nextHandler;
    }
}
