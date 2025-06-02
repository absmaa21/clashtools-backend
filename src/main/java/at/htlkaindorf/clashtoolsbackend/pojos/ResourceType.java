package at.htlkaindorf.clashtoolsbackend.pojos;

/**
 * Enum representing the different types of resources in the game.
 * These resources are used for upgrading base entities and other game elements.
 *
 * Classic resources:
 * - GOLD: Standard resource used for most buildings and upgrades
 * - ELIXIR: Used for troops, spells, and certain buildings
 * - DARK_ELIXIR: Premium resource used for hero upgrades and dark troops
 *
 * Special resources:
 * - SHINY_ORE: Special resource for certain upgrades
 * - GLOWY_ORE: Special resource for certain upgrades
 * - STARRY_ORE: Special resource for certain upgrades
 */
public enum ResourceType {
    /**
     * Standard resource used for most buildings and upgrades
     */
    GOLD,

    /**
     * Used for troops, spells, and certain buildings
     */
    ELIXIR,

    /**
     * Premium resource used for hero upgrades and dark troops
     */
    DARK_ELIXIR,

    /**
     * Special resource for certain upgrades
     */
    SHINY_ORE,

    /**
     * Special resource for certain upgrades
     */
    GLOWY_ORE,

    /**
     * Special resource for certain upgrades
     */
    STARRY_ORE
}
