package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.packets.LoginPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@EqualsAndHashCode
public class LoginResponse implements Response {

    private String token, expiresAfter;

    @Override
    public Packet getPacket() {
        return new LoginPacket();
    }

    @Override
    public void read(ResponseContext context) {
        JSONObject rawObject = context.getObject().getJSONObject("users");
        token = rawObject.getString("token");
        expiresAfter = rawObject.getString("expires_after");
    }

}
