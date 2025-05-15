package at.htlkaindorf.clashtoolsbackend.dto.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeValueDTO {
    private Long id;

    private Long attributeId;

    private String attributeName;

    private Long baseEntityLevelId;

    private String valueType;

    private Object value;
}
