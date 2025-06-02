package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

/**
 * Entity representing a specific level of a base entity.
 * This entity contains information about a base entity at a specific level,
 * including its attributes, resource requirements, and upgrade details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "base_entity_level")
public class BaseEntityLevel {

    /**
     * Unique identifier for the base entity level.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_level_seq")
    @SequenceGenerator(name = "base_entity_level_seq", sequenceName = "base_entity_level_seq", allocationSize = 10)
    private Long id;

    /**
     * The base entity that this level belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_entity_id", nullable = false)
    private BaseEntity baseEntity;

    /**
     * The level information for this base entity level.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    /**
     * The attributes associated with this base entity level.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "base_entity_level_attributes",
        joinColumns = @JoinColumn(name = "base_entity_level_id"),
        inverseJoinColumns = @JoinColumn(name = "attribute_id")
    )
    private Set<Attribute> attributes;

    /**
     * The type of resource required for upgrading this base entity level.
     */
    @NotNull
    @Column(nullable = false)
    private ResourceType resourceType;

    /**
     * The cost to upgrade to this level, in the specified resource type.
     */
    @NotNull
    @Column(nullable = false)
    private Integer upgradeCost;

    /**
     * The time required to upgrade to this level, in seconds.
     */
    @NotNull
    @Column(nullable = false)
    private Integer upgradeTime;

    /**
     * The path to the image representing this base entity level.
     * This can be a relative path within the application or a full URL.
     * The path should be properly formatted and not contain directory traversal sequences.
     */
    @NotBlank
    @URL(message = "Image path must be a valid URL ending with a supported image extension")
    private String imgPath;
}
