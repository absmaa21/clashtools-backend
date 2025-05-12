package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "attribute_seq", sequenceName = "attribute_sequence", allocationSize = 50)
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_seq")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attribute_name_id", nullable = false)
    private AttributeName attributeName;

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttributeTranslation> translations;

    @ManyToMany(mappedBy = "attributes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<BaseEntityLevel> baseEntityLevels;
}
