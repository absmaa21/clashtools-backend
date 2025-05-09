package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment {

    @Id
    @SequenceGenerator(name = "equipment_seq", sequenceName = "equipment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_seq")
    private Long id;

    @ManyToMany
    private List<Attribute> activeAbilities;

    @ManyToMany
    private List<Attribute> passiveAbilities;

    @ManyToMany
    private List<Attribute> heroBoosts;
}
