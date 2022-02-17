package cordovaPluginFacebookEventOS;

import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Iterator;


/**
 * This class echoes a string called from JavaScript.
 */
public class FacebookEventOS extends CordovaPlugin {

    private static final String TAG = "FacebookEventOS ";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("logSentFriendRequestEvent")){
            this.logSentFriendRequestEvent(args.getString(0),callbackContext,args.getJSONObject(1));
            return true;
        }
        return false;
    }

    @Override
    protected void pluginInitialize() {
        FacebookSdk.sdkInitialize(cordova.getContext());
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        super.pluginInitialize();
    }

    private void logSentFriendRequestEvent(final String eventName,final CallbackContext callbackContext,final JSONObject params) throws FacebookException, JSONException {
        Log.d(TAG, "logEvent called. name: " + eventName);
        AppEventsLogger logger = AppEventsLogger.newLogger(cordova.getContext());
        Bundle bundelEvent = new Bundle();
        Iterator iterator = params.keys();

        while (iterator.hasNext()){
            String key = (String) iterator.next();
            Object value = params.get(key);

            if(value instanceof Integer || value instanceof Double){
                bundelEvent.putFloat(key, ((Number) value).floatValue());
            }else{
                bundelEvent.putString(key,value.toString());
            }
        }

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.logEvent(eventName,bundelEvent);
                    callbackContext.success();
                    Log.d(TAG,"logEvent success "+eventName);
                }catch (Exception e){
                    callbackContext.error(e.getMessage());
                    Log.e(TAG,"logEvent error "+eventName+" - "+e.getMessage());
                }
            }
        });
    }

}