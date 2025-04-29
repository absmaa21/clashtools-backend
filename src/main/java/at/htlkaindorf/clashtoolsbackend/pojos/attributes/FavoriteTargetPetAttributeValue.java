package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.FavoriteTargetPet;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FAVORITE_TARGET_PET")
public class FavoriteTargetPetAttributeValue extends AttributeValue<FavoriteTargetPet> {

    @Embedded
    private FavoriteTargetPet value;

    @Override
    public FavoriteTargetPet getValue() {
        return value;
    }

    @Override
    public void setValue(FavoriteTargetPet value) {
        this.value = value;
    }
}
