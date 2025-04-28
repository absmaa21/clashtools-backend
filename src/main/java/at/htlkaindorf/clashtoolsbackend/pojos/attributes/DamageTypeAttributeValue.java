package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.DamageType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("DAMAGE_TYPE")
public class DamageTypeAttributeValue extends AttributeValue<DamageType> {

    @Enumerated(EnumType.STRING)
    private DamageType value;

    @Override
    public DamageType getValue() {
        return value;
    }

    @Override
    public void setValue(DamageType value) {
        this.value = value;
    }
}
