package com.example.manrajsingh960.gettogether;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by manrajsingh960 on 11/15/17.
 */

public class CreateEventRequest extends StringRequest {
    private static final String CREATE_EVENT_REQUEST_URL = "https://gettogetherapp.000webhostapp.com/CreateEvent.php";
    private Map<String, String> params;

    public CreateEventRequest(String title, String description, int startHour, String startMin, int endHour,
                              String endMin, String startTimeValue, String endTimeValue, String creator,
                              String location,
                              Response.Listener<String> listener) {
        super(Request.Method.POST, CREATE_EVENT_REQUEST_URL, listener, null);
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
        params.put("location" , location);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
