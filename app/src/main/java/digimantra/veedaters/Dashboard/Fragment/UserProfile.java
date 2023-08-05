/*
package digimantra.veedaters.Dashboard.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.VideoCapture;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*
 * Created by dmlabs on 7/11/17.



public class UserProfile extends Fragment {
    DashboardInteractor interactor;
    @BindView(R.id.mainImage)
    public ImageView mainImage;
    @BindView(R.id.userImage)
    public ImageView userImage;
    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.userBeer)
    TextView userBeer;
    @BindView(R.id.userSmoke)
    TextView userSmoke;
    @BindView(R.id.userStyle)
    TextView userStyle;
    @BindView(R.id.userIncome)
    TextView userIncome;
    @BindView(R.id.userTravel)
    TextView userTravel;
    @BindView(R.id.userSport)
    TextView userSport;
    @BindView(R.id.userReligion)
    TextView userReligion;
    @BindView(R.id.userNation)
    TextView userNation;
    @BindView(R.id.userGender)
    TextView userGender;
    @BindView(R.id.userDob)
    TextView userDob;
    @BindView(R.id.userAddress)
    TextView userAddress;
    @BindView(R.id.userStatus)
    TextView userStatus;
    @BindView(R.id.userAbout)
    TextView userAbout;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.userAge)
    TextView userAge;
    @BindView(R.id.gender)
    ImageView gender;
    digimantra.veedaters.Model.UserProfile profile;
    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).profileImage.setImageResource(R.drawable.profile_select);
        ((Dashboard)getActivity()).edit.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).profileText.setTextColor(getResources().getColor(R.color.pink));
        getProfileData();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((Dashboard)getActivity()).profileImage.setImageResource(R.drawable.profile);
        ((Dashboard)getActivity()).edit.setVisibility(View.GONE);
        ((Dashboard)getActivity()).profileText.setTextColor(getResources().getColor(R.color.gray));
    }
    private void getProfileData()
    {
        Log.e("profileCheck",PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)+"");
        CommonUtility.showProgress(getActivity(),"Please Profilewait...");
        ApiClient.getClient().getProfileData(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID))).enqueue(new Callback<digimantra.veedaters.Model.UserProfile>() {
            @Override
            public void onResponse(Call<digimantra.veedaters.Model.UserProfile> call, Response<digimantra.veedaters.Model.UserProfile> response) {
                profile=response.body();
                if (profile!=null)
                {
                    if (profile.is_success())
                    {
                        setData(profile);
                    }else {
                        CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                    }
                }else{
                    CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
                }
                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<digimantra.veedaters.Model.UserProfile> call, Throwable t) {
                CommonUtility.hideProgress();
                CommonUtility.confusingToast(getActivity(),"Something went wrong!!!");
            }
        });
    }
private void setData(digimantra.veedaters.Model.UserProfile profile)
    {
    digimantra.veedaters.Model.UserProfile.Profile.UserProfileMeta meta= profile.getProfile().getProfileMeta();
        digimantra.veedaters.Model.UserProfile.Profile userprofile=profile.getProfile();
        if (meta!=null)
        {
            userBeer.setText(meta.getBeer()!=null? meta.getBeer().replace("\"",""):"NA");
            userSmoke.setText(meta.getSmoke()!=null? meta.getSmoke().replace("\"",""):"NA");
            userStyle.setText(meta.getStyle()!=null? meta.getStyle().replace("\"",""):"NA");
            userIncome.setText(meta.getIncome()!=null ? meta.getIncome().replace("\"",""):"NA" );
            userTravel.setText(meta.getTravel()!=null ? meta.getTravel().replace("\"",""):"NA");
            userSport.setText(meta.getSport()!=null ? meta.getSport().replace("\"",""):"NA");
            userReligion.setText(meta.getReligion()!=null ? meta.getReligion().replace("\"",""):"NA");
            userNation.setText(meta.getNation()!=null ? meta.getNation().replace("\"",""):"NA");
            userGender.setText(meta.getGender()!=null ? meta.getGender().replace("\"",""):"NA");
            userDob.setText(meta.getDob()!=null ? meta.getDob().replace("\"",""):"NA");

            userStatus.setText(meta.getStatus()!=null ? meta.getStatus().replace("\"",""):"NA");
            userAbout.setText(meta.getAboutMe()!=null ? meta.getAboutMe().replace("\"",""):"NA");
        }
         if (userprofile!=null)
         {
            userName.setText(userprofile.getName()!=null ? userprofile.getName().replace("\"",""):"NA");
             userAddress.setText(userprofile.getAddress()!=null ? userprofile.getAddress().replace("\"",""):"NA");
         }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Profile");
        BitmapDrawable drawable = (BitmapDrawable) mainImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        Bitmap blurred = blurRenderScript(bitmap, 25);//second parametre is radius
        mainImage.setImageBitmap(blurred);
        ((Dashboard)getActivity()).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putParcelable("USERDATA",profile);
             EditProfile editProfile=   new EditProfile();
                editProfile .setArguments(bundle);
                ((Dashboard)getActivity()).processFragment(editProfile);
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(getActivity());

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    @OnClick(R.id.userImage)
    public void onUserimageClick()
    {
        startActivity(new Intent(getActivity(), VideoCapture.class));
    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}
*/
