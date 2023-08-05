package digimantra.veedaters.LoginSection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.R;
import io.blackbox_vision.wheelview.view.DatePickerPopUpWindow;

public class CompleteProfileActivity extends AppCompatActivity implements DatePickerPopUpWindow.OnDateSelectedListener {

    @BindView(R.id.clickDob)
    LinearLayout clickDob;
    @BindView(R.id.dob)
    EditText dob;
    @BindView(R.id.gender)
    EditText gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.gender)
    public void chooseGender()
    {
        Intent intent=new Intent(this,ChooseGenderActivity.class);
        startActivityForResult(intent,1001);
        //overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
    }
    @OnClick(R.id.dob)
    public void setDob()
    {
        final DatePickerPopUpWindow datePicker = new DatePickerPopUpWindow.Builder(this)
                .setMinYear(1980)
                .setMaxYear(2550)
                .setSelectedDate("2013-11-11")
                .setOnDateSelectedListener(this)
                .setConfirmButtonText("CONFIRM")
                .setCancelButtonText("CANCEL")
                .setConfirmButtonTextColor(Color.parseColor("#f31975"))
                .setCancelButtonTextColor(Color.parseColor("#f31975"))
                .setButtonTextSize(16)
                .setViewTextSize(16)
                .setShowDayMonthYear(true)
                .build();
        datePicker.show(this);
    }

    @Override
    public void onDateSelected(int i, int i1, int i2) {
        dob.setText(i+"-"+i1+"-"+i2);
    }
}
