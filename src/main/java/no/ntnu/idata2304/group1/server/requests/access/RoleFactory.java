package no.ntnu.idata2304.group1.server.requests.access;

/**
 * A factory for creating a role object
 * <p>
 * It is used to create a role object based on a given role name
 */
public class RoleFactory {
    /**
     * The enum Roles.
     */
    enum Roles {
        /**
         * Admin roles.
         */
        ADMIN("Admin"),
        /**
         * User roles.
         */
        USER("User"),
        /**
         * Node roles.
         */
        NODE("Node");

        private String methodString;

        private Roles(String methodName) {
            this.methodString = methodName;
        }

    }

    /**
     * Gets role.
     *
     * @param role the role
     * @return the role
     */
    public static Role getRole(String role) {
        Roles[] roles = Roles.values();
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].methodString.equalsIgnoreCase(role)) {
                return new Role();
            }
        }
        return null;
    }
}
