package de.splotycode.jwa.core.metrics;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CoreAppParameter {

    private String name;
    private int number;

    private String fullName;

    public CoreAppParameter(String rawValue) {
        fullName = rawValue;
        String[] split = rawValue.split(":");
        name = split[0];
        number = Integer.valueOf(split[1]);
    }
}
