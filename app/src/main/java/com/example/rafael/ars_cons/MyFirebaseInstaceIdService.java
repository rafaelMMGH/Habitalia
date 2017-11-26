package com.example.rafael.ars_cons;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rafael on 21/10/17.
 */

public class MyFirebaseInstaceIdService extends FirebaseInstanceIdService {
    public static final String TAG = "Noticias";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG,"token" + token);
    }
}
