package de.splotycode.jwa.core.metrics.objects;

import de.splotycode.jwa.core.metrics.MultiMetricValue;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

public class RequestsMetrics extends AbstractMultiMetrics {

    public enum RequestState {

        READ,
        IDLE,
        HANDLE_REQUEST

    }

    @Getter private Map<String, Long> stateValueMap;

    public RequestsMetrics(MultiMetricValue value) {
        super(value);
        stateValueMap = value.generadeParameterValueMap("state");
    }

    public long getRequestCount(String state) {
        return stateValueMap.get(state);
    }

    public long getRequestCount(RequestState state) {
        return getRequestCount(state.name().toLowerCase().replace('_', '-'));
    }

    public Set<String> getStates() {
        return stateValueMap.keySet();
    }

}
