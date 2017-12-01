package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 11/30/17.
 */

public class CheckEventExistenceRequest extends StringRequest {
    private static final String CHECK_EVENT_EXISTENCE_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/CheckEventExistence.php";
    private Map<String, String> params;

    public CheckEventExistenceRequest(int id, Response.Listener<String> listener) {
        super(Request.Method.POST, CHECK_EVENT_EXISTENCE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
