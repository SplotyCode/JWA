package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateGroupPacket implements Packet {

    private String subject;

    @Override
    public boolean authNeeded() {
        return true;
    }

    @Override
    public void write(PacketConnection connection) {
        connection.addJson("subject", subject);
    }

    @Override
    public String getPath() {
        return "/v1/groups";
    }

}
