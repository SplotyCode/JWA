package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.packets.CreateGroupPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.Getter;
import org.json.JSONObject;

@Getter
public class CreateGroupResponse implements Response {

    private long creationTime;
    private String id;

    @Override
    public Packet getPacket() {
        return new CreateGroupPacket();
    }

    @Override
    public void read(ResponseContext context) {
        JSONObject groupObj = context.getObject().getJSONArray("groups").getJSONObject(0);
        creationTime = groupObj.getLong("creation_time");
        id = groupObj.getString("id");
    }

}
