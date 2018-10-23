package de.splotycode.jwa.response;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This represents an whatsapp response
 */
public class ResponseContext {

    @Getter private Set<Error> errors = new HashSet<>();
    @Getter private int errorCode;
    @Getter private String errorMessage;
    @Getter private Map<String, String> headers = new HashMap<>();
    @Getter private JSONObject object;

    public ResponseContext(InputStream stream) {
        try {
            parse(IOUtils.toString(stream, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses an raw response into this object
     * @param response the raw response
     */
    public void parse(String response) {
        String[] lines = response.split("\n");
        String[] firstLine = lines[0].split(" ");

        errorCode = Integer.valueOf(firstLine[1]);
        errorMessage = firstLine[2];

        boolean header = true;
        StringBuilder body = new StringBuilder();

        for (int i = 1; i < lines.length;i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                header = false;
                continue;
            }
            if (header) {
                String[] headerEntry = line.split(": ");
                headers.put(headerEntry[0], headerEntry[1]);
            } else {
                body.append(line).append("\n");
            }
        }
        object = new JSONObject(body);
        JSONArray errorArray = object.getJSONArray("errors");
        for (int i = 0; i < errorArray.length(); i++) {
            JSONObject errorObj = errorArray.getJSONObject(i);
            errors.add(new Error(errorObj.getInt("code"), errorObj.getString("title"), errorObj.getString("details")));
        }
    }



}
