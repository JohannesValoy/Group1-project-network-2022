package no.ntnu.idata2304.group1.server.messages;

class LogOutputter{
    
    public enum types{
        Warning("\u001B[43m"),
        Error("\u001B[31m");
    
        private String color;
    
        types(String color){
            this.color = color;
        }
    }

public static void print(types type, String message){
    System.out.println(type+message);
}
}
