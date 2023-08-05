package digimantra.veedaters;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.PasscodeActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class Splash extends AppCompatActivity
{
    public static final int TIMEOUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOption);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceConnector.getInstance(Splash.this).readBoolean(KeyValue.IS_LOGIN))
                {
                    Intent intent=new Intent(Splash.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(Splash.this, LoginDashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        },TIMEOUT);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
