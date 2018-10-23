package de.splotycode.jwa.response;

import de.splotycode.jwa.packet.Packet;

/**
 * The Response of an Packet
 */
public interface Response {

    /**
     * Create a new packet for this reposne type
     * @return the new created packet
     */
    Packet getPacket();

    /**
     * Reads the response from a ResponseContext
     * @param context the ResponseContext you want to read from
     */
    void read(ResponseContext context);

}
