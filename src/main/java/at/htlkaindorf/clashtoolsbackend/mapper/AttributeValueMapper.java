package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeValueDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.attributes.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeValueMapper {

    /**
     * Converts an AttributeValue entity to an AttributeValueDTO
     * @param attributeValue the entity to convert
     * @return the converted DTO
     */
    @Mapping(source = "attribute.id", target = "attributeId")
    @Mapping(source = "attribute.attributeName.name", target = "attributeName")
    @Mapping(source = "baseEntityLevel.id", target = "baseEntityLevelId")
    @Mapping(source = "attributeValue", target = "valueType", qualifiedByName = "getValueType")
    @Mapping(source = "attributeValue", target = "value", qualifiedByName = "getValue")
    AttributeValueDTO toDTO(AttributeValue<?> attributeValue);

    /**
     * Converts a list of AttributeValue entities to a list of AttributeValueDTOs
     * @param attributeValues the entities to convert
     * @return the converted DTOs
     */
    List<AttributeValueDTO> toDTOList(List<AttributeValue<?>> attributeValues);

    /**
     * Determines the value type based on the concrete AttributeValue class
     * @param attributeValue the attribute value
     * @return the value type as a string
     */
    @Named("getValueType")
    default String getValueType(AttributeValue<?> attributeValue) {
        if (attributeValue instanceof IntegerAttributeValue) {
            return "INTEGER";
        } else if (attributeValue instanceof StringAttributeValue) {
            return "STRING";
        } else if (attributeValue instanceof RangeAttributeValue) {
            return "RANGE";
        } else if (attributeValue instanceof DamageTypeAttributeValue) {
            return "DAMAGE_TYPE";
        } else if (attributeValue instanceof DeployPositionAttributeValue) {
            return "DEPLOY_POSITION";
        } else if (attributeValue instanceof EquipmentAttributeValue) {
            return "EQUIPMENT";
        } else if (attributeValue instanceof FavoriteTargetAttributeValue) {
            return "FAVORITE_TARGET";
        } else if (attributeValue instanceof FavoriteTargetPetAttributeValue) {
            return "FAVORITE_TARGET_PET";
        } else if (attributeValue instanceof ProductionAndStorageAttributeValue) {
            return "PRODUCTION_AND_STORAGE";
        } else if (attributeValue instanceof TargetTypeAttributeValue) {
            return "TARGET_TYPE";
        } else if (attributeValue instanceof TriggerAttributeValue) {
            return "TRIGGER";
        } else {
            return "UNKNOWN";
        }
    }

    /**
     * Gets the value from the concrete AttributeValue class
     * @param attributeValue the attribute value
     * @return the value as an Object
     */
    @Named("getValue")
    default Object getValue(AttributeValue<?> attributeValue) {
        if (attributeValue == null) {
            return null;
        }

        // For the value, we need to handle each subclass differently due to protected access
        if (attributeValue instanceof IntegerAttributeValue) {
            return ((IntegerAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof StringAttributeValue) {
            return ((StringAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof RangeAttributeValue) {
            return ((RangeAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof DamageTypeAttributeValue) {
            return ((DamageTypeAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof DeployPositionAttributeValue) {
            return ((DeployPositionAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof EquipmentAttributeValue) {
            return ((EquipmentAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof FavoriteTargetAttributeValue) {
            return ((FavoriteTargetAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof FavoriteTargetPetAttributeValue) {
            return ((FavoriteTargetPetAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof ProductionAndStorageAttributeValue) {
            return ((ProductionAndStorageAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof TargetTypeAttributeValue) {
            return ((TargetTypeAttributeValue) attributeValue).getValue();
        } else if (attributeValue instanceof TriggerAttributeValue) {
            return ((TriggerAttributeValue) attributeValue).getValue();
        }

        return null;
    }
}
