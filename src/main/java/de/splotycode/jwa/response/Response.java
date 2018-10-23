package de.splotycode.jwa.response;

import de.splotycode.jwa.packet.Packet;

public interface Response {

    Packet getPacket();
    void read(ResponseContext context);

}
