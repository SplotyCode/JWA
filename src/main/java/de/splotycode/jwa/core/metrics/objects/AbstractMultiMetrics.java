package de.splotycode.jwa.core.metrics.objects;

import de.splotycode.jwa.core.metrics.MultiMetricValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class AbstractMultiMetrics {

    protected MultiMetricValue value;

}
