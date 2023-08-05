package digimantra.veedaters.Dashboard;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.MyOptionsPickerView;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import net.hockeyapp.android.CrashManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.CustomViews.SeekbarWithIntervals;
import digimantra.veedaters.LoginSection.ChooseGenderActivity;
import digimantra.veedaters.Model.PrefData;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class FilterActivity extends AppCompatActivity
{
    @BindView(R.id.backButton)
    ImageView backButton;
    @BindView(R.id.done)
    TextView done;
    @BindView(R.id.usergender)
    TextView usergender;
    @BindView(R.id.distanceMax)
    TextView distanceMax;
    @BindView(R.id.distanceMin)
    TextView distanceMin;
    @BindView(R.id.ageMax)
    TextView ageMax;
    @BindView(R.id.ageMin)
    TextView ageMin;
    @BindView(R.id.tick_mark_labels_rl)
    RelativeLayout tick_mark_labels_rl;
    @BindView(R.id.ageSeekbar)
    CrystalRangeSeekbar ageSeekbar;
    public static final int GENDER=1;
    ArrayList<PrefData> prefDatas=new ArrayList<>();
    SeekbarWithIntervals seekBar;
    String distance[]={"0","25","50","100","200","500","500+"};
   /* @BindView(R.id.age)
    TextView age;*/
  /*  @BindView(R.id.distance)
    TextView distance;*/
    String disArray[]={"0-10","20-100","100-250"};
    private String gender="",userage="0-50",userdistance="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
       /* View view=getWindow().getDecorView();
        int uiOption=View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOption);*/
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        seekBar=(SeekbarWithIntervals)findViewById(R.id.seekBar);
        seekBar.setProgress(6);
        List<String> seekbarIntervals = getIntervals();
        getSeekbarWithIntervals().setIntervals(seekbarIntervals);

      //  distanceSeekBar.setMinStartValue(Float.parseFloat("250")).apply();
      /*  distanceSeekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {

                if (value.intValue()>=250)
                {
                    distanceMax.setText("Worldwide");
                    userdistance="Worldwide";
                }else {
                    distanceMax.setText(String.valueOf(value)+" mi");
                    userdistance=String.valueOf(value);
                }
            }
        });*/
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                userdistance=distance[i];
                distanceMax.setText(userdistance);
                if (userdistance.equalsIgnoreCase("500+"))
                {
                    userdistance="";
                    distanceMax.setText("1000");
                }else {

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ageSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                //ageMin.setText(String.valueOf(minValue));
                ageMax.setText(String.valueOf(minValue)+" - "+String.valueOf(maxValue));
                userage=String.valueOf(minValue)+"-"+String.valueOf(maxValue);
            }
        });


    }
    private List<String> getIntervals() {
        return new ArrayList<String>() {{
            add("0");
            add("25");
            add("50");
            add("100");
            add("200");
            add("500");
            add("500+");
        }};
    }


    private SeekbarWithIntervals getSeekbarWithIntervals() {
        if (seekBar == null) {
            seekBar = (SeekbarWithIntervals) findViewById(R.id.seekBar);
        }
        return seekBar;
    }
    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        setData();
        finish();
    }
    @OnClick(R.id.done)
    public void onDoneClick()
    {
        setData();
        finish();
    }
    @OnClick(R.id.genderRoot)
    public void getGender()
    {
        prefDatas.clear();
        prefDatas.add(new PrefData("Men",false));
        prefDatas.add(new PrefData("Women",false));
        prefDatas.add(new PrefData("All",false));
        Intent intent=new Intent(this, ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",prefDatas);
        intent.putExtra("TYPE",GENDER);
        intent.putExtra("PREVIOUS",usergender.getText().toString());
        startActivityForResult(intent,GENDER);
    }

    @Override
    public void onBackPressed()
    {
        setData();
        finish();
        super.onBackPressed();
    }

    private void setData()
    {
        Intent intent=new Intent();
        intent.putExtra("GENDER",gender);
        intent.putExtra("DISTANCE",userdistance);
        intent.putExtra("AGE",userage);
        setResult(200,intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case GENDER:
                if (data!=null)
                {
                    gender=data.getStringExtra("RESULT");
                    if (gender!=null)
                    {
                        usergender.setText(gender);
                    }
                }
                break;
        }
    }

    /* @OnClick(R.id.all)
        public void allClick()
        {
            gender="All";
          *//*  all.setBackground(getResources().getDrawable(R.drawable.left_bg));
        all.setTextColor(getResources().getColor(R.color.white));
        female.setTextColor(getResources().getColor(R.color.black));
        male.setTextColor(getResources().getColor(R.color.black));
        female.setBackground(null);
        male.setBackground(null);*//*
    }*/
    @Override
    protected void onResume() {
        super.onResume();
        CrashManager.register(this,getResources().getString(R.string.HockeyAppID));
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(FilterActivity.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(FilterActivity.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(FilterActivity.this,PasscodeActivity.class);
                    startActivity(intent);
                }


            }
        }

        myApp.stopActivityTransitionTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((VeeDatersApp)this.getApplication()).startActivityTransitionTimer();
    }

    /*@OnClick(R.id.male)
        public void maleClick()
        {
            gender="Male";
          *//*  male.setBackground(getResources().getDrawable(R.drawable.mid_bg));
        male.setTextColor(getResources().getColor(R.color.white));
        all.setTextColor(getResources().getColor(R.color.black));
        female.setTextColor(getResources().getColor(R.color.black));
        female.setBackground(null);
        all.setBackground(null);*//*
    }
    @OnClick(R.id.female)
    public void femaleClick()
    {
        gender="Female";
       *//* female.setBackground(getResources().getDrawable(R.drawable.right_bg));
        female.setTextColor(getResources().getColor(R.color.white));
        all.setTextColor(getResources().getColor(R.color.black));
        male.setTextColor(getResources().getColor(R.color.black));
        all.setBackground(null);
        male.setBackground(null);*//*
    }*/
  //  @OnClick(R.id.age)
    public void setAge()
    {
        MyOptionsPickerView singlePicker = new MyOptionsPickerView(FilterActivity.this);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("18-25");
        items.add("25-30");
        items.add("30-35");
        singlePicker.setPicker(items);
        singlePicker.setCyclic(false);
        singlePicker.setSelectOptions(0);
        singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //  singleTVOptions.setText("Single Picker " + items.get(options1));
               // age.setText(items.get(options1));
                userage=items.get(options1);
            }
        });
        singlePicker.show();
    }
  //  @OnClick(R.id.distance)
    public void setDistance()
    {
        MyOptionsPickerView singlePicker = new MyOptionsPickerView(FilterActivity.this);
        final ArrayList<String> items = new ArrayList<String>();
        items.add("0-10 km");
        items.add("20 - 100 km");
        items.add("100 - 250 km");
        singlePicker.setPicker(items);
        singlePicker.setCyclic(false);
        singlePicker.setSelectOptions(0);
        singlePicker.setOnoptionsSelectListener(new MyOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //  singleTVOptions.setText("Single Picker " + items.get(options1));
               // distance.setText(items.get(options1));
                userdistance=disArray[options1];
            }
        });
        singlePicker.show();
    }
}
