package digimantra.veedaters.LoginSection.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.Fragment.TermCondition;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 6/11/17.
 */

public class SignUpFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
   @Nullable
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;
    FragmentInteractor interactor;
    @BindView(R.id.signUp)
    FrameLayout signUp;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userEmail)
    EditText userEmail;
    @BindView(R.id.userPassword)
    EditText userPassword;
    @BindView(R.id.faceBookButton)
    TextView faceBookButton;
    @BindView(R.id.googleButton)
    TextView googleButton;
    CallbackManager callbackManager;
    @BindView(R.id.logInImage)
    ImageView logInImage;
    LoginManager loginManager;
    @BindView(R.id.termscondition)
    TextView termscondition;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkbox;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInOptions gso;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.signup_fragment,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Sign Up");
        termscondition.setText(Html.fromHtml("I agree with"+"<u> Terms & Condition </u>"));
        ((LoginDashboard)getActivity()).makeCorenerRound(logInImage);
        callbackManager=CallbackManager.Factory.create();
        loginManager=LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken currentAccessToken=loginResult.getAccessToken();
                fetchUserData(currentAccessToken);
            }

            @Override
            public void onCancel() {
                Log.e("onCancel","");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("onError","");
                error.printStackTrace();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreateSignUp","onCreate");
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void fetchUserData(AccessToken currentAccessToken)
    {
       // CommonUtility.showProgress(getActivity(),"Fetching facebook data...");
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                       // CommonUtility.hideProgress();
                        getSignUp(object.optString("name"),object.optString("email"),object.optString("id"));
                        Log.e("ResponseFrom",object.toString()+"");
                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
       // parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @OnClick(R.id.googleButton)
    public void getLoginWithGoogle()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void getSignUpWithGoogle(String googleId,String name,String email,String type)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[social_id]",googleId);
        hashMap.put("User[name]",name);
        hashMap.put("User[email]",email);
        hashMap.put("User[social_media_type]",type);
                ApiClient.getClient().signUpWithSocialMedial(hashMap).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user=response.body();
                        if (user!=null)
                        {
                            if (user.is_success())
                            {
                                CommonUtility.successToast(getActivity(),"Register successfully !");
                                getActivity().getSupportFragmentManager().popBackStack();
                            }else {
                                CommonUtility.errorToast(getActivity(),user.getError()+"");
                            }
                        }else {
                            CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                        }
                        CommonUtility.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        CommonUtility.hideProgress();
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }
                });
    }
    private void getSignUp(String userName,String userEmail,String fbId)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[social_id]",fbId);
        hashMap.put("User[name]",userName);
        hashMap.put("User[email]",userEmail);
        hashMap.put("User[social_media_type]","facebook");
        ApiClient.getClient().signUpWithSocialMedial(hashMap).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user=response.body();
                if (user!=null)
                {
                    if (user.is_success())
                    {
                        CommonUtility.successToast(getActivity(),"Register successfully !");


                    }else {
                        CommonUtility.errorToast(getActivity(),response.body().getError()+"");
                    }
                }else {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CommonUtility.hideProgress();
                CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");

            }
        });
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(LoginDashboard)context;
    }
    @OnClick(R.id.termscondition)
    public void termCondition()
    {
        Intent intent=new Intent(getActivity(),TermCondition.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    @OnClick(R.id.signUp)
    public void getNoramlSignUp()
    {
       // interactor.replaceFragment(new CompleteProfile());
        if (getData(userName)==null)
        {
            CommonUtility.errorToast(getActivity(),"Enter username.");
            return;
        }
        if (getData(userEmail)==null)
        {
            CommonUtility.errorToast(getActivity(),"Enter email address");
            return;
        }
        if (getData(userPassword)==null)
        {
            CommonUtility.errorToast(getActivity(),"Enter password.");
            return;
        }
        if (!checkbox.isChecked())
        {
            CommonUtility.errorToast(getActivity(),"Please accept terms & Conditions");
            return;
        }
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[email]",getData(userEmail));
        hashMap.put("User[username]",getData(userName));
        hashMap.put("User[password]",getData(userPassword));
        hashMap.put("User[device_type]","Android");
        hashMap.put("User[device_token]",PreferenceConnector.getInstance(getContext()).readString(KeyValue.DEVICE_ID));

        ApiClient.getClient().register(hashMap).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                CommonUtility.hideProgress();
                User user=response.body();
                if (user!=null)
                {
                    if (user.is_success())
                    {
                        CommonUtility.successToast(getActivity(),"Register successfully !");
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.IS_LOGIN,true);
                        PreferenceConnector.getInstance(getActivity()).writeInt(KeyValue.USER_ID,user.getUserData().getId());
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USER_NAME,user.getUserData().getUsername());
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USER_EMAIL,user.getUserData().getEmail());
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                        startActivity(new Intent(getActivity(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else {
                        CommonUtility.errorToast(getActivity(),user.getError()+"");
                    }
                }else {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");

                CommonUtility.hideProgress();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            getSignUpWithGoogle(result.getSignInAccount().getId(),result.getSignInAccount().getDisplayName(),result.getSignInAccount().getEmail(),"google");

        }else {
            Log.e("UserInfoelelse","result.getSignInAccount().getId()"+"");
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }



    }
    @OnClick(R.id.faceBookButton)
    public void registerWithFaceBook()
    {
        LoginManager.getInstance().logOut();
        loginManager.logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile", "user_birthday"));

    }
    @Override
    public void onResume() {
        super.onResume();
        ((LoginDashboard)getActivity()).backButton.setVisibility(View.VISIBLE);

    }
    @OnClick(R.id.forgotPassword)
    public void onForgotClick()
    {
        interactor.replaceFragment(new ForgotPassword());

    }
    @Override
    public void onPause() {
        super.onPause();
        ((LoginDashboard)getActivity()).backButton.setVisibility(View.GONE);
       /* mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
