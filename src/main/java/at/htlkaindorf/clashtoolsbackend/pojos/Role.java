package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(name = "role_seq", sequenceName = "role_sequence", allocationSize = 1)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // z. B. "ROLE_USER", "ROLE_ADMIN"
}
