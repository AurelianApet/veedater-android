package digimantra.veedaters.Dashboard.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.bigkoo.pickerview.MyOptionsPickerView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;


import org.apache.commons.lang3.StringEscapeUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.DataPicker;
import digimantra.veedaters.Dashboard.FilterActivity;
import digimantra.veedaters.Dashboard.MapViewActivity;
import digimantra.veedaters.Dashboard.PlanListActivity;
import digimantra.veedaters.Dashboard.PlayListDialog;
import digimantra.veedaters.Dashboard.PlayerActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.FCM.TokenServices;
import digimantra.veedaters.LoginSection.ChooseGenderActivity;
import digimantra.veedaters.LoginSection.LoginDashboard;
import digimantra.veedaters.Model.*;
import digimantra.veedaters.Model.UserProfile;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import io.blackbox_vision.wheelview.view.DatePickerPopUpWindow;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 13/11/17.
 */

public class EditProfile extends Fragment implements DatePickerPopUpWindow.OnDateSelectedListener {
    DashboardInteractor interactor;
    @BindView(R.id.aboutUser)
    EditText aboutUser;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.usergender)
    TextView usergender;
    @BindView(R.id.nation)
    TextView nation;
    @BindView(R.id.religionTxt)
    TextView religionTxt;
    @BindView(R.id.sportTxt)
    TextView sportTxt;
    @BindView(R.id.incomeTxt)
    TextView incomeTxt;
    @BindView(R.id.styleTxt)
    TextView styleTxt;
    @BindView(R.id.alchohalSwitch)
    SwitchCompat alchohalSwitch;
    @BindView(R.id.tattoSwitch)
    SwitchCompat tattoSwitch;
    @BindView(R.id.smokeSwitch)
    SwitchCompat smokeSwitch;
    @Nullable
    @BindView(R.id.addVideo)
    ImageView addVideo;
    @BindView(R.id.imageOne)
    ImageView imageOne;
    @BindView(R.id.imageTwo)
    ImageView imageTwo;
    @BindView(R.id.imageThree)
    ImageView imageThree;
    @BindView(R.id.imageFour)
    ImageView imageFour;
    @BindView(R.id.addFour)
    ImageView addFour;
    @BindView(R.id.addThree)
    ImageView addThree;
    @BindView(R.id.addTwo)
    ImageView addTwo;
    @BindView(R.id.addOne)
    ImageView addOne;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.videoView)
    UniversalVideoView videoView;
    @BindView(R.id.addressRoot)
    LinearLayout addressRoot;
    String dateTosend="";
   /* @BindView(R.id.media_controller)
    UniversalMediaController media_controller;*/
   /* @BindView(R.id.picassoVideoView)
    VideoPlayView picassoVideoView;*/
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.logout)
    LinearLayout logout;
    boolean isEdit=false;
    String videoPath;
    File thumb;
    ArrayList<ImageView> imageViews=new ArrayList<>();
    ArrayList<ImageView> views=new ArrayList<>();
    ArrayList<String> imagePath=new ArrayList<>();
    ArrayList<Integer> removeIndex=new ArrayList<>();
    ArrayList<File> files=new ArrayList<>();
    private String userimage;
    private boolean isVideoUpdated;
    private boolean isImageUpdated;
    private ArrayList<String> gender=new ArrayList<>();
    private ArrayList<String> statusList=new ArrayList<>();
    private ArrayList<String> smokeList=new ArrayList<>();
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    File videoFile;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int CAMERA_RQ = 6969;
    MyOptionsPickerView statusPicker;
    MyOptionsPickerView smokePicker;
    MyOptionsPickerView genderPicker;
    private UserProfile profile;
    private static final String[] VIDEO_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK
    };
    private static final int REQUEST_VIDEO_PERMISSIONS_REQUEST = 1000;
    private static final int RECORD_VIDEO_REQUEST = 2000;
    private static final int ADDRESS_GET = 3000;

    String workFolder = null;
    String demoVideoFolder = null;
    String demoVideoPath = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;
    private boolean isFull;
    private int cachedHeight;
    @Nullable
    @BindView(R.id.clickView)
    View clickView;
    @BindView(R.id.videoLayout)
    LinearLayout mVideoLayout;
    ArrayList<PrefData> arrayList=new ArrayList<>();
    public static final int STYLE=5;
    public static final int SPORT=3;
    public static final int INCOME=4;
    public static final int RELIGION=2;
    public static final int STATUS=6;
    public static final int NATION=7;
    public static final int GENDER=1;
    @BindView(R.id.progress)
    ProgressBar progress;
    private int mSeekPosition;
    public  static double latitude;
    public  static double longitude;
    @BindView(R.id.genderImage)
    ImageView genderImage;
    @BindView(R.id.userName)
    EditText userName;
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

            "Man",
            "Woman",
            "Not specified"
    };
    String martialStatus [] = {
            "Single",
            "Married",
            "Remarried",
            "Separated",
            "Divorced",
            "Widowed"
    };
    ArrayList<String> country=new ArrayList<>();
    private String tatoo="no",alchohal="no",smoke="no";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.edit_user_profile,container,false);
        ButterKnife.bind(this,view);
        setImageHeight();
        gender.clear();
        statusList.clear();
        smokeList.clear();
        gender.add("male");
        gender.add("female");
        smokeList.add("yes");
        smokeList.add("no");
        statusList.add("single");
        statusList.add("married");
        makeAllDisable();
        interactor.setTitle("Profile");
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
        tattoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    tatoo="yes";
                }else {
                    tatoo="no";
                }
            }
        });
        LoginResponse response= PreferenceConnector.getInstance(getActivity()).getLogUser();
        if (response!=null)
        {
            setData(response);
        }
        getProfileData();
        Log.e("HEARDER_TOKEN",String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)));

        /*videoView.setMediaController(media_controller);*/
      //  media_controller.setOnLoadingView(R.layout.loading);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {


            }
        });
        setVideoAreaSize();
        videoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                isFull=isFullscreen;
                if (isFull) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);


                } else {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height =cachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
                }
                switchTitleBar(!isFull);
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {

            }
        });
        clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoPath!=null)
                openDialog(videoPath);
            }
        });

      //  profile=getArguments().getParcelable("USERDATA");

        ((Dashboard)getActivity()).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (((Dashboard)getActivity()).edit.getText().toString().equalsIgnoreCase("Edit"))
                {
                   // Toast.makeText(getActivity(), "All enable", Toast.LENGTH_SHORT).show();
                    makeAllEnable();
                    isEdit=true;
                    ((Dashboard)getActivity()).edit.setText("Done");

                }else
                    {
                    ((Dashboard)getActivity()).edit.setText("Edit");
                    isEdit=false;
                    makeAllDisable();
                        saveProfileData();
                        uploadVideo(videoFile,thumb);
                }
            }
        });
        return view;
    }
    @OnClick(R.id.addressRoot)
    public void getMap()
    {
        if (isEdit)
        {
            startActivityForResult(new Intent(getActivity(), MapViewActivity.class),ADDRESS_GET);
        }

        //((Dashboard)getActivity()).processFragment(new GoogleAddressFragment());
    }
    private void switchTitleBar(boolean show)
    {
        android.support.v7.app.ActionBar supportActionBar = ((Dashboard)getActivity()).actionBar;
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
                ((Dashboard)getActivity()).bottomTab.setVisibility(View.VISIBLE);
            } else {
                supportActionBar.hide();
                ((Dashboard)getActivity()).bottomTab.setVisibility(View.GONE);
            }
        }
    }
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
            }
        });
    }
    private boolean isReadStorageAllowed()
    {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }
    private void getProfileData()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().getProfileData(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID))).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                CommonUtility.hideProgress();
                profile=response.body();
                if (profile!=null)
                {
                    if (profile.getIsSuccess())
                    {
                        setUserData(profile);
                    }else {
                        CommonUtility.errorToast(getActivity(),"Somthing wend wrong...");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                CommonUtility.hideProgress();
                CommonUtility.errorToast(getActivity(),"Somthing wend wrong...");
            }
        });
    }
    private boolean isSubscribedUser()
    {
       return PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION);
    }
    //Set User Data
    private void setUserData(UserProfile profile)
    {
      UserProfile.NewUser.UserMeta meta= profile.getUser().getUserMeta();
        List<UserProfile.NewUser.UserPhoto> photo= profile.getUser().getUserPhoto();
        UserProfile.NewUser user=profile.getUser();

        if (profile.getUser().getEmail()!=null)
        {
            email.setText(profile.getUser().getEmail()+"");
        }
        if (profile.getUser().getAddress()!=null)
        {
            address.setText(profile.getUser().getAddress()+"");
        }
        if (profile.getUser().getUserVideo()!=null && profile.getUser().getUserVideo().getVideoUrl()!=null)
        {
            if (isVideoUpdated)
            {
                videoPath=PreferenceConnector.getInstance(getActivity()).readString(KeyValue.USER_VIDEO_LINK);
                videoView.setVideoPath(PreferenceConnector.getInstance(getActivity()).readString(KeyValue.USER_VIDEO_LINK));
                videoView.seekTo(mSeekPosition);
                videoView.start();
               // picassoVideoView.setState(VideoPlayView.State.Initial.INSTANCE);
                //picassoVideoView.setVideoUrl(PreferenceConnector.getInstance(getActivity()).readString(KeyValue.USER_VIDEO_LINK));
            }else {
                videoPath=ApiClient.IMG_BASE+profile.getUser().getUserVideo().getVideoUrl();
                videoView.setVideoPath(videoPath);
                videoView.seekTo(mSeekPosition);
                videoView.start();
               // picassoVideoView.setState(VideoPlayView.State.Initial.INSTANCE);
               // picassoVideoView.setVideoUrl(videoPath);
            }

        }else {
            progress.setVisibility(View.GONE);
        }

        if (meta!=null)
        {
            if (meta.getAlchohol()!=null && meta.getAlchohol().equalsIgnoreCase("yes"))
            {
                alchohalSwitch.setChecked(true);
            }else {
                alchohalSwitch.setChecked(false);
            }
            if (meta.getGender()!=null && meta.getGender().equalsIgnoreCase("Man"))
            {
               genderImage.setImageResource(R.drawable.masculine);
            }else if(meta.getGender()!=null && meta.getGender().equalsIgnoreCase("Woman")){
                genderImage.setImageResource(R.drawable.femenine);
            }
            if (meta.getAge()!=null && !meta.getAge().equalsIgnoreCase("")) {
                age.setText(meta.getAge()+"");
            }
            if (meta.getSmoke()!=null && meta.getSmoke().equalsIgnoreCase("yes"))
            {
                smokeSwitch.setChecked(true);
            }else {
                smokeSwitch.setChecked(false);
            }
            if (meta.getTatoo()!=null && meta.getTatoo().equalsIgnoreCase("yes"))
            {
                tattoSwitch.setChecked(true);
            }else {
                tattoSwitch.setChecked(false);
            }
            if (meta.getAbout()!=null && !meta.getAbout().equalsIgnoreCase(""))
            {
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(meta.getAbout());
                aboutUser.setText(fromServerUnicodeDecoded);
            }
            if (meta.getStatus()!=null && !meta.getStatus().equalsIgnoreCase(""))
            {
                status.setText(meta.getStatus());
            }
            if (meta.getGender()!=null && !meta.getGender().equalsIgnoreCase(""))
            {
                usergender.setText(meta.getGender());
            }
            if (meta.getNation()!=null && !meta.getNation().equalsIgnoreCase(""))
            {
                nation.setText(meta.getNation());
            }
            if (meta.getReligion()!=null && !meta.getReligion().equalsIgnoreCase(""))
            {
                religionTxt.setText(meta.getReligion());
            }
            if (meta.getStyle()!=null && !meta.getStyle().equalsIgnoreCase(""))
            {
                styleTxt.setText(meta.getStyle());
            }
            if (meta.getSport()!=null && !meta.getSport().equalsIgnoreCase(""))
            {
                sportTxt.setText(meta.getSport());
            }
            if (meta.getMinIncome()!=null && !meta.getMinIncome().equalsIgnoreCase(""))
            {
                incomeTxt.setText("$"+meta.getMinIncome()+" - "+"$"+meta.getMaxIncome());
            }
            if (meta.getDob()!=null && !meta.getDob().equalsIgnoreCase(""))
            {
                dateTosend=meta.getDob();
                setDob(meta.getDob());
                //birthday.setText(meta.getDob());
            }
        }
        if (photo!=null)
        {

            for (int i = 0; i <photo.size() ; i++) {
                if (!isImageUpdated)
                showImages(i,photo.get(i).getPhotoPath(),photo.get(i).getPhotosId());
            }

        }
    }
    private void setDob(String dob)
    {
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date input = inputFormat.parse(dob);
            DateFormat outputFormat = new SimpleDateFormat("MMM,dd yyyy", Locale.ENGLISH);
            String finaldate = outputFormat.format(input);
            birthday.setText(finaldate);
        }catch (Exception ex) {

        }
    }
    private void showImages(int i, String photoPath, String photosId)
    {
        if (i<=3) {
            views.get(i).setTag(photosId);
            Picasso.with(getActivity()).load(ApiClient.IMG_BASE+photoPath).fit()
                    .into(views.get(i));
        }
    }
    private void setAge(String age)
    {}
    private void setImageHeight() {

        int width=(getDeviceWidth()/2)-10;
        imageOne.getLayoutParams().height=width;
        imageTwo.getLayoutParams().height=width;
        imageThree.getLayoutParams().height=width;
        imageFour.getLayoutParams().height=width;
        views.add(imageOne);
        views.add(imageTwo);
        views.add(imageThree);
        views.add(imageFour);
    }

    private void setData(LoginResponse response)
    {
        if (response.getUser()!=null)
        {
            userName.setText(response.getUser().getUsername()!=null ? response.getUser().getUsername() : "");
        }
    }
    private int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
       getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
    private void makeAllDisable()
    {
        aboutUser.setInputType(InputType.TYPE_NULL);

    }
    private void makeAllEnable()
    {
        aboutUser.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

    }
   /* public boolean hasAllPermissions()
    {
        for (String permission : VIDEO_PERMISSIONS)
        {
            if (ContextCompat.checkSelfPermission(getActivity(),permission)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(VIDEO_PERMISSIONS,REQUEST_VIDEO_PERMISSIONS_REQUEST);
            }
        }
        return true;
    }*/

    @OnClick(R.id.addOne)
    public void addFirstImage()
    {
        if (isEdit) {
            if (isSubscribedUser()) {
                imageViews.clear();
                imageViews.add(imageOne);

                removeImage();
                getImage();
                return;
            }
            if (!isSubscribedUser() && getCount()==1)
            {
                openDialog(R.layout.prmium_layout);
            }else {
                imageViews.clear();
                imageViews.add(imageOne);

                removeImage();
                getImage();
                return;
            }
        }
    }
    private void openDialog(int layout)
    {
        final Dialog builder = new Dialog(getActivity());
        builder.setContentView(layout);
        FrameLayout getMembership= (FrameLayout) builder.findViewById(R.id.getMembership);
        ImageView logInImage= (ImageView) builder.findViewById(R.id.logInImage);
        makeCorenerRound(logInImage);
        getMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
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
    private void getImage()
    {
          if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
            // do your stuff..
            Config config = new Config();
            config.setCameraHeight(R.dimen.app_camera_height);
            config.setToolbarTitleRes(R.string.custom_title);
            config.setSelectionMin(1);
            config.setSelectionLimit(1);
            config.setTabSelectionIndicatorColor(R.color.pink);
            config.setSelectedBottomHeight(R.dimen.bottom_height);
            ImagePickerActivity.setConfig(config);
            Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
            startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
        }
    }
    @OnClick(R.id.addTwo)
    public void addSecondImage()
    {
        if (isEdit) {
            if (isSubscribedUser())
            {
                imageViews.clear();
                imageViews.add(imageTwo);

                removeImage();
                getImage();
                return;
            }
            if (!isSubscribedUser() && getCount()==1)
            {
                openDialog(R.layout.prmium_layout);
            }else {
                imageViews.clear();
                imageViews.add(imageTwo);

                removeImage();
                getImage();
                return;
            }

        }
    }

    @OnClick(R.id.addThree)
    public void addThirdImage()
    {
        if (isEdit)
        {
            if (isSubscribedUser())
            {
                imageViews.clear();
                imageViews.add(imageThree);

                removeImage();
                getImage();
                return;
            }
            if (!isSubscribedUser() && getCount()==1)
            {
                openDialog(R.layout.prmium_layout);
            }else {
                imageViews.clear();
                imageViews.add(imageThree);

                removeImage();
                getImage();
            }

        }

    }
    @OnClick(R.id.addFour)
    public void addFourthImage() {
        if (isEdit) {
            if (isSubscribedUser())
            {
                imageViews.clear();
                imageViews.add(imageFour);

                removeImage();
                getImage();
                return;
            }
            if (!isSubscribedUser() && getCount()==1)
            {
                openDialog(R.layout.prmium_layout);

            }else {
                imageViews.clear();
                imageViews.add(imageFour);

                removeImage();
                getImage();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean shouldShowRequestPermissionRationale() {
        for (String permission : VIDEO_PERMISSIONS) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasAllPermissions()
    {
        for (String permission : VIDEO_PERMISSIONS)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void handleRequestPermissionsResult() {
        if (hasAllPermissions()) {
            launchvideo();
            //launchVideoRecorder();
        } else if (shouldShowRequestPermissionRationale()) {
            requestPermissions(VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS_REQUEST);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_VIDEO_PERMISSIONS_REQUEST:
               // handleRequestPermissionsResult();
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    launchvideo();
                    //Displaying a toast
                    Toast.makeText(getActivity(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();

                }else{
                    //Displaying another toast if permission is not granted
                    Toast.makeText(getActivity(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
                }

                break;
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                    getUserProfile();
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
    @OnClick(R.id.addVideo)
    public void getUserProfile() {
        if (isEdit) {
            if (isReadStorageAllowed()) {
                launchvideo();

            }else {
                requestStoragePermission();
            }
           // requestPermissions(VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS_REQUEST);
            //
        }
    }
    private void requestStoragePermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_VIDEO_PERMISSIONS_REQUEST);
    }
    private void launchvideo()
    {
        File saveDir = null;
        saveDir = new File(Environment.getExternalStorageDirectory(), "VeeDaters");
        saveDir.mkdirs();
       // if (!saveDir.mkdir())
            //throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");
        MaterialCamera materialCamera =
                new MaterialCamera(this)
                        .saveDir(saveDir)
                        .allowRetry(true)
                        .showPortraitWarning(false)
                        .restartTimerOnRetry(true)
                        .countdownMillis(20000)
                        .qualityProfile(MaterialCamera.QUALITY_480P)
                        .videoPreferredHeight(480)
                        .defaultToFrontFacing(true);
        materialCamera.start(CAMERA_RQ);
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    private String readableFileSize(long size)
    {
        if (size <= 0) return size + " B";
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups))
                + " "
                + units[digitGroups];
    }
    private String fileSize(File file)
    {
        return readableFileSize(file.length());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == Activity.RESULT_OK ) {
            setCount(1);
            isImageUpdated=true;
            ArrayList<Uri>  image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
           // userImage.setImageURI(image_uris.get(0));
            userimage= image_uris.get(0).getPath();
            imagePath.add(userimage);
            imageViews.get(0).setImageURI(image_uris.get(0));
            removeIndex.add(views.indexOf(imageViews.get(0)));
           //Picasso.with(getActivity()).load(userimage).fit().into( imageViews.get(0));
            compressFile(userimage);

            //do something
            //"/storage/emulated/0/VeeDaters/VID_20171121_143557.mp4";
        }
        if (requestCode == CAMERA_RQ && resultCode == Activity.RESULT_OK ) {

            isVideoUpdated=true;
             videoFile= new File(data.getData().getPath());
            videoView.setVideoPath(data.getData().getPath());
            videoView.start();
            Bitmap bitmap=      ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(videoFile.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND),getDeviceWidth(),getDeviceWidth());
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            try {
                createFileFromBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PreferenceConnector.getInstance(getActivity()).writeString(KeyValue.USER_VIDEO_LINK,videoFile.getAbsolutePath());

          //  uploadVideo(file);
          //  Toast.makeText(getActivity(), "Size "+fileSize(file), Toast.LENGTH_SHORT).show();
        } /*else if (data != null) {
            Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
            if (e != null) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            //do something
        }*/
        if (requestCode==ADDRESS_GET)
        {
            if (data!=null)
            {
                address.setText("");
                String addr=data.getStringExtra("ADD");
                address.setText(addr!=null? addr:"");
            }

            return;
        }
        switch (requestCode)
        {
            case GENDER:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                                if(result.equalsIgnoreCase("Man"))
                                {
                                    genderImage.setImageResource(R.drawable.masculine);

                                }else if (result.equalsIgnoreCase("Woman"))
                                {
                                    genderImage.setImageResource(R.drawable.femenine);
                                }else if (result.equalsIgnoreCase("Not specified")){
                                    genderImage.setImageResource(R.drawable.undifiine);
                                }
                        usergender.setText(result);
                    }
                }
                break;
            case STATUS:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        status.setText(result);
                    }
                }
                break;
            case NATION:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        nation.setText(result);
                    }
                }
                break;
            case SPORT:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        sportTxt.setText(result);
                    }
                }
                break;
            case INCOME:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        incomeTxt.setText(result);
                    }
                }
                break;
            case STYLE:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        styleTxt.setText(result);
                    }
                }
                break;
            case RELIGION:
                if (data!=null)
                {
                    String result=data.getStringExtra("RESULT");
                    if (result!=null)
                    {
                        religionTxt.setText(result);
                    }
                }
                break;
        }
    }
    private void createFileFromBitmap(Bitmap bitmap1) throws Exception
    {
         thumb = new File(getActivity().getCacheDir(), "thumb");
        thumb.createNewFile();

//Convert bitmap to byte array
        Bitmap bitmap = bitmap1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(thumb);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
    }
    private void uploadVideo(File file,File thumb) {
        if (file!=null && isVideoUpdated)
        {
            Log.e("ThumFile",thumb.getPath());
            isVideoUpdated=false;
            CommonUtility.showProgress(getActivity(),"Uploading Video...");
            RequestBody body=RequestBody.create(MediaType.parse("video/*"),file);
            RequestBody videothumb=RequestBody.create(MediaType.parse("image/*"),thumb);
            MultipartBody.Part part=MultipartBody.Part.createFormData("Videos[video_data]","video.mp4",body);
            MultipartBody.Part thumbPart=MultipartBody.Part.createFormData("Videos[video_thumb]",thumb.getName(),videothumb);
            ApiClient.getClient().uploadVideo(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID),part,thumbPart).enqueue(new Callback<UserLikesResponse>() {
                @Override
                public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response)
                {
                    CommonUtility.hideProgress();
                    UserLikesResponse likesResponse=response.body();
                        if (likesResponse!=null && likesResponse.getIsSuccess())
                        {
                            CommonUtility.successToast(getActivity(),"Video upload successfully");
                        }
                }
                @Override
                public void onFailure(Call<UserLikesResponse> call, Throwable t)
                {
                    CommonUtility.hideProgress();
                }
            });
        }
    }
    @OnClick(R.id.logout)
    public void logOut()
    {
        ApiClient.getClient().logout(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),PreferenceConnector.getInstance(getActivity()).readString(KeyValue.DEVICE_ID),"Android").enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                    if (response.body().getIsSuccess())
                    {
                        try {
                            new AsyncTask<Void,Void,Void>()
                            {
                                @Override
                                protected Void doInBackground(Void... params)
                                {
                                    {
                                        try
                                        {
                                            FirebaseInstanceId.getInstance().deleteInstanceId();
                                        } catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                    return null;
                                }
                                @Override
                                protected void onPostExecute(Void result)
                                {
                                    //call your activity where you want to land after log out
                                }
                            }.execute();
                            PreferenceConnector.getInstance(getActivity()).logout();
                           // Intent intent=new Intent(getActivity(),TokenServices.class);
                           // getActivity().startService(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent=new Intent(getActivity(), LoginDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {

            }
        });
    }

    private void compressFile(String userimage) {
    File file=new File(userimage);
        files.add(file);

    }
    OnCompressListener listener=new OnCompressListener() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(File file) {
           // files.add(file);
        }

        @Override
        public void onError(Throwable e) {

        }
    };
    @OnClick(R.id.genderRoot)
    public void setgender() {
        if (isEdit) {
            getData(showMeGenderArray);
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", GENDER);
            intent.putExtra("PREVIOUS", usergender.getText().toString());
            startActivityForResult(intent, GENDER);
        }
    }
    @OnClick(R.id.statusRoot)
    public void setStatus()
    {
        if (isEdit)
        {
            getData(martialStatus);
            Intent intent=new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA",arrayList);
            intent.putExtra("TYPE",STATUS);
            intent.putExtra("PREVIOUS",status.getText().toString());
            startActivityForResult(intent,STATUS);
        }

    }
    @OnClick(R.id.nationRoot)
   public void setNation() {
        if (isEdit) {
            country.clear();
            String[] countryCodes = Locale.getISOCountries();

            for (String countryCode : countryCodes) {

                Locale obj = new Locale("", countryCode);
                country.add(obj.getDisplayCountry());
            }
            getData(country.toArray(new String[]{}));
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", NATION);
            intent.putExtra("PREVIOUS", nation.getText().toString());
            startActivityForResult(intent, NATION);
        }
    }
    @OnClick(R.id.birthdayRoot)
    public void setDob()
    {
        if (isEdit) {
            final DatePickerPopUpWindow datePicker = new DatePickerPopUpWindow.Builder(getActivity())
                    .setMinYear(1980)
                    .setMaxYear(2550)
                    .setSelectedDate("2013-11-11")
                    .setOnDateSelectedListener(this)
                    .setConfirmButtonText("CONFIRM")
                    .setCancelButtonText("CANCEL")
                    .setConfirmButtonTextColor(Color.parseColor("#999999"))
                    .setCancelButtonTextColor(Color.parseColor("#009900"))
                    .setButtonTextSize(16)
                    .setViewTextSize(16)
                    .setShowDayMonthYear(true)
                    .build();
            datePicker.show(getActivity());
        }
    }
    @OnClick(R.id.religion)
    public void setReligion()
    {
        if (isEdit) {
            getData(religionArray);
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", RELIGION);
            intent.putExtra("PREVIOUS", religionTxt.getText().toString());
            startActivityForResult(intent, RELIGION);
        }
    }
    @OnClick(R.id.sports)
    public void setSports()
    {
        if (isEdit) {
            getData(sportsArray);
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", SPORT);
            intent.putExtra("PREVIOUS", sportTxt.getText().toString());
            startActivityForResult(intent, SPORT);
        }
    }
    @OnClick(R.id.income)
    public void setIncome()
    {
        if (isEdit) {
            getData(incomeArray);
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", INCOME);
            intent.putExtra("PREVIOUS", incomeTxt.getText().toString());
            startActivityForResult(intent, INCOME);
        }
    }
    @OnClick(R.id.style)
    public void setStyle()
    {
        if (isEdit) {
            getData(fashionArray);
            Intent intent = new Intent(getActivity(), DataPicker.class);
            intent.putParcelableArrayListExtra("DATA", arrayList);
            intent.putExtra("TYPE", STYLE);
            intent.putExtra("PREVIOUS", sportTxt.getText().toString());
            startActivityForResult(intent, STYLE);
        }
    }
    private void openDialog(String path)
    {
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        intent.putExtra("VIDEOPATH",videoPath);
        startActivity(intent);
    }
    private ArrayList<PrefData> getData(String [] string)
    {
        arrayList.clear();
        for (int i = 0; i < string.length; i++) {
            arrayList.add(new PrefData(string[i],false));
        }
        return arrayList;
    }
   /* @OnClick(R.id.userNation)
    public void getNation()
    {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                    //userNation.setText(name+"");
                //countryImage.setVisibility(View.VISIBLE);
               // countryImage.setImageResource(flagDrawableResID);
                picker.dismiss();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "Select Nation");
    }*/
    private void removeImage()
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
        if (((String)views.get(views.indexOf(imageViews.get(0))).getTag())!=null && !((String)views.get(views.indexOf(imageViews.get(0))).getTag()).equalsIgnoreCase(""))
        {
            String toServer = aboutUser.getText().toString();
            String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(toServer);
            HashMap<String,RequestBody> hashMap=new HashMap<>();
            hashMap.put("UserMeta[about_me]",RequestBody.create(MediaType.parse("text/plain"),toServerUnicodeEncoded));
            hashMap.put("UserMeta[dob]",RequestBody.create(MediaType.parse("text/plain"),dateTosend));
            hashMap.put("UserMeta[status]",RequestBody.create(MediaType.parse("text/plain"), getData(status)));
            hashMap.put("UserMeta[gender]",RequestBody.create(MediaType.parse("text/plain"), getData(usergender)));
            hashMap.put("UserMeta[nation]",RequestBody.create(MediaType.parse("text/plain"), getData(nation)));
            hashMap.put("UserMeta[religion]",RequestBody.create(MediaType.parse("text/plain"), getData(religionTxt)));
            hashMap.put("UserMeta[sport]",RequestBody.create(MediaType.parse("text/plain"), getData(sportTxt)));
            hashMap.put("UserMeta[style]",RequestBody.create(MediaType.parse("text/plain"), getData(styleTxt)));
            hashMap.put("UserMeta[smoke]",RequestBody.create(MediaType.parse("text/plain"), smoke));
            hashMap.put("UserMeta[alchohol]",RequestBody.create(MediaType.parse("text/plain"),alchohal));
            hashMap.put("User[name]",RequestBody.create(MediaType.parse("text/plain"),getData(userName)));
            hashMap.put("User[address]",RequestBody.create(MediaType.parse("text/plain"),getData(address)));
            hashMap.put("UserMeta[tatoo]",RequestBody.create(MediaType.parse("text/plain"),tatoo));
            hashMap.put("UserMeta[max_income]",RequestBody.create(MediaType.parse("text/plain"),maxPrice));
            hashMap.put("UserMeta[min_income]",RequestBody.create(MediaType.parse("text/plain"),minPrice));
            hashMap.put("User[lat]",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(EditProfile.latitude) ));
            hashMap.put("User[lng]",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(EditProfile.longitude)));

            hashMap.put("User[photo_id][+"+0+"]",RequestBody.create(MediaType.parse("text/plain"),((String)views.get(views.indexOf(imageViews.get(0))).getTag())));
            ApiClient.getClient().upDateUserProfileWithoutImage(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),hashMap).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    if (user!=null)
                    {
                        if (user.is_success())
                        {
                            // TODO: 14/11/17 Goto Dashboard after successful signIn
                            CommonUtility.successToast(getActivity(),"Image deleted"+"");
                            //files.clear();

                        }else {
                            CommonUtility.errorToast(getActivity(),user.getError()+"");
                        }
                    }else {
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }


                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
            });
        }
    }
    private void saveProfileData()
    {
        if (address.getText().toString().isEmpty())
        {
            CommonUtility.inFoToast(getActivity(),"Choose Address");
            return;
        }
        isImageUpdated=false;

        String price=incomeTxt.getText().toString().trim().replace(" ","");
        String minPrice="";
        String maxPrice="";
        if (price!=null && !price.equalsIgnoreCase(""))
        {
            String priceArr[]=price.split("-");
            minPrice=priceArr[0].replace("$","").trim();
            maxPrice=priceArr[1].replace("$","").trim();
        }
        HashMap<String,RequestBody> hashMap=new HashMap<>();
        String toServer = aboutUser.getText().toString();
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(toServer);
        List<MultipartBody.Part> imageMap=new ArrayList<>();
        hashMap.put("UserMeta[about_me]",RequestBody.create(MediaType.parse("text/plain"),toServerUnicodeEncoded));
        hashMap.put("UserMeta[dob]",RequestBody.create(MediaType.parse("text/plain"),dateTosend));
        hashMap.put("UserMeta[status]",RequestBody.create(MediaType.parse("text/plain"), getData(status)));
        hashMap.put("UserMeta[gender]",RequestBody.create(MediaType.parse("text/plain"), getData(usergender)));
        hashMap.put("UserMeta[nation]",RequestBody.create(MediaType.parse("text/plain"), getData(nation)));
        hashMap.put("UserMeta[religion]",RequestBody.create(MediaType.parse("text/plain"), getData(religionTxt)));
        hashMap.put("UserMeta[sport]",RequestBody.create(MediaType.parse("text/plain"), getData(sportTxt)));
        hashMap.put("UserMeta[style]",RequestBody.create(MediaType.parse("text/plain"), getData(styleTxt)));
        hashMap.put("UserMeta[smoke]",RequestBody.create(MediaType.parse("text/plain"), smoke));
        hashMap.put("UserMeta[alchohol]",RequestBody.create(MediaType.parse("text/plain"),alchohal));
        hashMap.put("User[name]",RequestBody.create(MediaType.parse("text/plain"),getData(userName)));
        hashMap.put("User[address]",RequestBody.create(MediaType.parse("text/plain"),getData(address)));
        hashMap.put("UserMeta[tatoo]",RequestBody.create(MediaType.parse("text/plain"),tatoo));
        hashMap.put("UserMeta[max_income]",RequestBody.create(MediaType.parse("text/plain"),maxPrice));
        hashMap.put("UserMeta[min_income]",RequestBody.create(MediaType.parse("text/plain"),minPrice));
        hashMap.put("User[lat]",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(EditProfile.latitude) ));
        hashMap.put("User[lng]",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(EditProfile.longitude)));
        ArrayList<String> deleteId=new ArrayList<>();
        for (int i = 0; i <removeIndex.size() ; i++)
        {
            if (((String) views.get(removeIndex.get(i)).getTag())!=null && !((String) views.get(removeIndex.get(i)).getTag()).equalsIgnoreCase(""))
            {
                deleteId.add((String) views.get(removeIndex.get(i)).getTag());
            }
        }
        if (!files.isEmpty())
        {
            for (int i = 0; i <deleteId.size() ; i++) {
                hashMap.put("User[photo_id][+"+i+"]",RequestBody.create(MediaType.parse("text/plain"),deleteId.get(i)));
            }
            for (int i = 0; i <files.size() ; i++)
            {
                RequestBody reqFile =RequestBody.create(MediaType.parse("image"), files.get(i));
                MultipartBody.Part body= MultipartBody.Part.createFormData("User[user_photo]["+i+"]",files.get(i).getName(),reqFile);
                imageMap.add(body);
            }
        }
        if (files.isEmpty())
        {
            ApiClient.getClient().upDateUserProfileWithoutImage(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),hashMap).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User user=response.body();
                    if (user!=null)
                    {
                        if (user.is_success())
                        {
                            // TODO: 14/11/17 Goto Dashboard after successful signIn
                            CommonUtility.successToast(getActivity(),response.body().getMessage()+"");
                        }else {
                            CommonUtility.errorToast(getActivity(),user.getError()+"");
                        }
                    }else {
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }


                }


                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
            });
        }else
        {
            ApiClient.getClient().upDateUserProfile(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),hashMap,imageMap).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User user=response.body();
                    if (user!=null)
                    {
                        if (user.is_success())
                        {

                            // TODO: 14/11/17 Goto Dashboard after successful signIn
                            CommonUtility.successToast(getActivity(),response.body().getMessage()+"");
                            files.clear();

                        }else {
                            CommonUtility.errorToast(getActivity(),user.getError()+"");
                        }
                    }else {
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }


                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
            });
        }

       //
    }
    private String getData(EditText editText)
    {
        if (editText.getText().toString().trim().isEmpty() ||editText.getText().toString().trim().equalsIgnoreCase(" ") ||editText.getText().toString().trim().equalsIgnoreCase(""))
        {
            return "";
        }else {
            return editText.getText().toString().trim().replace("\"","");
        }

    }
    private String getData(TextView editText)
    {
        if (editText.getText().toString().trim().isEmpty() ||editText.getText().toString().trim().equalsIgnoreCase(" ") ||editText.getText().toString().trim().equalsIgnoreCase(""))
        {
            return "";
        }else {
            return editText.getText().toString().trim().replace("\"","");
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    public void onResume() {
        super.onResume();
        if (videoView!=null)
        {
            videoView.start();
        }
       // ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).profileImage.setImageResource(R.drawable.profile_select);
        ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).profileText.setTextColor(getResources().getColor(R.color.pink));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SEEK_POSITION_KEY", mSeekPosition);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null)
        mSeekPosition = savedInstanceState.getInt("SEEK_POSITION_KEY");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.isFull) {
            videoView.setFullscreen(false);
        }
        if (videoView != null && videoView.isPlaying()) {
            mSeekPosition = videoView.getCurrentPosition();

            videoView.pause();
        }
       // ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
        ((Dashboard)getActivity()).profileImage.setImageResource(R.drawable.profile);
        ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
        ((Dashboard)getActivity()).profileText.setTextColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void onDateSelected(int i, int i1, int i2) {
        birthday.setText(String.valueOf(i)+"-"+String.valueOf(i1)+"-"+String.valueOf(i2));
        dateTosend=birthday.getText().toString();
        setDob(birthday.getText().toString());
        calculateAge(i,i1,i2);
    }
    private void calculateAge(int y,int m,int d)
    {
        Calendar dateOfYourBirth = new GregorianCalendar(y,m,d);
        Calendar today = Calendar.getInstance();
        int yourAge = today.get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR);
        dateOfYourBirth.add(Calendar.YEAR, yourAge);
        if (today.before(dateOfYourBirth)) {
            yourAge--;
        }
        age.setText(yourAge+"");
    }
}
