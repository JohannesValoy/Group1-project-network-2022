package no.ntnu.idata2304.group1.server.messages;

public class LogOutputer {

    public enum MessageType {
        WARNING("\u001B[43m", "[Warning]"), ERROR("\u001B[31m", "[ERROR]"), INFO("", "[INFO]");

        private String color;
        private String type;

        MessageType(String color, String type) {
            this.color = color;
            this.type = type;
        }

        public String getColor() {
            return color;
        }

        public String getType() {
            return type;
        }
    }

    public static void print(MessageType type, String message) {
        if (type == null) {
            type = MessageType.INFO;
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("You need to declare a message");
        }
        System.out.println(type.getColor() + type.getType() + message);
    }
}
