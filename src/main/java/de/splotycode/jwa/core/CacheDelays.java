package de.splotycode.jwa.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class CacheDelays {

    @Getter private static final CacheDelays instance = new CacheDelays();

    private long groupSubject = 1000 * 15;
    private long groupCreationTime = 1000 * 15;
    private long groupCreator = 1000 * 15;
    private long groupAdmins = 1000 * 15;
    private long groupParticipants = 1000 * 15;
    private long groupInviteiUrl = 1000 * 15;


}
