package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 12/1/17.
 */

public class JoinEventRequest extends StringRequest {
    private static final String JOIN_EVENT_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/JoinEvent.php";
    private Map<String, String> params;

    public JoinEventRequest(String title, String description, int startHour, String startMin, int endHour,
                            String endMin, String startTimeValue, String endTimeValue, String creator,
                            String joinUser, Response.Listener<String> listener) {
        super(Request.Method.POST, JOIN_EVENT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("title", title);
        params.put("description", description);
        params.put("startHour", startHour + "");
        params.put("startMin", startMin);
        params.put("endHour", endHour + "");
        params.put("endMin", endMin);
        params.put("startTimeValue", startTimeValue);
        params.put("endTimeValue", endTimeValue);
        params.put("creator", creator);
        params.put("joinUser", joinUser);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


