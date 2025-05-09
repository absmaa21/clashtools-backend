package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "value_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "attribute_value_seq", sequenceName = "attribute_value_sequence", allocationSize = 1)
public abstract class AttributeValue<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_value_seq")
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "base_entity_level_id", nullable = false)
    private BaseEntityLevel baseEntityLevel;

    protected abstract T getValue();
    protected abstract void setValue(T value);
}
