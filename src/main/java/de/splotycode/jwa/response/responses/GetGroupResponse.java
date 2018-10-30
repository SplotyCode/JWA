package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class GetGroupResponse implements Response {

    private List<String> admins = new ArrayList<>();
    private List<String> participants = new ArrayList<>();
    private long creationTime;
    private String creator, subject;

    @Override
    public Packet getPacket() {
        throw new IllegalStateException("Can not create this packet type");
    }

    @Override
    public void read(ResponseContext context) {
        JSONObject obj = context.getObject().getJSONArray("groups").getJSONObject(0);
        JSONArray adminArray = obj.getJSONArray("admins");
        JSONArray participantsArray = obj.getJSONArray("participants");

        for (int i = 0; i < adminArray.length(); i++)
            admins.add(adminArray.getString(i));
        for (int i = 0; i < participantsArray.length(); i++)
            participants.add(participantsArray.getString(i));

        creationTime = obj.getLong("creation_time");
        creator = obj.getString("creator");
        subject = obj.getString("subject");

    }

}
