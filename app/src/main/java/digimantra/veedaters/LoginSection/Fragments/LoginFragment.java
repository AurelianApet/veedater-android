package digimantra.veedaters.LoginSection.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.facebook.FacebookSdk;
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
import com.google.firebase.iid.FirebaseInstanceId;

import net.hockeyapp.android.CrashManager;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.Model.LoginResponse;
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

public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.registration)
    TextView registration;
    @BindView(R.id.forgotPassword)
    TextView forgotPassword;
    FragmentInteractor interactor;
    FrameLayout logIn;
    @BindView(R.id.logInImage)
        ImageView logInImage;
    CallbackManager callbackManager;
    @BindView(R.id.faceBook)
    TextView faceBook;
    @BindView(R.id.google)
    TextView google;
    LoginManager loginManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInOptions gso;
    @BindView(R.id.userEmail)
    EditText userEmail;
    @BindView(R.id.userPassword)
    EditText userPassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Login");
        PreferenceConnector connector=PreferenceConnector.getInstance(getContext());
        Log.e("USERDEVICEID",connector.readString(KeyValue.DEVICE_ID));
        if (PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID).equalsIgnoreCase(""))
        {
            new AsyncTask<Void,Void,String>()
            {
                @Override
                protected String doInBackground(Void... params)
                {
                    String token = FirebaseInstanceId.getInstance().getToken();
                    while(token == null)//this is used to get firebase token until its null so it will save you from null pointer exeption
                    {
                        token = FirebaseInstanceId.getInstance().getToken();
                    }
                    return token;
                }
                @Override
                protected void onPostExecute(String result)
                {
                    PreferenceConnector connector=PreferenceConnector.getInstance(getActivity());
                    connector.writeString(KeyValue.DEVICE_ID,result);
                }
            }.execute();
        }
        makeCorenerRound();
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
    private void makeCorenerRound()
    {
        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.toplayer)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100
        logInImage.setImageBitmap(imageRounded);
    }

    private void fetchUserData(AccessToken currentAccessToken)
    {

        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        logInWithFacebook(object.optString("id"),"facebook",object.optString("email"),object.optString("name"));
                        Log.e("ResponseFrom",object.toString()+"");
                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,link");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @OnClick(R.id.faceBook)
    public void getLoginWithFacebook()
    {
        LoginManager.getInstance().logOut();
        loginManager.logInWithReadPermissions(getActivity(), Arrays.asList("email", "public_profile", "user_birthday"));
    }
    private void logInWithFacebook(String fbId,String type,String email,String name)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[social_id]",fbId);
        hashMap.put("User[social_media_type]",type);
        hashMap.put("User[email]",email);
        hashMap.put("User[username]",name);
        hashMap.put("User[device_token]",PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID));
        hashMap.put("User[device_type]","Android");
        Log.e("ParamFb",hashMap.toString());
            ApiClient.getClient().logIn(hashMap).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse user=response.body();
                    if (user!=null)
                    {
                        if (user.getIsSuccess())
                        {
                            // TODO: 14/11/17 Goto Dashboard after successful signIn
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.IS_LOGIN,true);
                            PreferenceConnector.getInstance(getActivity()).storeLogUser(user);
                            PreferenceConnector.getInstance(getActivity()).writeInt(KeyValue.USER_ID, Integer.parseInt(user.getUser().getId()));
                            PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.SOCIAL_ID,user.getUser().getSocialId());
                            PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.SOCIAL_MEDIA,user.getUser().getSocialMediaType());
                            if (user.getUser().getSubscription()!=null)
                            {
                                PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,true);
                            }else {
                                PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                            }
                            if (user.getUser().getIsActive().equalsIgnoreCase("1"))
                            {
                                PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,true);
                            }else {
                                PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,false);
                            }
                            startActivity(new Intent(getActivity(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else {
                            CommonUtility.errorToast(getActivity(),user.getError()+"");
                        }
                    }else {
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }

                    CommonUtility.hideProgress();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    CommonUtility.hideProgress();
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
            });
    }
    @OnClick(R.id.google)
    public void getLoginWithGoogle()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status)
                    {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.getSignInAccount()!=null)
            {
                loginWithGoogle(result.getSignInAccount().getDisplayName(),result.getSignInAccount().getEmail(),result.getSignInAccount().getId(),"google");
            }

        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }

    }
    private void loginWithGoogle(String name,String email,String id,String type)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[social_id]",id);
        hashMap.put("User[email]",email);
        hashMap.put("User[username]",name);
        hashMap.put("User[social_media_type]",type);
        hashMap.put("User[device_token]",PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID));
        hashMap.put("User[device_type]","Android");
        Log.e("ParamGoogel",hashMap.toString());
        ApiClient.getClient().logIn(hashMap).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse user=response.body();
                if (user!=null)
                {
                    if (user.getIsSuccess())
                    {
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.IS_LOGIN,true);
                        // TODO: 14/11/17 Goto Dashboard after successful signIn
                        PreferenceConnector.getInstance(getActivity()).storeLogUser(user);
                        PreferenceConnector.getInstance(getActivity()).writeInt(KeyValue.USER_ID, Integer.parseInt(user.getUser().getId()));
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.SOCIAL_ID,user.getUser().getSocialId());
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.SOCIAL_MEDIA,user.getUser().getSocialMediaType());
                        if (user.getUser().getSubscription()!=null)
                        {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,true);
                        }else {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                        }
                        if (user.getUser().getIsActive().equalsIgnoreCase("1"))
                        {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,true);
                        }else {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,false);
                        }
                        startActivity(new Intent(getActivity(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));


                    }else {
                        CommonUtility.errorToast(getActivity(),user.getError()+"");
                    }
                }else {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }

                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
    @OnClick(R.id.registration)
    public void onRegistrationClick()
    {
        interactor.replaceFragment(new SignUpFragment());
    }


    @OnClick(R.id.logIn)
    public void onLoginClick()
    {
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
        Log.e("USERMETA",getData(userPassword));
        Log.e("USERMETA",getData(userEmail));
        CommonUtility.showProgress(getActivity(),"Please wait...");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("User[password]",getData(userPassword));
        hashMap.put("User[username]",getData(userEmail));
        hashMap.put("User[device_token]",PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID));
        hashMap.put("User[device_type]","Android");
        Log.e("Param",hashMap.toString());
        ApiClient.getClient().logIn(hashMap).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse user=response.body();
                if (user!=null) {
                    if (user.getIsSuccess()) {
                        PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.IS_LOGIN,true);
                        PreferenceConnector.getInstance(getActivity()).writeInt(KeyValue.USER_ID, Integer.parseInt(user.getUser().getId()));
                        PreferenceConnector.getInstance(getActivity()).storeLogUser(user);
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USER_NAME,user.getUser().getUsername());
                        PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USER_EMAIL,user.getUser().getEmail());
                        if (user.getUser().getSubscription()!=null)
                        {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,true);
                        }else {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.HAS_SUBSCRIPTION,false);
                        }
                        if (user.getUser().getIsActive().equalsIgnoreCase("1"))
                        {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,true);
                        }else {
                            PreferenceConnector.getInstance(getActivity()).writeBoolean(KeyValue.ISACTIVE,false);
                        }

                        startActivity(new Intent(getActivity(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else {
                        CommonUtility.errorToast(getActivity(),user.getError()+"");
                    }
                }else {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong !");
                }

                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                CommonUtility.confusingToast(getActivity(),"Something went wrong !");
                CommonUtility.hideProgress();
            }
        });

    }
    @OnClick(R.id.forgotPassword)
    public void onForgotClick()
    {
        interactor.replaceFragment(new ForgotPassword());
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(LoginDashboard)context;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((LoginDashboard)getActivity()).backButton.setVisibility(View.GONE);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onPause()
    {
        super.onPause();
       /* mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();*/

    }
    @Override
    public void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }
}
