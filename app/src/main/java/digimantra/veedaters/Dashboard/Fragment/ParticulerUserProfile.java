package digimantra.veedaters.Dashboard.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.baoyz.actionsheet.ActionSheet;
import com.bigkoo.pickerview.MyOptionsPickerView;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.squareup.picasso.Picasso;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.MessageActivity;
import digimantra.veedaters.Dashboard.ParticulerUserData;
import digimantra.veedaters.Dashboard.PlanListActivity;
import digimantra.veedaters.Dashboard.PlayListDialog;
import digimantra.veedaters.Dashboard.PlayerActivity;
import digimantra.veedaters.Dashboard.ViewImages;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.Model.UserProfile;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.BlcokListener;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 20/12/17.
 */

public class ParticulerUserProfile extends Fragment implements ActionSheet.ActionSheetListener {

    @BindView(R.id.aboutUser)
    EditText aboutUser;
    @BindView(R.id.address)
    EditText address;
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
    TextView alchohalSwitch;
    @BindView(R.id.tattoSwitch)
    TextView tattoSwitch;
    @BindView(R.id.smokeSwitch)
    TextView smokeSwitch;

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
    @BindView(R.id.genderImage)
    ImageView genderImage;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    BlcokListener listener;
    boolean isEdit=false;
    ArrayList<ImageView> imageViews=new ArrayList<>();
    ArrayList<ImageView> views=new ArrayList<>();
    ArrayList<String> imagePath=new ArrayList<>();
    ArrayList<File> files=new ArrayList<>();
    private String userimage;
    private boolean isVideoUpdated;
    private ArrayList<String> gender=new ArrayList<>();
    private ArrayList<String> statusList=new ArrayList<>();
    private ArrayList<String> smokeList=new ArrayList<>();
    private ArrayList<String> imagesPath=new ArrayList<>();
    private UserProfile profile;
    ArrayList<String> options=new ArrayList<>();
        boolean isLike;
    String userID;
    @BindView(R.id.clickView)
    View clickView;
    private int mSeekPosition;
    @BindView(R.id.videoView)
    UniversalVideoView videoView;
  /*  @BindView(R.id.media_controller)
    UniversalMediaController media_controller;*/
    @BindView(R.id.videoLayout)
    LinearLayout mVideoLayout;
    private boolean isFull;
    private int cachedHeight;
    @BindView(R.id.progress)
    ProgressBar progress;
    boolean isScroll=true;
    String videoPath;
    @BindView(R.id.chatWith)
    ImageView chatWith;
    @BindView(R.id.email)
    TextView email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.singleuser_profile,container,false);
        ButterKnife.bind(this,view);
        setImageHeight();
        makeAllDisable();
        userID=getArguments().getString("USERID");
       /* if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION))
        {
            addVideo.setVisibility(View.GONE);
            clickView.setVisibility(View.VISIBLE);
        }else {
            addVideo.setVisibility(View.VISIBLE);
            clickView.setVisibility(View.GONE);
        }*/
        getData();
        gender.clear();
        statusList.clear();
        smokeList.clear();
        gender.add("male");
        gender.add("female");
        smokeList.add("yes");
        smokeList.add("no");
        statusList.add("single");
        statusList.add("married");
       // videoView.setMediaController(media_controller);
      //  media_controller.setOnLoadingView(R.layout.loading);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });

        videoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {

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
      /*  ((ParticulerUserData)getActivity()).block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockUser();
            }
        });*/
        ((ParticulerUserData)getActivity()).chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        return view;
    }
    @OnClick(R.id.fourthImage)
    public void onFourthclick()
    { if (imagesPath.size()>3) {
        Intent intent = new Intent(getActivity(), ViewImages.class);
        intent.putStringArrayListExtra("IMAGES", imagesPath);
        intent.putExtra("INDEX",3);
        startActivity(intent);
    }
    }
    @OnClick(R.id.thirdImage)
    public void thirdImageclick()
    {
        if (imagesPath.size()>2) {
            Intent intent = new Intent(getActivity(), ViewImages.class);
            intent.putStringArrayListExtra("IMAGES", imagesPath);
            intent.putExtra("INDEX",2);
            startActivity(intent);
        }
    }
    @OnClick(R.id.secondImage)
    public void secondImageclick()
    {
        if (imagesPath.size()>1) {
            Intent intent = new Intent(getActivity(), ViewImages.class);
            intent.putStringArrayListExtra("IMAGES", imagesPath);
            intent.putExtra("INDEX",1);
            startActivity(intent);
        }
    }
    @OnClick(R.id.firstImage)
    public void firstImageclick()
    {
        if (imagesPath.size()>0) {
            Intent intent = new Intent(getActivity(), ViewImages.class);
            intent.putStringArrayListExtra("IMAGES", imagesPath);
            intent.putExtra("INDEX",0);
            startActivity(intent);
        }
    }
    @OnClick(R.id.chatWith)
    public void chatWith()
    {
        chatWithUser();
    }
    @OnClick(R.id.clickView)
    public void viewFull()
    {
        if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION))
        {
            //openDialog(videoPath);
            Intent intent=new Intent(getActivity(), PlayerActivity.class);
            intent.putExtra("VIDEOPATH",videoPath);
            startActivity(intent);

                   // ((ParticulerUserData)getActivity()).bottomTab.setVisibility(View.GONE);
          //  anInterface.playFragment(PlayerFragment.getInstance(videoPath));

        }

    }
    @OnClick(R.id.addVideo)
    public void subscribeVideo()
    {
        if (!PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION))
        {
            openDialog(R.layout.prmium_layout);
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
    private void setVideoAreaSize()
    {

        mVideoLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
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
    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = ((ParticulerUserData)getActivity()).actionBar;
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
                ((ParticulerUserData)getActivity()).bottomTab.setVisibility(View.VISIBLE);
            } else {
                supportActionBar.hide();
                ((ParticulerUserData)getActivity()).bottomTab.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollView.setSmoothScrollingEnabled(false);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            scrollView.setSmoothScrollingEnabled(true);
        }
    }
    @OnClick(R.id.imageRoot)
    public void fullImage()
    {

    }
    private void likeUser()
    {
        final int flag;
        if (isLike)
        {
            flag=0;
        }else {
            flag=1;
        }
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().likeUser(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID), Integer.parseInt(userID),flag).enqueue(new Callback<UserLikesResponse>() {
            @Override
            public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
                CommonUtility.hideProgress();
                if (flag==0)
                {
                    isLike=false;
                    listener.likeResponse(isLike);
                }else {
                    isLike=true;
                    listener.likeResponse(isLike);
                }
            }

            @Override
            public void onFailure(Call<UserLikesResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }

    private void openDialog()
    {
        options.clear();
            if (isLike)
            {
                options.add("Unfavourite");
                options.add("Block");
            }else {
                options.add("Favourite");
                options.add("Block");
            }
        ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(options.toArray(new String[]{}))
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    private void chatWithUser()
    {
        if (!PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION))
        {
            openDialog(R.layout.prmium_layout);
        }else {

            Intent intent=new Intent(getActivity(), MessageActivity.class);
            intent.putExtra(KeyValue.RECEIVER_ID,userID);
            startActivity(intent);
        }

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener=(ParticulerUserData)context;

    }

    private void blockUser()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().blockUser(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),userID).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                CommonUtility.hideProgress();
                BlockResponse blockResponse=response.body();
                if (blockResponse!=null ) {
                    if (blockResponse.getIsSuccess()) {
                        CommonUtility.successToast(getActivity(), blockResponse.getMessage() + "");
                        listener.blockUser("Yes");

                    } else {
                        CommonUtility.successToast(getActivity(), blockResponse.getError() + "");
                    }
                }
            }
            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t)
            {
                CommonUtility.hideProgress();
            }
        });
    }
    private void openDialog(String path)
    {
        if (path!=null && !path.equalsIgnoreCase(""))
        {
            PlayListDialog dialog=PlayListDialog.newInstance(path);
            dialog.show(getFragmentManager(),"Video");
        }

    }
    private void getData()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().getUserData(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),userID).enqueue(new Callback<UserProfile>() {
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
                        CommonUtility.errorToast(getActivity(),"Something wend wrong...");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
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
                videoPath=ApiClient.IMG_BASE+profile.getUser().getUserVideo().getVideoUrl();
                videoView.setVideoPath(videoPath);

            if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION))
            {
                videoView.seekTo(mSeekPosition);
                videoView.start();
            }else {
                progress.setVisibility(View.GONE);
            }
              //  picassoVideoView.setState(VideoPlayView.State.Initial.INSTANCE);
           // picassoVideoView.setAutoplay(true);
          /*  videocontroller1.mRetryBtn.setVisibility(View.GONE);
            // videocontroller1.replayTextView.setText("Play Again!");
            videocontroller1.batteryLevel.setVisibility(View.GONE);
            videocontroller1.backButton.setVisibility(View.GONE);
            videocontroller1.setState(JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            videocontroller1.setUp(videoPath,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"Veedaters");
            Picasso.with(getActivity())
                    .load(R.drawable.place_holder)
                    .into(videocontroller1.thumbImageView);*/

        }else {
            progress.setVisibility(View.GONE);
            //media_controller.setEnabled(false);
          /*  videocontroller1.mRetryBtn.setVisibility(View.GONE);
            // videocontroller1.replayTextView.setText("Play Again!");
            videocontroller1.batteryLevel.setVisibility(View.GONE);
            videocontroller1.backButton.setVisibility(View.GONE);*/
        }
        if (meta!=null)
        {
            if (meta.getAlchohol()!=null && meta.getAlchohol().equalsIgnoreCase("yes"))
            {
                alchohalSwitch.setText("Yes");
            }else {
                alchohalSwitch.setText("No");
            }
            if (meta.getSmoke()!=null && meta.getSmoke().equalsIgnoreCase("yes"))
            {
                smokeSwitch.setText("Yes");
            }else {
                smokeSwitch.setText("No");
            }
            if (meta.getTatoo()!=null && meta.getTatoo().equalsIgnoreCase("yes"))
            {
                tattoSwitch.setText("Yes");
            }else {
                tattoSwitch.setText("No");
            }
            if (meta.getAbout()!=null && !meta.getAbout().equalsIgnoreCase(""))
            {
                String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(meta.getAbout());
                aboutUser.setText(fromServerUnicodeDecoded);
               // aboutUser.setText(meta.getAbout());
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
                birthday.setText(meta.getDob());
            }
            if (meta.getLike()==0)
            {
                isLike=false;


            }else {
                isLike=true;

            }
        }
        if (user!=null)
        {
            if (user.getUsername()!=null)
            {
                userName.setText(user.getUsername()!=null? user.getUsername():user.getName()+"");
            }
            age.setText(user.getUserMeta().getAge()!=null? user.getUserMeta().getAge(): "NA");
            if (user.getUserMeta().getGender()!=null)
            {
                if (user.getUserMeta().getGender().equalsIgnoreCase("Man"))
                {
                    genderImage.setImageResource(R.drawable.masculine);
                }else if (user.getUserMeta().getGender().equalsIgnoreCase("Woman"))
                {
                    genderImage.setImageResource(R.drawable.femenine);
                }
            }
        }
        if (photo!=null)
        {

            for (int i = 0; i <photo.size() ; i++) {

                showImages(i,photo.get(i).getPhotoPath());
            }

        }
    }
    private void showImages( int i ,String photoPath)
    {
        if (i<=3)
        {
            imagesPath.add(ApiClient.IMG_BASE+photoPath);
            Picasso.with(getActivity()).load(ApiClient.IMG_BASE+photoPath).fit()
                    .placeholder(R.drawable.place_holder).into(views.get(i));
        }
    }

    public static ParticulerUserProfile getInstance(String id)
    {
        ParticulerUserProfile particulerUserProfile=new ParticulerUserProfile();
        Bundle  bundle=new Bundle();
        bundle.putString("USERID",id);
        particulerUserProfile.setArguments(bundle);
        return particulerUserProfile;
    }
    private void setImageHeight()
    {
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
    private int getDeviceWidth()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
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

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView!=null)
        {
            if (PreferenceConnector.getInstance(getActivity()).readBoolean(KeyValue.HAS_SUBSCRIPTION)) {
                videoView.start();
                addVideo.setVisibility(View.GONE);
                clickView.setVisibility(View.VISIBLE);
            }else {
                addVideo.setVisibility(View.VISIBLE);
                clickView.setVisibility(View.GONE);
            }
        }
    }

    private void makeAllDisable()
    {

    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel)
    {

    }
    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index)
    {
        switch (index)
        {
            case 0:
                likeUser();
                break;
            case 1:
                blockUser();
                break;
            case 2:
                chatWithUser();
                break;
            case 3:
                break;
        }
    }
}
