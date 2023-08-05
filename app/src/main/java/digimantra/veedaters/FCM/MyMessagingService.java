package digimantra.veedaters.FCM;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.MessageActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 29/12/17.
 */

public class MyMessagingService extends FirebaseMessagingService
{

    String messageBody="";
    String photopath="";
    String senderId="";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage!=null)
        {
           // PreferenceConnector.getInstance(getApplicationContext()).writeBoolean(KeyValue.IS_LOGIN,false);
           /* Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"aa gye oye",Toast.LENGTH_SHORT).show();
                }
            });*/
            try {
                JSONObject jsonObject=new JSONObject(remoteMessage.getNotification().getTag().toString());
                messageBody=jsonObject.optString("message_body");
                photopath=jsonObject.optString("photo_path");
                senderId  =jsonObject.optString("sender_id");
              //  Log.e("ReceiverMessage",jsonObject.toString()+"");
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            //  Log.e("ReceiverMessage",remoteMessage.getData().toString()+"");

        }else {
        }
        if (VeeDatersApp.isActivityVisible())
        {
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", messageBody);
            pushNotification.putExtra("photopath", photopath);
            pushNotification.putExtra("senderId", senderId);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


        }else {
            Intent pushNotification = new Intent(getApplicationContext(),MessageActivity.class);
            pushNotification.putExtra("message", messageBody);
            pushNotification.putExtra("photopath", photopath);
            pushNotification.putExtra("senderId", senderId);
            new NotificationUtils(getApplicationContext()).showNotificationMessage("Veedaters",messageBody,getTimeStemp(),pushNotification,photopath);
            // Log.e("notOpen","noooooooo");

            //  new NotificationUtils(getApplicationContext()).showNotificationMessage();
        }
       // Toast.makeText(this, "recevei", Toast.LENGTH_SHORT).show();
     //   Toast.makeText(getApplicationContext(), "recevei", Toast.LENGTH_SHORT).show();
       /* NotificationUtils  utils=new NotificationUtils(getApplicationContext());
        Intent intent=new Intent(getApplicationContext(), Dashboard.class);
        utils.showNotificationMessage("Androdid","Check for notification","11:46",intent);*/
     /*   if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {

        }else {
            Intent pushNotification = new Intent(getApplicationContext(),MessageActivity.class);
            pushNotification.putExtra("message", messageBody);
            pushNotification.putExtra("photopath", photopath);
            pushNotification.putExtra("senderId", senderId);
            Log.e("Message",messageBody+"");
            new NotificationUtils(getApplicationContext()).showNotificationMessage("Veedaters",messageBody,getTimeStemp(),pushNotification);
        }*/
    }
    private String getTimeStemp()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

}
