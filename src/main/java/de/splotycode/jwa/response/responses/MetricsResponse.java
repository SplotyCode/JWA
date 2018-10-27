package de.splotycode.jwa.response.responses;

import de.splotycode.jwa.core.metrics.AbstractMetricValue;
import de.splotycode.jwa.core.metrics.MultiMetricValue;
import de.splotycode.jwa.core.metrics.SimpleMetricValue;
import de.splotycode.jwa.core.metrics.objects.*;
import de.splotycode.jwa.packet.Packet;
import de.splotycode.jwa.packet.packets.MetricsPacket;
import de.splotycode.jwa.response.Response;
import de.splotycode.jwa.response.ResponseContext;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MetricsResponse implements Response {

    @Getter private Map<String, AbstractMetricValue> values = new HashMap<>();

    @Override
    public Packet getPacket() {
        return new MetricsPacket();
    }

    @Override
    public void read(ResponseContext context) {
        JSONObject object = context.getObject().getJSONObject("metrics");
        for (String key : object.keySet()) {
            JSONObject keyObj = object.getJSONObject(key);

            String type = keyObj.getString("type");
            String help = keyObj.getString("help");

            JSONArray data = keyObj.getJSONArray("data");

            if (data.length() == 1) {
                JSONObject dataObj = data.getJSONObject(0);

                long value = dataObj.getLong("value");
                String node = dataObj.getJSONObject("labels").getString("node");

                values.put(key, new SimpleMetricValue(key, type, help, value, node));
            } else {
                MultiMetricValue value = new MultiMetricValue(key, type, help);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject dataObj = data.getJSONObject(i);

                    JSONObject labels = dataObj.getJSONObject("labels");
                    Map<String, String> parameters = new HashMap<>();

                    for (String labelKey : labels.keySet()) {
                        parameters.put(labelKey, labels.getString(labelKey));
                    }

                    value.getValues().add(new MultiMetricValue.SubValue(parameters, dataObj.getLong("value"), parameters.get("node")));
                }
            }
        }
    }

    /*
     * --------------------------------------------------------------
     *  General Information Functions
     * --------------------------------------------------------------
     */
    
    public AbstractMetricValue getValue(String name) {
        return values.get(name);
    }

    public SimpleMetricValue getSimpleValue(String name) {
        return (SimpleMetricValue) values.get(name);
    }

    public MultiMetricValue getMultiValue(String name) {
        return (MultiMetricValue) values.get(name);
    }

    /*
     * --------------------------------------------------------------
     *  Simple Information Functions
     * --------------------------------------------------------------
     */

    public long getUptime() {
        return getUptimeValue().getValue();
    }

    public SimpleMetricValue getUptimeValue() {
        return getSimpleValue("web_uptime");
    }

    public long getTotalConnections() {
        return getTotalConnectionsValue().getValue();
    }

    public SimpleMetricValue getTotalConnectionsValue() {
        return getSimpleValue("web_connections");
    }

    public long getPendingConnections() {
        return getPendingConnectionsValue().getValue();
    }

    public SimpleMetricValue getPendingConnectionsValue() {
        return getSimpleValue("web_pending_connections");
    }

    public long getMaxPendingConnections() {
        return getMaxPendingConnectionsValue().getValue();
    }

    public SimpleMetricValue getMaxPendingConnectionsValue() {
        return getSimpleValue("web_max_pending_connections");
    }

    public long getQueueLenghth() {
        return getQueueLengthValue().getValue();
    }

    public SimpleMetricValue getQueueLengthValue() {
        return getSimpleValue("web_queue_len");
    }

    public long getIdleProcesses() {
        return getIdleProcessesValue().getValue();
    }

    public SimpleMetricValue getIdleProcessesValue() {
        return getSimpleValue("web_idle_processes");
    }

    public long getTotalProcesses() {
        return getTotalProcessesValue().getValue();
    }

    public SimpleMetricValue getTotalProcessesValue() {
        return getSimpleValue("web_total_processes");
    }

    public long getProcessesLimitHit() {
        return getProcessesLimitHitValue().getValue();
    }

    public SimpleMetricValue getProcessesLimitHitValue() {
        return getSimpleValue("web_process_limit_hit");
    }

    public long getHandeldRequests() {
        return getHandeldRequestsValue().getValue();
    }

    public SimpleMetricValue getHandeldRequestsValue() {
        return getSimpleValue("web_handled_requests");
    }

    public long getKBSend() {
        return getKBSendValue().getValue();
    }

    public SimpleMetricValue getKBSendValue() {
        return getSimpleValue("web_out_kbytes");
    }


    /*
     * --------------------------------------------------------------
     *  Complex Information Functions
     * --------------------------------------------------------------
     */

    public RequestsMetrics getRequests() {
        return new RequestsMetrics(getMultiValue("web_requests"));
    }

    public RequestDBDurationCountMetrics getRequestsDBDurationCount() {
        return new RequestDBDurationCountMetrics(getMultiValue("api_requests_db_duration_ms_count"));
    }

    public RequestDBDurationSumMetrics getRequestsDBDurationSum() {
        return new RequestDBDurationSumMetrics(getMultiValue("api_requests_db_duration_ms_sum"));
    }

    public RequestDurationCountMetrics getRequestsDurationCount() {
        return new RequestDurationCountMetrics(getMultiValue("api_requests_duration_ms_count"));
    }

    public RequestDurationSumMetrics getRequestsDurationSum() {
        return new RequestDurationSumMetrics(getMultiValue("api_requests_duration_ms_sum"));
    }

    public RequestCoreAppDurationMetrics getRequestCoreAppDurationSum() {
        return new RequestCoreAppDurationMetrics(getMultiValue("api_requests_coreapp_duration_ms_sum"));
    }

    public RequestCoreAppDurationMetrics getRequestCoreAppDurationCount() {
        return new RequestCoreAppDurationMetrics(getMultiValue("api_requests_coreapp_duration_ms_count"));
    }


}
