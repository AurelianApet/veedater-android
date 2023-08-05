package digimantra.veedaters.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.NetworkUtil;

/**
 * Created by dmlabs on 16/1/18.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        CommonUtility.inFoToast(context,status+"");
    }
}
