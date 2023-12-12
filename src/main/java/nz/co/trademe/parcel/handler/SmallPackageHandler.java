package nz.co.trademe.parcel.handler;

import jakarta.annotation.PostConstruct;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class SmallPackageHandler implements PackageRecommendationHandler {
    private PackageRecommendationHandler nextHandler;
    private final Double cost;
    private final PackageType packageType = PackageType.SMALL;
    private final Dimension maxDimension;
    private final MediumPackageHandler mediumPackageHandler;

    public SmallPackageHandler(@Value("${small.package.max.length}") Double maxLength,
                               @Value("${small.package.max.breadth}") Double maxBreadth,
                               @Value("${small.package.max.height}") Double maxHeight,
                               @Value("${small.package.cost}") Double cost,
                               @Autowired MediumPackageHandler mediumPackageHandler) {
        this.cost = cost;
        this.maxDimension = new Dimension(maxLength, maxBreadth, maxHeight);
        this.mediumPackageHandler = mediumPackageHandler;
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

    @PostConstruct
    public void postConstruct() {
        setNextHandler(mediumPackageHandler);
    }
}
