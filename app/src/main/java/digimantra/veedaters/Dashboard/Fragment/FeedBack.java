package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.PasscodeActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 29/1/18.
 */

public class FeedBack extends AppCompatActivity
{
    @BindView(R.id.feedBackText)
    EditText feedBackText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.backButton)
    public ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }
    @OnClick(R.id.backButton)
    public void onBackClick()
    {
       finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    @OnClick(R.id.submitText)
    public void submitFeedback()
    {
        if (feedBackText.getText().toString().isEmpty())
        {
            feedBackText.setError("Enter yout feedback");
            return;
        }
        CommonUtility.showProgress(this,"Please wait...");
        ApiClient.getClient().feedBack(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID),feedBackText.getText().toString()).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                BlockResponse response1=response.body();
                if (response1!=null && response1.getIsSuccess())
                {
                    feedBackText.setText("");
                    CommonUtility.successToast(FeedBack.this,"Review added successfully!");
                }
                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((VeeDatersApp)this.getApplication()).startActivityTransitionTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(FeedBack.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(FeedBack.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(FeedBack.this,PasscodeActivity.class);
                    startActivity(intent);
                }


            }
        }
        myApp.stopActivityTransitionTimer();

    }
}
