package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.Range;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("RANGE")
public class RangeAttributeValue extends AttributeValue<Range> {

    @Embedded
    private Range value;

    @Override
    public Range getValue() {
        return value;
    }

    @Override
    public void setValue(Range value) {
        this.value = value;
    }
}
