package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
}
