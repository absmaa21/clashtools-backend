package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attribute_translation")
@SequenceGenerator(name = "attribute_translation_seq", sequenceName = "attribute_translation_seq", allocationSize = 50)
public class AttributeTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attribute_translation_seq")
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(nullable = false)
    private String name;
}
