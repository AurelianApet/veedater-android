package digimantra.veedaters.FCM;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 8/1/18.
 */

public class TokenServices extends IntentService {
    public TokenServices()
    {
        super("TokenServices");
    }
    public TokenServices(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent","onHandleIntent");
      String token=  FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(token);
    }
    public  void storeRegIdInPref(String token)
    {
        Log.e("AfterLogout",token+"");
        PreferenceConnector connector=PreferenceConnector.getInstance(getApplicationContext());
        connector.writeString(KeyValue.DEVICE_ID,token);
    }
}
