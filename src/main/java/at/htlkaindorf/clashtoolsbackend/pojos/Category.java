package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum representing different categories of game entities.
 */
@Schema(description = "Categories of game entities")
public enum Category {
    TROOP,
    HERO,
    TRAP,
    PET,
    EQUIPMENT,
    SIEGE_MACHINE,
    SPELL,
    ARMY,
    RESOURCES,
    DEFENSE,
    WALL,
}
