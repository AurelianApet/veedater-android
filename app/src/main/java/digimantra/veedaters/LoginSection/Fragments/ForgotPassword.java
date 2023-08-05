package digimantra.veedaters.LoginSection.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 8/11/17.
 */

public class ForgotPassword extends Fragment
{
    @BindView(R.id.registration)
    TextView registration;
    FragmentInteractor interactor;
    @BindView(R.id.forgotPassword)
    FrameLayout forgotPassword;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.logInImage)
    ImageView logInImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.forgot_password,container,false);
        ButterKnife.bind(this,view);
        ((LoginDashboard)getActivity()).makeCorenerRound(logInImage);
        interactor.setTitle("Forgot Password");
        return view;
    }
    @OnClick(R.id.registration)
    public void onRegistrationClick()
    {
        interactor.replaceFragment(new SignUpFragment());
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(LoginDashboard)context;
    }
    @OnClick(R.id.forgotPassword)
    public void getPassword()
    {
        if (getData(emailAddress)==null)
        {
            CommonUtility.errorToast(getActivity(),"Enter email address");
            return;
        }
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[email]",getData(emailAddress));
        ApiClient.getClient().forgotPassword(hashMap).enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                CommonUtility.hideProgress();
                User user=response.body();
                if (user!=null)
                {
                    if (user.is_success())
                    {
                        CommonUtility.successToast(getActivity(),user.getMessage()+"");
                        getActivity().getSupportFragmentManager().popBackStack();
                    }else {
                        CommonUtility.errorToast(getActivity(),user.getError()+"");
                    }
                }else {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CommonUtility.hideProgress();
                CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
            }
        });
    }
    private String getData(EditText editText)
    {
        if (editText.getText().toString().trim().isEmpty())
        {
            return null;
        }else {
            return editText.getText().toString().trim();
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        ((LoginDashboard)getActivity()).backButton.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginDashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
    }
}
