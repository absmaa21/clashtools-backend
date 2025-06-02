package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "base_entity_level")
public class BaseEntityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_level_seq")
    @SequenceGenerator(name = "base_entity_level_seq", sequenceName = "base_entity_level_seq", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_entity_id", nullable = false)
    private BaseEntity baseEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "base_entity_level_attributes",
        joinColumns = @JoinColumn(name = "base_entity_level_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private Set<Attribute> attributes;

    private ResourceType resourceType;

    private Integer upgradeCost;

    /**
     * Given in seconds
     */
    private Integer upgradeTime;

    private String imgPath;
}
