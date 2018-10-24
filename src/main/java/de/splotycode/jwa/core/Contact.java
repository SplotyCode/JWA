package de.splotycode.jwa.core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Contact {

    private String number;
    private String whatsappID;
    private ContactStatus status;

    public boolean isValid() {
        return status == ContactStatus.VALID;
    }

}
