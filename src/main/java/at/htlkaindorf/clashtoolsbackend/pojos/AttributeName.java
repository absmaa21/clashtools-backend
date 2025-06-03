package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attribute_name")
@SequenceGenerator(name = "attribute_name_seq", sequenceName = "attribute_name_sequence", allocationSize = 50) // allocationSize erhöht auf 50
public class AttributeName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_name_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "attributeName", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true) // Cascade angepasst
    private Set<Attribute> attributes;

}
