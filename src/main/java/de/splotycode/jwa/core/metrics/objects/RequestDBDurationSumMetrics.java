package de.splotycode.jwa.core.metrics.objects;

import de.splotycode.jwa.core.metrics.MultiMetricValue;
import lombok.Getter;

import java.util.Map;

public class RequestDBDurationSumMetrics extends AbstractMultiMetrics {

    public static final String PARAMETER_RESULT = "result",
                               PARAMETER_METHOD = "method",
                               PARAMETER_QUERY = "query";

    @Getter private Map<String, MultiMetricValue.SubValue> methodValueMap;

    public RequestDBDurationSumMetrics(MultiMetricValue value) {
        super(value);
        methodValueMap = value.generadeParameterSubValueMap(PARAMETER_METHOD);
    }

    public long getValueByMethod(String method) {
        return methodValueMap.get(method).getValue();
    }

    public String getResultByMethod(String method) {
        return methodValueMap.get(method).getParameterAsString(PARAMETER_RESULT);
    }

    public String getQueryByMethod(String method) {
        return methodValueMap.get(method).getParameterAsString(PARAMETER_QUERY);
    }

    public String getNodeByMethod(String method) {
        return methodValueMap.get(method).getNode();
    }

}
