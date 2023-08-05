package digimantra.veedaters.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.Fragment.DashboardInteractor;
import digimantra.veedaters.Dashboard.Fragment.ManageAccount;
import digimantra.veedaters.R;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 3/2/18.
 */

public class ConfirmPassCodeFragment extends Fragment
{
    private static String currentPasscode;
    DashboardInteractor interactor;
    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;
    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<ImageView> dots;
    private static final String TRUE_CODE = "2869";
    private static final int MAX_LENGHT = 4;
    private String codeString = "";
    public static ConfirmPassCodeFragment getInstance(String passcode)
    {
        currentPasscode=passcode;
        ConfirmPassCodeFragment fragment=new ConfirmPassCodeFragment();
        return fragment;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_confirm_passcode,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Confirm Passcode");
        ((Dashboard)getActivity()).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtility.showProgress(getActivity(),"Please wait...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USERPASSCODE,codeString);
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISPASSCODESET,true);
                        CommonUtility.successToast(getActivity(),"Passcode save successfully");
                        CommonUtility.hideProgress();
                        getActivity().getSupportFragmentManager().popBackStack(ManageAccount.class.getSimpleName(),0);
                    }
                },3000);
            }
        });
        return view;
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
            ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
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
                    ((Dashboard)getActivity()).edit.setText("Save");
                    ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
                }else {
                    ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
                    shakeAnimation();
                }
        } else if (codeString.length() > MAX_LENGHT){
            //reset the input code
            ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
    }
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_anim);
       getActivity(). findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(getActivity(), "Passcode do not match", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);

    }


    @Override
    public void onPause()
    {
        super.onPause();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
        ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
        ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
    }
}
