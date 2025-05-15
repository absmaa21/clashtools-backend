package at.htlkaindorf.clashtoolsbackend.constants;

/**
 * Enum for user roles in the application.
 * This enum provides centralized access to role names used throughout the application,
 * ensuring consistency and type safety.
 */
public enum RoleConstants {
    /**
     * Standard user role with basic permissions.
     */
    ROLE_USER("ROLE_USER"),

    /**
     * Administrator role with elevated permissions.
     */
    ROLE_ADMIN("ROLE_ADMIN");

    private final String roleName;

    RoleConstants(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the string representation of the role name.
     * This is the value stored in the database.
     *
     * @return The role name as a string
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Finds a RoleConstants enum value by its string role name.
     *
     * @param roleName The string role name to search for
     * @return The matching RoleConstants enum value, or null if not found
     */
    public static RoleConstants fromRoleName(String roleName) {
        for (RoleConstants role : values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }
}
