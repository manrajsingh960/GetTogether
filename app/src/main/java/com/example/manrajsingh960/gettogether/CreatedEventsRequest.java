package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 11/20/17.
 */

public class CreatedEventsRequest extends StringRequest {
    private static final String CREATED_EVENTS_REQUEST_URL = "https://manrajsingh960.000webhostapp.com/MyCreatedEvents.php";
    private Map<String, String> params;

    public CreatedEventsRequest(String creator, Response.Listener<String> listener) {
        super(Request.Method.POST, CREATED_EVENTS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", creator);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
