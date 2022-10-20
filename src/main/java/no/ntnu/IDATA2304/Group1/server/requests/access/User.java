package no.ntnu.idata2304.group1.server.requests.access;

import java.lang.reflect.Method;
import java.util.HashMap;

public class User {
    private HashMap<String, Method> accessible;

    User() throws NoSuchMethodException, SecurityException {
        this.accessible = new HashMap<>();
        accessible.put("Get", Commands.class.getMethod("get"));
    }
}
