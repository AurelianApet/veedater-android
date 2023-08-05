package digimantra.veedaters.Dashboard.Fragment;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.gms.vision.text.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.SetPasscode;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.lock.MyDevicePolicyReceiver;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.settings.SetPasscodeFragment;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dmlabs on 29/1/18.
 */

public class ManageAccount extends Fragment
{
    @BindView(R.id.deactivate)
    SwitchCompat deactivate;
    @BindView(R.id.passcodeSwitch)
    SwitchCompat passcodeSwitch;
    @BindView(R.id.delete)
    LinearLayout delete;
    @BindView(R.id.termscondition)
    LinearLayout termscondition;
    DashboardInteractor interactor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.manage_account,container,false);
        ButterKnife.bind(this,view);
        if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.ISACTIVE))
        {
            deactivate.setChecked(false);
        }else {
            deactivate.setChecked(true);
        }


        interactor.setTitle("Manage Account");
        deactivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                    {
                        perFomAction("deactivate");
                    }else {
                        perFomAction("activate");
                    }
            }
        });
        passcodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                    {
                        if (!PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.ISPASSCODESET))
                        {
                            startActivity(new Intent(getActivity(), SetPasscode.class));
                           // ((Dashboard)getActivity()).processFragment(new SetPasscodeFragment());
                        }
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISPASSCODEACTIVE,b);
                    }else {
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISPASSCODEACTIVE,b);
                    }
            }
        });
        return view;
    }

    @OnClick(R.id.feedback)
    public void getFeedback()
    {
        Intent intent=new Intent(getActivity(),FeedBack.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @OnClick(R.id.delete)
    public void onDeleteClick()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("VeeDaters");
        builder.setMessage("Do you want to delete account?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                perFomAction("delete");
            }
        });
        builder.create().show();
    }
    private void perFomAction(final String action)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().deleteAccount(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID),action).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                BlockResponse blockResponse=response.body();
                if (blockResponse!=null && blockResponse.getIsSuccess())
                {
                    if (action.equalsIgnoreCase("deactivate"))
                    {
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,false);
                        showAlert(blockResponse.getMessage());
                    }else if (action.equalsIgnoreCase("activate"))
                    {
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,true);
                        showAlert(blockResponse.getMessage());
                    }else if (action.equalsIgnoreCase("delete"))
                    {
                        logout();
                    }

                }
                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }

    private void showAlert(String s) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage(s+"");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void logout() {
        PreferenceConnector.getInstance(getActivity()).logout();
        Intent intent=new Intent(getActivity(), LoginDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.termscondition)
    public void termCondition()
    {
        Intent intent=new Intent(getActivity(),TermCondition.class);
        startActivity(intent);
       getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
        if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.ISPASSCODEACTIVE))
        {
            passcodeSwitch.setChecked(true);
        }else {
            passcodeSwitch.setChecked(false);
        }
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
