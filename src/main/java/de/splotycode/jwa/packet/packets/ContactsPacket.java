package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactsPacket implements Packet {

    private String[] numbers;

    @Override
    public boolean authNeeded() {
        return true;
    }

    @Override
    public void write(PacketConnection connection) {
        connection.getObject().put("blocking", "wait");
        connection.getObject().put("contacts", numbers);
    }

    @Override
    public String getPath() {
        return "/v1/contacts";
    }
}
