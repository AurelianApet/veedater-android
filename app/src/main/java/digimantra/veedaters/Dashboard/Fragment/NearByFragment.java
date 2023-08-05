package digimantra.veedaters.Dashboard.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;
import digimantra.veedaters.Adapter.ImageAdapter;
import digimantra.veedaters.Adapter.UserAdapter;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.FilterActivity;
import digimantra.veedaters.Dashboard.ParticulerUserData;
import digimantra.veedaters.Dashboard.PlanListActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PermissionUtil;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by dmlabs on 6/11/17.
 */

public class NearByFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PermissionUtil.PermissionResultCallback {
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int FILTER_REQUEST_CODE = 3000;
    @BindView(R.id.pager)
    ViewPager pager;
    List<UserData.User> arrayList=new ArrayList<>();
    DashboardInteractor interactor;
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    double latitude;
    double longitude;
    String age,distance,gender;
    // list of permissions
    @BindView(R.id.nothingFound)
    TextView nothingFound;
    ArrayList<String> permissions=new ArrayList<>();
    PermissionUtil permissionUtils;

    ImageAdapter imageAdapter;
    int finalFlag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.near_by_fragment,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Near by");
        permissionUtils=new PermissionUtil(getActivity());
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions,"Need GPS permission for getting your location",1);
        imageAdapter  =new ImageAdapter(getChildFragmentManager(),arrayList,getActivity(),true);
        pager.setAdapter(imageAdapter);
        pager.setClipToPadding(false);
        pager.setPageMargin(5);
        pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        pager.setPadding(30, 5, 30, 5);
        pager.setOffscreenPageLimit(3);
        Log.e("Tokenddddddd",PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID));
        imageAdapter.setOnPageClick(new ImageAdapter.OnPageClick() {
            @Override
            public void onClick(int pos) {
               // JZVideoPlayerStandard.releaseAllVideos();
                Intent intent=new Intent(getActivity(),ParticulerUserData.class);
                intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(pos).getId());
                startActivity(intent);
            }

            @Override
            public void makePayment(final int pos) {
                //openDialog();
                CommonUtility.showProgress(getActivity(),"Plese wait...");
                int flag=0;
                if (arrayList.get(pos).getUseMeta().getLike()==1) {
                    flag=0;
                }else {
                    flag=1;
                }
                finalFlag = flag;
                ApiClient.getClient().likeUser(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID), Integer.parseInt(arrayList.get(pos).getId()),flag).enqueue(new Callback<UserLikesResponse>() {
                    @Override
                    public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
                        CommonUtility.hideProgress();
                        if (response.body().getIsSuccess())
                        {
                            arrayList.get(pos).getUseMeta().setLike(finalFlag);
                            // arrayList.get(position).getUseMeta().setLike(value);
                            imageAdapter.notifyDataSetChanged();
                            pager.invalidate();

                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<UserLikesResponse> call, Throwable t) {
                        CommonUtility.hideProgress();
                    }
                });
            }


        });
        ((Dashboard)getActivity()).filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), FilterActivity.class),FILTER_REQUEST_CODE);
            }
        });
        return view;
    }
    private void openDialog()
    {
        Dialog builder = new Dialog(getActivity());
        builder.setContentView(R.layout.prmium_layout);
        FrameLayout getMembership= (FrameLayout) builder.findViewById(R.id.getMembership);
        ImageView logInImage= (ImageView) builder.findViewById(R.id.logInImage);
        makeCorenerRound(logInImage);
        getMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PlanListActivity.class);
                startActivity(intent);
            }
        });
        builder.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        builder.show();
    }
    private void makeCorenerRound(ImageView imageView)
    {
        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.toplayer)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 80, 80, mpaint);// Round Image Corner 100 100 100 100
        imageView.setImageBitmap(imageRounded);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        JZVideoPlayerStandard.releaseAllVideos();
    }

    private void getUserList(final boolean b)
    {
        HashMap<String,String> map=new HashMap<>();
        map.put("lat", VeeDatersApp.getLatitude());
        map.put("lng", String.valueOf(longitude));
        map.put("nearby", String.valueOf(1));
        map.put("page",String.valueOf(0));
        if (age!=null)map.put("age",age);
        if (distance!=null)map.put("distance",distance.equalsIgnoreCase("Worldwide")? "":distance);
        if (gender!=null) map.put("gender",gender.equalsIgnoreCase("All")? "":gender);
        Log.e("Param",map.toString());
        if (b)
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().nearBy(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //CommonUtility.successToast(getContext(),response.body().getUser().size()+"");
               if (b)
                CommonUtility.hideProgress();
                UserData data=response.body();
                if (data!=null)
                {
                    setDataToList(data);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                if (b)
                CommonUtility.hideProgress();
            }
        });
    }
    private void setDataToList(UserData data) {


        if (data.getUser()!=null && !data.getUser().isEmpty())
        {
            arrayList.clear();
            arrayList.addAll(data.getUser());
            imageAdapter.notifyDataSetChanged();
            pager.invalidate();

        }else {
            arrayList.clear();
            imageAdapter.notifyDataSetChanged();
            pager.invalidate();

        }


    }
    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(),resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
   /* private void getData()
    {
        arrayList.clear();
        arrayList.add(R.drawable.image1);
        arrayList.add(R.drawable.image2);
        arrayList.add(R.drawable.image4);
        arrayList.add(R.drawable.image5);
        arrayList.add(R.drawable.image6);
        arrayList.add(R.drawable.image7);
        arrayList.add(R.drawable.image8);
    }*/

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            inflater.inflate(R.menu.search_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.search_view:

               *//* CharacterPickerWindow mOptions = new CharacterPickerWindow(getActivity());
                ArrayList<String> strings=new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add(String.valueOf(i));
                }
                mOptions.setPicker(strings);
              //  setPickerData(mOptions.getPickerView());
                mOptions.showAtLocation(view, Gravity.BOTTOM, 0, 0);*//*
                return true;
        }
        return false;

    }*/
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final
                Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;

                    default:
                        break;
                }
                break;
            case FILTER_REQUEST_CODE:
                 age=data.getStringExtra("AGE");
                 distance=data.getStringExtra("DISTANCE");
                 gender=data.getStringExtra("GENDER");
               // getUserList(true);
                break;
        }

    }

 /*   private void getFilterUser(String age, String distance, String gender)
    {

        HashMap<String,String> map=new HashMap<>();
        map.put("lat", String.valueOf(latitude));
        map.put("lng", String.valueOf(longitude));
        map.put("age",age);
        map.put("distance",distance);
        map.put("gender",gender);
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().nearBy(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //CommonUtility.successToast(getContext(),response.body().getUser().size()+"");
                CommonUtility.hideProgress();
                UserData data=response.body();
                if (data!=null)
                {
                    setDataToList(data);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });

    }*/

    private void getLocation()
    {

        if (Dashboard.isPermissionGranted)
        {

            try
            {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                if (mLastLocation != null)
                {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    VeeDatersApp.setLatitude(String.valueOf(latitude));
                    VeeDatersApp.setLongitude(String.valueOf(longitude));
                    getUserList(true);
                    Log.e("LATLONGOFUSER",VeeDatersApp.getLatitude()+" "+" "+VeeDatersApp.getLongitude());
                }
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }

        }

    }
    @Override
    public void onPause()
    {
        super.onPause();
        mGoogleApiClient.disconnect();
        age=null;
        distance=null;
        gender=null;
        ((Dashboard)getActivity()).nearByImage.setImageResource(R.drawable.map);
        ((Dashboard)getActivity()).filter.setVisibility(View.GONE);
        ((Dashboard)getActivity()).nearByText.setTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        pager.setCurrentItem(0);
        if (checkPlayServices())
        {
            buildGoogleApiClient();
        }
        ((Dashboard)getActivity()).filter.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).nearByImage.setImageResource(R.drawable.map_select);
        ((Dashboard)getActivity()).nearByText.setTextColor(getResources().getColor(R.color.pink));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void PermissionGranted(int request_code) {
      Dashboard.  isPermissionGranted=true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }
}
