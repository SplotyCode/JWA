package de.splotycode.jwa.listener;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class Manages the Listeners
 * You can register and call Events with it
 */
public class ListenerRegistry {

    private Table<Class<? extends Event>, Listener, Method> packetListener = HashBasedTable.create();

    /**
     * Registers all Events in an Listener
     * @param listener the listener that you want to register
     */
    public void addListener(Listener listener) {
        Class<? extends Listener> clazz = listener.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ListenerTarget.class) && method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                packetListener.put((Class<? extends Event>) method.getParameterTypes()[0], listener, method);
            }
        }
    }

    /**
     * Call a Event to all Listeners that listening on that Even Type
     * @param event the event that you want to call
     */
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
