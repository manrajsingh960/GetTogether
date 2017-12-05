package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 12/5/17.
 */

public class UnJoinEventRequest extends StringRequest {
    private static final String UNJOIN_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/UnJoinEvent.php";
    private Map<String, String> params;

    public UnJoinEventRequest(String joinUser, int eventId, Response.Listener<String> listener) {
        super(Request.Method.POST, UNJOIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("joinUser", joinUser);
        params.put("eventId", eventId + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
