package no.ntnu.idata2304.group1.server.requests.commands;

import java.lang.reflect.Method;

public enum AccessLevel {
    User(0), Node(1), Admin(2);

    private int level;

    AccessLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    /**
     * 
     * @param command
     * @param accesslevel
     * @return
     */
    public static Method getCommand(String command, AccessLevel accesslevel) {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("This is not a valid command");
        }

    }
}
