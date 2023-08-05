package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.CustomViews.SeekbarWithIntervals;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.LoginSection.ChooseGenderActivity;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.PrefData;
import digimantra.veedaters.Model.PreferenceModel;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 8/1/18.
 */

public class PreferencesFragment extends Fragment {
    @BindView(R.id.genderRoot)
    LinearLayout genderRoot;
    @BindView(R.id.religion)
    LinearLayout religion;
    @BindView(R.id.sports)
    LinearLayout sports;
    @BindView(R.id.income)
    LinearLayout income;
    @BindView(R.id.style)
    LinearLayout style;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.religionTxt)
    TextView religionTxt;
    @BindView(R.id.sportTxt)
    TextView sportTxt;
    @BindView(R.id.incomeTxt)
    TextView incomeTxt;
    @BindView(R.id.styleTxt)
    TextView styleTxt;
    @BindView(R.id.distanceTxt)
    TextView distanceTxt;
    @BindView(R.id.ageMax)
    TextView ageMax;
    PrefData prefData;
    public static final int RELIGION=2;
    public static final int SPORT=3;
    public static final int INCOME=4;
    public static final int STYLE=5;
    public static final int GENDER=1;
    private int pos;
    ArrayList<PrefData> arrayList=new ArrayList<>();
    String fashionArray[] = {

            "Trendy",
            "Casual",
            "Exotic",
            "Vibrant",
            "Sexy",
            "Preppy",
            "Elegant",
            "Bohemian",
            "Punk",
            "Artsy",
            "Gothic",
            "Rocker",
            "50s",
            "70s",
            "Sporty",
            "Others",
    };
    String sportsArray[] = {

            "Archery",
            "Badminton",
            "Basketball",
            "Softball",
            "Beach Volleyball",
            "Boxing",
            "Cycling",
            "Diving",
            "Golf",
            "Baseball",
            "Gymnastics",
            "Ludo",
            "Karate",
            "Soccer",
            "Swimming",
            "Surfing",
            "Table Tennis",
            "Tennis",
            "Taekwondo",
            "Wrestling",
            "Cricket",
            "Others"
    };
    String incomeArray [] = {

            "$3000 - $5000",
            "$5000 - $10000",
            "$10000 - $20000",
            "Above $20000"
    };
    String religionArray [] = {

            "Christians",
            "Muslims",
            "Irreligious and atheist",
            "Hindus",
            "Buddhists",
            "Taoists",
            "Confucianists",
            "Ethnic and indigenous",
            "Sikhism",
            "Judaism",
            "Spiritism",
            "Bahá'ís",
            "Jainism",
            "Shinto",
            "Cao Dai",
            "Tenrikyo",
            "Neo-Paganism",
            "Unitarian Universalism",
            "Rastafari",
            "Others",
    };
    String showMeGenderArray [] = {

            "Men",
            "Women",
            "Not specified",
            "All"
    };
    @BindView(R.id.alchohalSwitch)
    SwitchCompat alchohalSwitch;
    @BindView(R.id.smokeSwitch)
    SwitchCompat smokeSwitch;
    @BindView(R.id.tattoSwitch)
    SwitchCompat tattoSwitch;
    @BindView(R.id.ageSeekbar)
    CrystalRangeSeekbar ageSeekbar;

    DashboardInteractor interactor;
    String age="18-50",distance="";
    String minAge="0",maxAge="50";
    String smoke="no",tatto="no",alchohal="no";
    SeekbarWithIntervals seekBar;
    String distanceArr[]={"0","25","50","100","200","500","500+"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.preference_layout,container,false);
        ButterKnife.bind(this,view);
        seekBar=(SeekbarWithIntervals)view.findViewById(R.id.seekBar);

        List<String> seekbarIntervals = getIntervals();
        getSeekbarWithIntervals().setIntervals(seekbarIntervals);
        interactor.setTitle("Filter");
        ((Dashboard)getActivity()).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPreferences();
            }
        });

        getPreferences();
        tattoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    tatto="yes";
                }else {
                    tatto="no";
                }
            }
        });
        smokeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    smoke="yes";
                }else {
                    smoke="no";
                }
            }
        });
        alchohalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    alchohal="yes";
                }else {
                    alchohal="no";
                }
            }
        });
        ageSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
               String userage=String.valueOf(minValue)+"-"+String.valueOf(maxValue);
                ageMax.setText(userage);
                age =String.valueOf(minValue)+"-"+String.valueOf(maxValue);
                minAge=String.valueOf(minValue);
                maxAge=String.valueOf(maxValue);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distance=distanceArr[i];
                distanceTxt.setText(distance);
                if (distance.equalsIgnoreCase("500+"))
                {
                    distance="";
                    distanceTxt.setText("1000");
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
        return view;
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
            seekBar = (SeekbarWithIntervals)getActivity(). findViewById(R.id.seekBar);
        }

        return seekBar;
    }
    private void getPreferences()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().getPrefernce(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID))).enqueue(new Callback<PreferenceModel>() {
            @Override
            public void onResponse(Call<PreferenceModel> call, Response<PreferenceModel> response) {
                CommonUtility.hideProgress();
                PreferenceModel model=response.body();
                if (model!=null && model.getUserPrefer()!=null)
                {
                    setUserData(model.getUserPrefer());
                }
            }

            @Override
            public void onFailure(Call<PreferenceModel> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
    private void setUserData(PreferenceModel.UserPrefer data)
    {
     if (data.getGender()!=null)
     {
         if (data.getGender().equalsIgnoreCase(""))
         {
             gender.setText("All");
         }else {
             gender.setText(data.getGender());
         }

     }
        if (data.getMinAge()!=null && !data.getMinAge().equalsIgnoreCase(""))
        {
            ageSeekbar.setMinStartValue(Float.parseFloat(data.getMinAge())).setMaxStartValue(Float.parseFloat(data.getMaxAge())).apply();
        }else {
            ageSeekbar.setMinStartValue(Float.parseFloat("0")).setMaxStartValue(Float.parseFloat("50")).apply();
        }
        if (data.getDistance()!=null && !data.getDistance().equalsIgnoreCase(""))
        {
            String distance=data.getDistance();
            if (distance.equalsIgnoreCase("500+"))
            {
                seekBar.setProgress(getIndex(1000));
            }else {
                seekBar.setProgress(getIndex(Integer.parseInt(distance)));
            }

        }else {
            seekBar.setProgress(getIndex(1000));
        }
        if (data.getReligion()!=null && !data.getReligion().equalsIgnoreCase(""))
        {
            religionTxt.setText(data.getReligion());
        }
        if (data.getSports()!=null && !data.getSports().equalsIgnoreCase(""))
        {
            sportTxt.setText(data.getSports());
        }
        if (data.getMinIncome()!=null && !data.getMinIncome().equalsIgnoreCase(""))
        {
            incomeTxt.setText("$"+data.getMinIncome()+" - "+"$"+data.getMaxIncome());
        }
        if (data.getStyle()!=null && !data.getStyle().equalsIgnoreCase(""))
        {
            styleTxt.setText(data.getStyle());
        }
        if (data.getAlchohol()!=null && data.getAlchohol().equalsIgnoreCase("yes"))
        {
            alchohalSwitch.setChecked(true);
        }else {
            alchohalSwitch.setChecked(false);
        }
        if (data.getSmoke()!=null && data.getSmoke().equalsIgnoreCase("yes"))
        {
            smokeSwitch.setChecked(true);
        }else {
            smokeSwitch.setChecked(false);
        }
        if (data.getTatoo()!=null && data.getTatoo().equalsIgnoreCase("yes"))
        {
            tattoSwitch.setChecked(true);
        }else {
            tattoSwitch.setChecked(false);
        }
    }
    private int getIndex(int value)
    {

        switch (value)
        {
            case 0:
                return 0;

            case 25:
                return 1;

            case 50:
                return 2;

            case 100:
                return 3;

            case 200:
                return 4;

            case 500:
                return 5;

            case 1000:
                return 6;

        }
        return 0;
    }
    private void setPreferences()
    {
        String price=incomeTxt.getText().toString().trim().replace(" ","");
        String minPrice="";
        String maxPrice="";
        if (price!=null && !price.equalsIgnoreCase(""))
        {
            String priceArr[]=price.split("-");
            minPrice=priceArr[0].replace("$","").trim();
            maxPrice=priceArr[1].replace("$","").trim();
        }
        HashMap<String,String> map=new HashMap<>();
        map.put("User[gender]",gender.getText().toString().equalsIgnoreCase("All")? " ":gender.getText().toString());
        map.put("User[min_age]",minAge);
        map.put("User[max_age]",maxAge);
        map.put("User[distance]",distance.equalsIgnoreCase("Worldwide")? "":distance);
        map.put("User[religion]",religionTxt.getText().toString());
        map.put("User[sports]",sportTxt.getText().toString());
        map.put("User[min_income]",minPrice);
        map.put("User[max_income]",maxPrice);
        map.put("User[style]",styleTxt.getText().toString());
        map.put("User[alchohol]",alchohal);
        map.put("User[smoke]",smoke);
        map.put("User[tatoo]",tatto);
        Log.e("DAtaaaaaaaaa",map.toString());
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().setPreference(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                CommonUtility.hideProgress();
                CommonUtility.successToast(getActivity(),"Saved Successfully");
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
    @OnClick(R.id.genderRoot)
    public void getGender()
    {
        getData(showMeGenderArray);
        Intent intent=new Intent(getActivity(), ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",arrayList);
        intent.putExtra("TYPE",GENDER);
        intent.putExtra("PREVIOUS",gender.getText().toString());
        startActivityForResult(intent,101);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    private ArrayList<PrefData> getData(String [] string)
    {
        arrayList.clear();
        for (int i = 0; i < string.length; i++) {
            arrayList.add(new PrefData(string[i],false));
        }
        if (prefData!=null&& arrayList.contains(prefData))
        {
            arrayList.get(arrayList.indexOf(prefData)).setSelected(true);
        }
     return arrayList;
    }
    @OnClick(R.id.religion)
    public void getReligion()
    {
        getData(religionArray);
        Intent intent=new Intent(getActivity(), ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",arrayList);
        intent.putExtra("TYPE",RELIGION);
        intent.putExtra("PREVIOUS",religionTxt.getText().toString());
        startActivityForResult(intent,102);
    }
    @OnClick(R.id.sports)
    public void getSport()
    {
        getData(sportsArray);
        Intent intent=new Intent(getActivity(), ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",arrayList);
        intent.putExtra("TYPE",SPORT);
        intent.putExtra("PREVIOUS",sportTxt.getText().toString());
        startActivityForResult(intent,103);
    }
    @OnClick(R.id.income)
    public void getIncome()
    {
        getData(incomeArray);
        Intent intent=new Intent(getActivity(), ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",arrayList);
        intent.putExtra("TYPE",INCOME);
        intent.putExtra("PREVIOUS",incomeTxt.getText().toString());
        startActivityForResult(intent,104);
    }
    @OnClick(R.id.style)
    public void getStyle()
    {
        getData(fashionArray);
        Intent intent=new Intent(getActivity(), ChooseGenderActivity.class);
        intent.putParcelableArrayListExtra("DATA",arrayList);
        intent.putExtra("TYPE",STYLE);
        intent.putExtra("PREVIOUS",styleTxt.getText().toString());
        startActivityForResult(intent,105);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 101:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                     pos=data.getIntExtra("POS",0);
                    prefData=data.getParcelableExtra("DATA");
                    if (result!=null)
                    {
                        gender.setText(result);
                    }
                }
                break;
            case 102:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    pos=data.getIntExtra("POS",0);
                    prefData=data.getParcelableExtra("DATA");
                    if (result!=null)
                    {
                        religionTxt.setText(result);
                    }
                }
                break;
            case 103:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    pos=data.getIntExtra("POS",0);
                    prefData=data.getParcelableExtra("DATA");
                    if (result!=null)
                    {
                        sportTxt.setText(result);
                    }
                }
                break;
            case 104:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    pos=data.getIntExtra("POS",0);
                    prefData=data.getParcelableExtra("DATA");
                    if (result!=null)
                    {
                        incomeTxt.setText(result);
                    }
                }
                break;
            case 105:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    prefData=data.getParcelableExtra("DATA");
                    if (result!=null)
                    {
                        styleTxt.setText(result);
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).edit.setText("Done");
    }

    @Override
    public void onPause() {
        super.onPause();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
        ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
        ((Dashboard)getActivity()).edit.setText("Edit");
        ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
    }
}
