package at.htlkaindorf.clashtoolsbackend.pojos;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "attribute_translation")
public class AttributeTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode; // z.B. "de", "en", "fr"

    @Column(nullable = false)
    private String name; // Übersetzter Name des Attributes
}
