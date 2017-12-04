package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 12/2/17.
 */

public class SaveJoinedEventRequest extends StringRequest {
    private static final String SAVE_JOINED_EVENT_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/SaveJoinEvent.php";
    private Map<String, String> params;

    public SaveJoinedEventRequest(String joinUser, Response.Listener<String> listener) {
        super(Request.Method.POST, SAVE_JOINED_EVENT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", joinUser);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
