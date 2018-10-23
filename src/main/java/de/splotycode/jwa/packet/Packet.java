package de.splotycode.jwa.packet;

/**
 * Base Packet
 */
public interface Packet {

    /**
     * Does you need to be authenticated to send this packet
     * @return true if you need to be authenticated or else false
     */
    boolean authNeeded();

    /**
     * Writes all necessary headers and json variables to a connection
     * @param connection the connection you want to write on
     */
    void write(PacketConnection connection);

    /**
     * Witch Http Type you want to use for this packet
     * Example: POST
     * @return the PacketMethod you want to use for this packet
     */
    default PacketMethod getMethod() {
        return PacketMethod.POST;
    }

    /**
     * The Path of this Packet
     * Example: /v1/users/login
     * @return the path of this packet
     */
    String getPath();

}
