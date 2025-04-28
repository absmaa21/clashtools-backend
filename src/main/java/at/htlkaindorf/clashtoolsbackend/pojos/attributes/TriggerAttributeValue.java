package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.Trigger;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("TRIGGER")
public class TriggerAttributeValue extends AttributeValue<Trigger> {

    @Enumerated(EnumType.STRING)
    private Trigger value;

    @Override
    public Trigger getValue() {
        return value;
    }

    @Override
    public void setValue(Trigger value) {
        this.value = value;
    }
}
