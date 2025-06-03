package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entity representing a base game object")
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_seq")
    @SequenceGenerator(name = "base_entity_seq", sequenceName = "base_entity_seq", allocationSize = 10)
    @Schema(description = "Unique identifier of the base entity", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Name of the base entity")
    private String name;

    @OneToMany(mappedBy = "baseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Set of levels associated with this base entity")
    private Set<BaseEntityLevel> baseEntityLevels = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @Schema(description = "Category associated with this base entity")
    private Category category;
}
