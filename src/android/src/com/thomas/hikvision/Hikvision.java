package com.thomas.hikvision;

import android.content.Context;
import android.content.Intent;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Thomas.Wang on 2017/5/16.
 */

public class Hikvision extends CordovaPlugin{
    private Context context;
    private static final int HIKVISION_ACTIVIT_CODE = 0;
    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        context = cordova.getActivity();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("startMonitor".equals(action)){

            String array  = args.getString(0);
            Intent intent = new Intent();
            intent.setClass(context, HikvisionActivity.class);

            intent.putExtra("array",array);
            cordova.startActivityForResult( this,intent, HIKVISION_ACTIVIT_CODE);
        }else if("preview".equals(action)){

        }

        return true;
    }
}
