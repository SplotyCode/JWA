package de.splotycode.jwa.core.metrics.objects;

import de.splotycode.jwa.core.metrics.MultiMetricValue;
import lombok.Getter;

import java.util.Map;

public class RequestDurationMetrics extends AbstractMultiMetrics {

    public static final String PARAMETER_RESULT = "result",
                               PARAMETER_METHOD = "method";

    @Getter private Map<String, MultiMetricValue.SubValue> methodValueMap;

    public RequestDurationMetrics(MultiMetricValue value) {
        super(value);
        methodValueMap = value.generadeParameterSubValueMap(PARAMETER_METHOD);
    }

    public long getValueByMethod(String method) {
        return methodValueMap.get(method).getValue();
    }

    public int getResultByMethod(String method) {
        return Integer.parseInt(methodValueMap.get(method).getParameterAsString(PARAMETER_RESULT));
    }

    public String getNodeByMethod(String method) {
        return methodValueMap.get(method).getNode();
    }

}
