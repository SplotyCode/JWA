package de.splotycode.jwa.core.metrics;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public abstract class AbstractMetricValue {

    protected String name, type, help;

}
