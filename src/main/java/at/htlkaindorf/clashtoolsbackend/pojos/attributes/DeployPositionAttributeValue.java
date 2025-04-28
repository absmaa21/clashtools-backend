package at.htlkaindorf.clashtoolsbackend.pojos.attributes;

import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.DeployPosition;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("DEPLOY_POSITION")
public class DeployPositionAttributeValue extends AttributeValue<DeployPosition> {

    @Enumerated(EnumType.STRING)
    private DeployPosition value;

    @Override
    public DeployPosition getValue() {
        return value;
    }

    @Override
    public void setValue(DeployPosition value) {
        this.value = value;
    }
}
