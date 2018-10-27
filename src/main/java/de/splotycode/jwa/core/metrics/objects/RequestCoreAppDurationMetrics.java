package de.splotycode.jwa.core.metrics.objects;

import de.splotycode.jwa.core.metrics.CoreAppParameter;
import de.splotycode.jwa.core.metrics.MultiMetricValue;

import java.util.Map;

public class RequestCoreAppDurationMetrics extends AbstractMultiMetrics {

    public static final String PARAMETER_COREAPP = "coreapp",
                               PARAMETER_RESULT = "result",
                               PARAMETER_METHOD = "method";

    private Map<String, MultiMetricValue.SubValue> methodValueMap;

    public RequestCoreAppDurationMetrics(MultiMetricValue value) {
        super(value);
        methodValueMap = value.generadeParameterSubValueMap(PARAMETER_METHOD);
    }

    public long getValueByMethod(String method) {
        return methodValueMap.get(method).getValue();
    }

    public String getResultByMethod(String method) {
        return methodValueMap.get(method).getParameterAsString(PARAMETER_RESULT);
    }

    public CoreAppParameter getCoreAppByMethod(String method) {
        return methodValueMap.get(method).getCoreAppParameter(PARAMETER_COREAPP);
    }

    public String getNodeByMethod(String method) {
        return methodValueMap.get(method).getNode();
    }

}
