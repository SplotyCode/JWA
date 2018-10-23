package de.splotycode.jwa.listener.events;

import de.splotycode.jwa.core.Status;
import de.splotycode.jwa.listener.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Events triggers when the Status of the Whatapp connection changes
 */
@AllArgsConstructor
@Getter
@Setter
public class StatusChangeEvent extends Event {

    private Status oldStatus, newStatus;

}
