package cordovaPluginFacebookEventOS;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
/**
 * This class echoes a string called from JavaScript.
 */
public class FacebookEventOS extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("logSentFriendRequestEvent")){
            this.logSentFriendRequestEvent(args.getString(0),callbackContext);
            return true;
        }
        return false;
    }

    private void logSentFriendRequestEvent(String event,CallbackContext callbackContext){
        AppEventsLogger logger = AppEventsLogger.newLogger(cordova.getContext());
        logger.logEvent(event);
        callbackContext.success();
    }

}