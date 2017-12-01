package com.example.manrajsingh960.gettogether;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by manrajsingh960 on 12/1/17.
 */

public class ToastMessage {
    private Context toastContext;

    public ToastMessage(Context c){
        this.toastContext = c;
    }

    public void makeMessage(String s){
        Toast.makeText(toastContext, s, Toast.LENGTH_SHORT).show();
    }
}
