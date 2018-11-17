package client.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRepository {
    private Map<Class<?>, Object> cmds = new HashMap<>();

    public CommandRepository() {
    }

    public <T> void put(Class<T> type, T commands) {
        cmds.put(type, type.cast(commands));
    }

    public <T> T get(Class<T> type) {
        return type.cast(cmds.get(type));
    }
}