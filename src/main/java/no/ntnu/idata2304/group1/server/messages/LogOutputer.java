package no.ntnu.idata2304.group1.server.messages;

/**
 * The type Log outputer.
 */
public class LogOutputer {

    /**
     * The enum Message type.
     */
    public enum MessageType {
        /**
         * Warning message type.
         */
        WARNING("\u001B[43m", "[Warning]"),
        /**
         * Error message type.
         */
        ERROR("\u001B[31m", "[ERROR]"),
        /**
         * Info message type.
         */
        INFO("\u001B[37m",
                "[INFO]");

        private String color;
        private String type;

        MessageType(String color, String type) {
            this.color = color;
            this.type = type;
        }

        /**
         * Gets color.
         *
         * @return the color
         */
        public String getColor() {
            return color;
        }

        /**
         * Gets type.
         *
         * @return the type
         */
        public String getType() {
            return type;
        }
    }

    /**
     * Print.
     *
     * @param type    the type
     * @param message the message
     */
    public static synchronized void print(MessageType type, String message) {
        if (type == null) {
            type = MessageType.INFO;
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("You need to declare a message");
        }
        System.out.println(type.getColor() + " " + type.getType() + message);
    }
}
