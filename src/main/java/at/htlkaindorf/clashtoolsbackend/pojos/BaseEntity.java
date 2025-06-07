package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = {
    @Index(name = "idx_base_entity_category", columnList = "category")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "baseEntityLevels")
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

    @OneToMany(mappedBy = "baseEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Schema(description = "Set of levels associated with this base entity")
    @Builder.Default
    private Set<BaseEntityLevel> baseEntityLevels = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    @Schema(description = "Category associated with this base entity")
    private Category category;
}
