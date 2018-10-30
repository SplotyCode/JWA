package de.splotycode.jwa.core;

import de.splotycode.jwa.Connection;
import de.splotycode.jwa.packet.packets.CreateGroupInvitePacket;
import de.splotycode.jwa.packet.packets.DeleteGroupInvitePacket;
import de.splotycode.jwa.packet.packets.GetGroupPacket;
import de.splotycode.jwa.packet.packets.LeaveGroupPacket;
import de.splotycode.jwa.response.responses.CreateGroupInviteResponse;
import de.splotycode.jwa.response.responses.DeleteGroupInviteResponse;
import de.splotycode.jwa.response.responses.GetGroupResponse;
import de.splotycode.jwa.response.responses.LeaveGroupResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
public class Group implements ValueCacheListener.CacheListener {

    private final String groupId;
    private final Connection connection;

    private ValueCacheListener<String> subject = new ValueCacheListener<>(this, CacheDelays.getInstance().getGroupSubject());
    private ValueCacheListener<Long> creationTime = new ValueCacheListener<>(this, CacheDelays.getInstance().getGroupCreationTime());
    private ValueCacheListener<User> creator = new ValueCacheListener<>(this, CacheDelays.getInstance().getGroupCreator());

    private ValueCacheListener<List<User>> admins = new ValueCacheListener<>(this, CacheDelays.getInstance().getGroupAdmins());
    private ValueCacheListener<List<User>> participants = new ValueCacheListener<>(this, CacheDelays.getInstance().getGroupParticipants());

    private ValueCache<String> inviteURL = new ValueCache<>(this::updateLink, CacheDelays.getInstance().getGroupInviteUrl());

    public Group(String groupId, Connection connection) {
        this.groupId = groupId;
        this.connection = connection;
    }

    private String updateLink() {
        return connection.sendPacket(CreateGroupInviteResponse.class, new CreateGroupInvitePacket(groupId)).getUrl();
    }

    @Override
    public void refresh() {
        GetGroupPacket packet = new GetGroupPacket(groupId);
        GetGroupResponse response = connection.sendPacket(GetGroupResponse.class, packet);
        subject.setValue(response.getSubject());
        creationTime.setValue(response.getCreationTime());
        creator.setValue(new User(response.getCreator(), connection));

        List<User> admins = new ArrayList<>();
        for (String admin : response.getAdmins())
            admins.add(new User(admin, connection));
        this.admins.setValue(admins);

        List<User> participants = new ArrayList<>();
        for (String participant : response.getAdmins())
            participants.add(new User(participant, connection));
        this.admins.setValue(participants);

    }

    public void leave() {
        connection.sendPacket(LeaveGroupResponse.class, new LeaveGroupPacket(groupId));
    }

    public void removeInviteLink() {
        connection.sendPacket(DeleteGroupInviteResponse.class, new DeleteGroupInvitePacket(groupId));
        inviteURL.resetValue();
    }

}
