package com.Lieyang.Chef.Firebase;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.Lieyang.Chef.Interfaces.NetworkResponseListener;
import com.Lieyang.Chef.Network.NetworkManager;
import com.Lieyang.Chef.Network.RequestType;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements NetworkResponseListener {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("FIREBASE_TOKEN", refreshedToken).apply();

        NetworkManager.getInstance().updateFirebaseToken("5c967e60d2e79f4afc43fdf1", refreshedToken);
    }

    @Override
    public void OnNetworkResponseReceived(RequestType REQUEST_TYPE, Object result) {

    }
}