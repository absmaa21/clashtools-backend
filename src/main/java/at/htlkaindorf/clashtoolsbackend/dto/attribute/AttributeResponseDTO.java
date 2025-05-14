package at.htlkaindorf.clashtoolsbackend.dto.attribute;

import lombok.Data;

import java.util.List;

@Data
public class AttributeResponseDTO {
    private Long id;

    private AttributeNameDTO attributeName;

    private List<AttributeTranslationDTO> translations;
}
