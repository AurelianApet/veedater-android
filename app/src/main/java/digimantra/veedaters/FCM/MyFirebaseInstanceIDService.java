package digimantra.veedaters.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 29/12/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("DEVICETOKEN",refreshedToken+"");
        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
    }
    public  void storeRegIdInPref(String token)
    {
        PreferenceConnector connector=PreferenceConnector.getInstance(getApplicationContext());
        connector.writeString(KeyValue.DEVICE_ID,token);
    }
}
