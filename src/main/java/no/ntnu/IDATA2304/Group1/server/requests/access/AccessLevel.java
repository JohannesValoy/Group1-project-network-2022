package no.ntnu.idata2304.group1.server.requests.access;

public enum AccessLevel {
    /**
     * Read Access
     */
    USER(0),
    /**
     * Add temp values
     */
    NODE(1),
    /**
     * Add, Remove rooms
     */
    ADMIN(2);

    private int level;

    AccessLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
