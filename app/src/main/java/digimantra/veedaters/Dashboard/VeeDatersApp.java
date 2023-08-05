package digimantra.veedaters.Dashboard;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by dmlabs on 14/12/17.
 */

public class VeeDatersApp extends Application {
    public static  String latitude;
    private static boolean activityVisible;
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 1000;
    public static String getPhotoPath() {
        return photoPath;
    }

    public static String getMyName() {
        return myName;
    }

    public static void setMyName(String myName) {
        VeeDatersApp.myName = myName;
    }

    public static String getYourName() {
        return yourName;
    }

    public static void setYourName(String yourName) {
        VeeDatersApp.yourName = yourName;
    }

    public  static String myName;
    public  static String yourName;
    public static void setPhotoPath(String photoPath) {
        VeeDatersApp.photoPath = photoPath;
    }

    public static String photoPath;
    public static String senderPic;

    public static String getMyPic() {
        return myPic;
    }

    public static void setMyPic(String myPic) {
        VeeDatersApp.myPic = myPic;
    }

    public static String getSenderPic() {
        return senderPic;
    }

    public static void setSenderPic(String senderPic) {
        VeeDatersApp.senderPic = senderPic;
    }

    public static String myPic;

    public static int getLastClick() {
        return lastClick;
    }

    public static void setLastClick(int lastClick) {
        VeeDatersApp.lastClick = lastClick;
    }

    public static int lastClick;
    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(String longitude) {
        VeeDatersApp.longitude = longitude;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        VeeDatersApp.latitude = latitude;
    }

    public static  String longitude;
    @Override
    public void onCreate() {
        super.onCreate();
        JZVideoPlayer.WIFI_TIP_DIALOG_SHOWED = true;
    }
    public static boolean isActivityVisible() {
        return activityVisible;
    }
    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }
    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                VeeDatersApp.this.wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }
}
