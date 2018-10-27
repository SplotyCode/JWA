package de.splotycode.jwa.core.metrics;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MultiMetricValue extends AbstractMetricValue {

    @Getter private Set<SubValue> values = new HashSet<>();

    public MultiMetricValue(String name, String type, String help) {
        super(name, type, help);
    }


    public Map<String, Long> generadeParameterValueMap(String parameter) {
        HashMap<String, Long> map = new HashMap<>();
        for (SubValue subValue : values) {
            String name = subValue.getParameters().get(parameter);
            if (name == null) continue;
            map.put(name, subValue.value);
        }
        return map;
    }

    public Map<String, SubValue> generadeParameterSubValueMap(String parameter) {
        HashMap<String, SubValue> map = new HashMap<>();
        for (SubValue subValue : values) {
            String name = subValue.getParameters().get(parameter);
            if (name == null) continue;
            map.put(name, subValue);
        }
        return map;
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class SubValue {

        private Map<String, String> parameters;
        private long value;

        public String getParameterAsString(String name) {
            return parameters.get(name);
        }

        public CoreAppParameter getCoreAppParameter(String name) {
            return new CoreAppParameter(parameters.get(name));
        }

    }

}
