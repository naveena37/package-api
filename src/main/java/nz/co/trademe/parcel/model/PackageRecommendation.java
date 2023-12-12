package nz.co.trademe.parcel.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class PackageRecommendation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final PackageType packageType;
    private final Double cost;

    @JsonCreator
    public PackageRecommendation(@JsonProperty("packageType") PackageType packageType,
                                 @JsonProperty("cost") Double cost) {
        super();
        this.packageType = packageType;
        this.cost = cost;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public Double getCost() {
        return cost;
    }
}
