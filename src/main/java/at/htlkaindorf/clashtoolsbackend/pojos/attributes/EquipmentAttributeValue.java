package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.Equipment;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("EQUIPMENT")
public class EquipmentAttributeValue extends AttributeValue<Equipment> {

    @Embedded
    private Equipment value;

    @Override
    public Equipment getValue() {
        return value;
    }

    @Override
    public void setValue(Equipment value) {
        this.value = value;
    }
}
