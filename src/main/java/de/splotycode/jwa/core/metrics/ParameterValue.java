package de.splotycode.jwa.core.metrics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParameterValue {

    protected String rawValue;

    @Override
    public String toString() {
        return rawValue;
    }
}
