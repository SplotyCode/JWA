package de.splotycode.jwa.listener;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ListenerRegistry {

    private Table<Class<? extends Event>, Listener, Method> packetListener = HashBasedTable.create();

    public void addListener(Listener listener) {
        Class<? extends Listener> clazz = listener.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ListenerTarget.class) && method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                packetListener.put((Class<? extends Event>) method.getParameterTypes()[0], listener, method);
            }
        }
    }

    public void callEvent(Event event) {
        if(!packetListener.containsRow(event.getClass())) return;
        packetListener.row(event.getClass()).forEach(((listener, method) -> {
            try {
                method.invoke(listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }));
    }

}
