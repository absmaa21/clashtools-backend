package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "base_entity_name_seq", sequenceName = "base_entity_name_seq", allocationSize = 1)
public class BaseEntityName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_entity_name_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "base_entity_name_category",
        joinColumns = @JoinColumn(name = "base_entity_name_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
}
