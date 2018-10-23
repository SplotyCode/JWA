package de.splotycode.jwa.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Whatsapp Errors
 * Sometimes whatsapp sends errors in a Json Response this Object represents one of this Errors
 * https://developers.facebook.com/docs/whatsapp/api/users/login
 */

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Error {

    private int code;
    private String title, details;

}
