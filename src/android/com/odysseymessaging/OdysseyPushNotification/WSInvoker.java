package com.odysseymessaging.OdysseyPushNotification;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import org.apache.commons.codec.binary.Base64;

public class WSInvoker extends CordovaPlugin {
	
	  
	  private static final String URL_BASE = "http://push.od-msg.net/PushNotifWS.svc/";
	  private enum Method {GET, POST}
	
	  public static final String ACTION_SET_AUTH_INFOS = "setAuthInfos";
//	  public static final String ACTION_POST_REGISTER = "postRegister";
//	  public static final String ACTION_GET_SUBSCRIPTIONS = "getSubscriptions";
	  public static final String ACTION_GET_TEST = "getTest";
	  
	  public static String encodedAuthorization;
	  

	  @Override
	  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		  
		    if (ACTION_SET_AUTH_INFOS.equals(action)) {
		        return setAuthInfos(args, callbackContext); 
		      }
		    else if (ACTION_GET_TEST.equals(action)) {
			      return getTest(callbackContext);
			    }
//		    else if (ACTION_POST_REGISTER.equals(action)) {
//			      return postRegister(args);
//			    }
//		    else if (ACTION_GET_SUBSCRIPTIONS.equals(action)) {
//		      return getSubscriptions();
//		    }
		    return false;
	  }
	  
	  private boolean getTest(CallbackContext callbackContext) {
		  callbackContext.success(callRestWS("Test", Method.GET , ""));
		return true;
	}


	private static boolean setAuthInfos(JSONArray args, CallbackContext callbackContext) {
		    try {
		    	final JSONObject argObject = args.getJSONObject(0);
      
			    String pair = argObject.getString("user") + ":" + argObject.getString("password");
				final byte[] encodedBytes = Base64.encodeBase64(pair.getBytes());
				encodedAuthorization = new String(encodedBytes);
		      		      
				callbackContext.success("Authentification informations were successfully encoded");
				return true;
		      
		    } catch (Exception e) {
		    	callbackContext.error("[setAuthInfos] : Exception: " + e.getMessage());
		    }
		    return false;
		  }

	public static String callRestWS(String requestUrl, Method method, String json) {
		 
        String response = null;
        try {
                URL url = new URL(URL_BASE + requestUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod(method.toString()); // Set method verb
                
                conn.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
                conn.setRequestProperty("Content-Type", "application/json");

                if (method == Method.POST)
                {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(json);  // set inputs (JSON string)
                out.close();
                }

                conn.disconnect();
                conn.setDoInput(true);

                InputStream is = conn.getInputStream();
                Scanner isScanner = new Scanner(is);

                StringBuffer buf = new StringBuffer();
                while(isScanner.hasNextLine()) {	buf.append(isScanner.nextLine() +"\n");		}
                response = buf.toString();
                return response;

        } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
        }              
}

}
