package digimantra.veedaters.Dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Fragment.DashboardInteractor;
import digimantra.veedaters.Dashboard.Fragment.ManageAccount;
import digimantra.veedaters.R;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class ConfirmPasscode extends AppCompatActivity
{
    private  String currentPasscode;
    DashboardInteractor interactor;
    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;
    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<ImageView> dots;
    private static final String TRUE_CODE = "2869";
    private static final int MAX_LENGHT = 4;
    private String codeString = "";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.save)
    TextView save;
    @Nullable
    @BindView(R.id.backButton)
    public ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_passcode);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        currentPasscode=getIntent().getStringExtra("PASSCODE");
    }
    @OnClick(R.id.save)
    public void savePassCode()
    {
        CommonUtility.showProgress(ConfirmPasscode.this,"Please wait...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PreferenceConnector.getInstance(ConfirmPasscode.this).writeString(KeyValue.USERPASSCODE,codeString);
                PreferenceConnector.getInstance(ConfirmPasscode.this).writeBoolean(KeyValue.ISPASSCODESET,true);
                CommonUtility.successToast(ConfirmPasscode.this,"Passcode save successfully");
                CommonUtility.hideProgress();
                setResult(true);
                finish();

            }
        },3000);
    }
    @OnClick(R.id.btn_clear)
    public void onClear() {
        if (codeString.length() > 0) {
            //remove last character of code
            codeString = removeLastChar(codeString);
            //update dots layout
            setDotImagesState();
        }
    }
    private void setDotImagesState() {
        for (int i = 0; i < codeString.length(); i++) {
            dots.get(i).setImageResource(R.drawable.dot_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setImageResource(R.drawable.dot_disable);
            }
        }
    }
    private String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        if (s.length()==4)
        {
            save.setVisibility(View.GONE);
         //   ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
        }
        return s.substring(0, s.length() - 1);
    }
    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(Button button) {
        getStringCode(button.getId());
        if (codeString.length() == MAX_LENGHT) {
            if (codeString.equals(currentPasscode))
            {
                save.setVisibility(View.VISIBLE);
              //  ((Dashboard)getActivity()).edit.setText("Save");
               // ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
            }else {
                save.setVisibility(View.GONE);
              //  ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
                shakeAnimation();
            }
        } else if (codeString.length() > MAX_LENGHT){
            //reset the input code
            save.setVisibility(View.GONE);
            //((Dashboard)getActivity()).edit.setVisibility(View.GONE);
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
    }
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(ConfirmPasscode.this, R.anim.shake_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(ConfirmPasscode.this, "Passcode do not match", Toast.LENGTH_SHORT).show();
    }
    private void getStringCode(int buttonId) {
        switch (buttonId) {
            case R.id.btn0:
                codeString += "0";
                break;
            case R.id.btn1:
                codeString += "1";
                break;
            case R.id.btn2:
                codeString += "2";
                break;
            case R.id.btn3:
                codeString += "3";
                break;
            case R.id.btn4:
                codeString += "4";
                break;
            case R.id.btn5:
                codeString += "5";
                break;
            case R.id.btn6:
                codeString += "6";
                break;
            case R.id.btn7:
                codeString += "7";
                break;
            case R.id.btn8:
                codeString += "8";
                break;
            case R.id.btn9:
                codeString += "9";
                break;
            default:
                break;
        }
    }
    private void setResult(boolean flag)
    {
        Intent intent=new Intent();
        intent.putExtra("ISTRUE",flag);
        setResult(201,intent);
    }

    @Override
    public void onBackPressed() {
        setResult(false);
        super.onBackPressed();

        finish();
    }
    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        setResult(false);
        super.onBackPressed();
        finish();
    }
}
