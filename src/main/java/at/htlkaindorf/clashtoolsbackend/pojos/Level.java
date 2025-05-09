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
@Table(name = "level")
@SequenceGenerator(name = "level_seq", sequenceName = "level_sequence", allocationSize = 10)
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "level_seq")
    private Long id;

    @Column(nullable = false)
    private int level;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<BaseEntityLevel> baseEntityLevels;
}
