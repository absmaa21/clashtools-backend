package at.htlkaindorf.clashtoolsbackend.dto.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttributeTranslationDTO {
    private Long id;

    @NotBlank
    private String languageCode;

    @NotBlank
    private String name;
}
