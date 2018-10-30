package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CreateGroupInviteResponse implements Response {

    private String url;

    @Override
    public Packet getPacket() {
        throw new IllegalStateException("Can not create this packet type");
    }

    @Override
    public void read(ResponseContext context) {
        url = context.getObject().getJSONArray("groups").getJSONObject(0).getString("link");
    }
}
