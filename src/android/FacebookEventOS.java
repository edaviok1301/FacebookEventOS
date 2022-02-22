package cordovaPluginFacebookEventOS;

import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import java.util.Iterator;

/**
 * This class echoes a string called from JavaScript.
 */
public class FacebookEventOS extends CordovaPlugin {

    private static final String TAG = "FacebookEventOS ";
    AppEventsLogger logger;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("logSentEvent")){
            this.logSentEvent(args.getString(0),callbackContext,args.getJSONObject(1));
            return true;
        }
        return false;
    }


    private void logSentEvent(final String eventName,final CallbackContext callbackContext,final JSONObject params) throws FacebookException {
        Log.d(TAG, "logSentEvent called. name: " + eventName);
        logger = AppEventsLogger.newLogger(cordova.getContext());
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if(eventName == AppEventsConstants.EVENT_NAME_PURCHASED){
                        Bundle bundleEvent = iteratorParams(params);
                        Double purchase = Double.parseDouble(bundleEvent.getString("purchase"));
                        logger.logEvent(AppEventsConstants.EVENT_NAME_PURCHASED,purchase,iteratorParams(params));
                    }else {
                        logger.logEvent(eventName, iteratorParams(params));
                    }
                    callbackContext.success();
                    Log.d(TAG,"logEvent success "+eventName);
                }catch (Exception e){
                    callbackContext.error(e.getMessage());
                    Log.e(TAG,"logEvent error "+eventName+" - "+e.getMessage());
                }
            }
        });
    }

    private Bundle iteratorParams(final JSONObject params)throws JSONException{
        Bundle bundleEvent = new Bundle();
        Iterator iterator = params.keys();

        while (iterator.hasNext()){
            String key = (String) iterator.next();
            Object value = params.get(key);

            if(value instanceof Integer || value instanceof Double){
                bundleEvent.putFloat(key, ((Number) value).floatValue());
            }else{
                bundleEvent.putString(key,value.toString());
            }
        }

        return bundleEvent;
    }
}