package de.splotycode.jwa.packet.packets;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.PacketConnection;
import lombok.*;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginPacket implements Packet {

    private String username, password;

    @Override
    public boolean authNeeded() {
        return false;
    }

    @Override
    public void write(PacketConnection connection) {
        connection.addHeader("Authorization", new String(Base64.getDecoder().decode(username + ":" + password)));
    }

    @Override
    public String getPath() {
        return "/v1/users/login";
    }
}
