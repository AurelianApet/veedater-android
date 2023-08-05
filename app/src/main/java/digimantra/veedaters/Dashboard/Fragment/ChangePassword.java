package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 23/1/18.
 */

public class ChangePassword extends Fragment
{
    @BindView(R.id.oldPassword)
    EditText oldPassword;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.reenterPassword)
    EditText reenterPassword;
    DashboardInteractor interactor;
    boolean isSame;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.change_password,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Change Password");
        reenterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().equals(newPassword.getText().toString()))
                    {
                        reenterPassword.setError("Password do not match");
                    }else {
                        reenterPassword.setError(null);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((Dashboard)getActivity()).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                changePassword();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    private void changePassword()
    {
        if (isEmpty(oldPassword))
        {
            oldPassword.setError("Enter old password");
            return;
        }
        if (isEmpty(newPassword))
        {
            newPassword.setError("Enter new password");
            return;
        }
        if (isEmpty(reenterPassword))
        {
            reenterPassword.setError("Enter password again");
            return;
        }
        if (!newPassword.getText().toString().equals(reenterPassword.getText().toString()))
        {
            reenterPassword.setError("Password do not match");
            return;
        }
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> map=new HashMap<>();
        map.put("User[old_password]",oldPassword.getText().toString());
        map.put("User[new_password]",newPassword.getText().toString());
        ApiClient.getClient().changePassword(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                CommonUtility.hideProgress();
                BlockResponse response1=response.body();
                if (response1!=null && response1.getIsSuccess())
                {
                    CommonUtility.successToast(getActivity(),response1.getMessage()+"");
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    CommonUtility.errorToast(getActivity(),"Invalid password");
                }
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
                CommonUtility.errorToast(getActivity(),"Something went wrong");
            }
        });
    }
    @Override
    public void onResume()
    {
        super.onResume();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).edit.setText("Done");
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
    }
    @Override
    public void onPause()
    {
        super.onPause();
        ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
        ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
        ((Dashboard)getActivity()).edit.setText("Edit");
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
    }
    private boolean isEmpty(EditText text)
    {
        if (!TextUtils.isEmpty(text.getText().toString()))
        {
            return false;
        }
        return true;
    }
}
