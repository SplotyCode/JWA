package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.core.GatewayStatus;
import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.packets.HealthPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HealthResponse implements Response {

    private GatewayStatus gatewayStatus;

    @Override
    public Packet getPacket() {
        return new HealthPacket();
    }

    @Override
    public void read(ResponseContext context) {
        gatewayStatus = context.getObject().getJSONObject("health").
                getEnum(GatewayStatus.class, "gateway_status");
    }
}
