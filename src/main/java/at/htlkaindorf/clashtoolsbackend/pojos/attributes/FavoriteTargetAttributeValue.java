package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.FavoriteTarget;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("FAVORITE_TARGET")
public class FavoriteTargetAttributeValue extends AttributeValue<FavoriteTarget> {

    @Enumerated(EnumType.STRING)
    private FavoriteTarget value;

    @Override
    public FavoriteTarget getValue() {
        return value;
    }

    @Override
    public void setValue(FavoriteTarget value) {
        this.value = value;
    }
}
