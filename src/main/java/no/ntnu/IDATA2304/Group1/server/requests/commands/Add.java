package no.ntnu.idata2304.group1.server.requests.commands;


public class Add {

    private Add() {};

    private final static Class<Commands> classType =


    private enum Commands {
        ADDTEMP(AccessLevel.Node, "AddTemprature", "addTemp");

        private AccessLevel minAccessLevel;
        private String jsonString;
        private String methodString;

        private Commands(AccessLevel minAccessLevel, String jsonString, String methodName) {
            this.minAccessLevel = minAccessLevel;
            this.jsonString = jsonString;
            this.methodString = methodName;
        }

        public String getJsonKeyword() {
            return jsonString;
        }

        public String getMethodName() {
            return jsonString;
        }
    }


    private static String commandToMethodName(String command) throws IllegalArgumentException {
        if (command == null || command.isEmpty()) {
            throw new IllegalArgumentException("The command word is invalid");
        }
        Commands[] commands = Commands.values();
        String methodName = "";
        for (int i = 0; i < commands.length && methodName.equals(""); i++) {
            if (commands[i].getJsonKeyword().equalsIgnoreCase(command)) {
            }
        }
        if (methodName.isEmpty()) {
            throw new IllegalArgumentException("There is no command called " + command);
        }
        return methodName;
    }

    public static Method dinf
}
