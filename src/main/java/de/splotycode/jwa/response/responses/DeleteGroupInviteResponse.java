package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.response.BadResponseException;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class DeleteGroupInviteResponse implements Response {

    @Override
    public Packet getPacket() {
        throw new IllegalStateException("Can not create this packet type");
    }

    @Override
    public void read(ResponseContext context) {
        if (context.getErrorCode() != 200)
            throw new BadResponseException("Could not delete group invite: " + context.getErrorMessage() + "(" + context.getErrorCode() + ")");
    }
}
