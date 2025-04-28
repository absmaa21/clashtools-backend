package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.ProductionAndStorage;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PRODUCTION_AND_STORAGE")
public class ProductionAndStorageAttributeValue extends AttributeValue<ProductionAndStorage> {

    @Embedded
    private ProductionAndStorage value;

    @Override
    public ProductionAndStorage getValue() {
        return value;
    }

    @Override
    public void setValue(ProductionAndStorage value) {
        this.value = value;
    }
}

