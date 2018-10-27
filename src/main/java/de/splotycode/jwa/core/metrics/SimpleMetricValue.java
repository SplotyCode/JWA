package de.splotycode.jwa.core.metrics;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class SimpleMetricValue extends AbstractMetricValue {

    private long value;
    private String node;

    public SimpleMetricValue(String name, String type, String help, long value, String node) {
        super(name, type, help);
        this.value = value;
        this.node = node;
    }

}
