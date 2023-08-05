package digimantra.veedaters.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.ConfirmPasscode;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.Fragment.DashboardInteractor;
import digimantra.veedaters.Dashboard.SetPasscode;
import digimantra.veedaters.R;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

/**
 * Created by dmlabs on 3/2/18.
 */

public class SetPasscodeFragment extends Fragment
{
    @BindViews({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_clear})
    List<View> btnNumPads;
    @BindViews({R.id.dot_1, R.id.dot_2, R.id.dot_3, R.id.dot_4})
    List<ImageView> dots;
    DashboardInteractor interactor;
    private static final String TRUE_CODE = "2869";
    private static final int MAX_LENGHT = 4;
    private String codeString = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_set_passcode,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("SetPasscode");
        return view;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
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
        return s.substring(0, s.length() - 1);
    }
    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9})
    public void onClick(Button button) {
        getStringCode(button.getId());
        if (codeString.length() == MAX_LENGHT) {

            /*Intent intent=new Intent(getActivity(),ConfirmPasscode.class);
            intent.putExtra("PASSCODE",codeString);
            startActivity(intent);*/
            ((Dashboard)getActivity()).processFragment(ConfirmPassCodeFragment.getInstance(codeString));

        } else if (codeString.length() > MAX_LENGHT){
            //reset the input code
            codeString = "";
            getStringCode(button.getId());
        }
        setDotImagesState();
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
    }

}
