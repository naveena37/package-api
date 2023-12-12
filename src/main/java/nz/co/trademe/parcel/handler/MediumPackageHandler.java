package nz.co.trademe.parcel.handler;

import jakarta.annotation.PostConstruct;
import nz.co.trademe.parcel.model.Dimension;
import nz.co.trademe.parcel.model.PackageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class MediumPackageHandler implements PackageRecommendationHandler {
    private PackageRecommendationHandler nextHandler;
    private final Double cost;
    private final PackageType packageType = PackageType.MEDIUM;
    private final Dimension maxDimension;

    private final LargePackageHandler largePackageHandler;

    public MediumPackageHandler(@Value("${medium.package.max.length}") Double maxLength,
                                @Value("${medium.package.max.breadth}") Double maxBreadth,
                                @Value("${medium.package.max.height}") Double maxHeight,
                                @Value("${medium.package.cost}") Double cost,
                                @Autowired LargePackageHandler largePackageHandler) {
        this.cost = cost;
        this.maxDimension = new Dimension(maxLength, maxBreadth, maxHeight);
        this.largePackageHandler = largePackageHandler;
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
        setNextHandler(largePackageHandler);
    }
}
