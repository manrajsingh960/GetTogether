package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 12/5/17.
 */

public class DeleteEventRequest extends StringRequest {
    private static final String DELETE_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/DeleteEvent.php";
    private Map<String, String> params;

    public DeleteEventRequest(int eventId, Response.Listener<String> listener) {
        super(Request.Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("event_id", eventId + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
