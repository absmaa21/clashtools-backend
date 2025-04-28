package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.TargetType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("TARGET_TYPE")
public class TargetTypeAttributeValue extends AttributeValue<TargetType> {

    @Enumerated(EnumType.STRING)
    private TargetType value;

    @Override
    public TargetType getValue() {
        return value;
    }

    @Override
    public void setValue(TargetType value) {
        this.value = value;
    }
}
