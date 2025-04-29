package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "value_type")
public abstract class AttributeValue<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //private EntityLevel entityLevel;

    @ManyToOne
    private Attribute attribute;

    public abstract T getValue();
    public abstract void setValue(T value);
}

