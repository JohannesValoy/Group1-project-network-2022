package no.ntnu.idata2304.group1.server.requests.access;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Roles for different users and their permissions
 */
// TODO: Add a more flexible way of adding roles/commands
public class Role {
    /**
     * The Commands.
     */
    ArrayList<Method> commands;

    /**
     * Instantiates a new Role.
     */
    Role() {
        commands = new ArrayList<>();
    }

    /**
     * Instantiates a new Role.
     *
     * @param commands the commands
     */
    Role(ArrayList<Method> commands) {
        this.commands = new ArrayList<>(commands);
    }

    /**
     * Gets commands.
     *
     * @return the commands
     */
    public Iterator<Method> getCommands() {
        return commands.iterator();
    }

    /**
     * Run command.
     *
     * @param command the command
     */
    public void runCommand(String command) {
        Iterator<Method> commandIterator = getCommands();
        while (commandIterator.hasNext()) {
            Method method = commandIterator.next();
            if (method.getName().equalsIgnoreCase(command)) {
                try {
                    method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
