package at.htlkaindorf.clashtoolsbackend.dto.attribute;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AttributeRequestDTO {
    @NotNull
    private Long attributeNameId;

    private List<AttributeTranslationDTO> translations;
}
