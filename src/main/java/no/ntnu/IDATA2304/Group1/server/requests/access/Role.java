package no.ntnu.idata2304.group1.server.requests.access;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class Role {
    ArrayList<Method> commands;

    Role() {
        commands = new ArrayList<>();
    }

    Role(ArrayList<Method> commands) {
        this.commands = new ArrayList<>(commands);
    }

    public Iterator<Method> getCommands() {
        return commands.iterator();
    }

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
