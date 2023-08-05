package digimantra.veedaters.Dashboard;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Fragment.DiscoveryFragment;
import digimantra.veedaters.Dashboard.Fragment.EditProfile;
import digimantra.veedaters.Dashboard.Fragment.NearByFragment;
import digimantra.veedaters.Dashboard.Fragment.ParticulerUserProfile;
import digimantra.veedaters.Dashboard.Fragment.PlayerInterface;
import digimantra.veedaters.Dashboard.Fragment.Settings;
import digimantra.veedaters.Dashboard.Fragment.UserListFragment;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.BlcokListener;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class ParticulerUserData extends AppCompatActivity implements BlcokListener,PlayerInterface {

    @BindView(R.id.discovery)
    public LinearLayout discovery;
    @BindView(R.id.discoveryImage)
    public ImageView discoveryImage;
    @BindView(R.id.discoveryText)
    public TextView discoveryText;
    @BindView(R.id.favorites)
    public LinearLayout favorites;
    @BindView(R.id.favoritesText)
    public TextView favoritesText;
    @BindView(R.id.favoritesImage)
    public ImageView favoritesImage;
    @BindView(R.id.nearBy)
    public LinearLayout nearBy;
    @BindView(R.id.nearByText)
    public TextView nearByText;
    @BindView(R.id.nearByImage)
    public ImageView nearByImage;
    @BindView(R.id.profile)
    public LinearLayout profile;
    @BindView(R.id.profileText)
    public TextView profileText;
    @BindView(R.id.profileImage)
    public ImageView profileImage;
    @BindView(R.id.messageImage)
    public ImageView messageImage;
    @BindView(R.id.messageText)
    public TextView messageText;
    @BindView(R.id.message)
    public LinearLayout message;
    @BindView(R.id.bottomTab)
    public LinearLayout bottomTab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
   /* @BindView(R.id.block)
   public TextView block;*/
    @Nullable
    @BindView(R.id.backButton)
    public ImageView backButton;
    @Nullable
    @BindView(R.id.edit)
    public TextView edit;
    @BindView(R.id.chat)
    public ImageView chat;
    String userId;
  public   ActionBar actionBar;
    int position;
    boolean isLike;
    boolean isBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particuler_user_data);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if (getIntent().hasExtra(KeyValue.RECEIVER_ID))
        {
            userId=getIntent().getStringExtra(KeyValue.RECEIVER_ID);
            position=getIntent().getIntExtra(KeyValue.POSITION,0);
            if (getIntent().hasExtra(KeyValue.STATE))
                isLike=getIntent().getBooleanExtra(KeyValue.STATE,false);
        }
        processFragment(ParticulerUserProfile.getInstance(userId));
       nearByImage.setImageResource(R.drawable.map_select);
        nearByText.setTextColor(getResources().getColor(R.color.pink));
    }
    public void processFragment(Fragment fragment)
    {
        String tag=fragment.getClass().getSimpleName();
        FragmentManager manager=getSupportFragmentManager();
        Fragment fragment1=manager.findFragmentByTag(tag);
        if (fragment1==null)
        {
            FragmentTransaction transaction= manager.beginTransaction();
            transaction .replace(R.id.mainContainer2,fragment,fragment.getClass().getSimpleName());
            transaction .addToBackStack(fragment.getClass().getSimpleName()).commit();

        }else
        {
            manager.popBackStack(tag,0);
        }
    }

    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        if (getSupportFragmentManager().getBackStackEntryCount()==1)
        {
            setResult();
            finish();
        }else
        {
            super.onBackPressed();
        }
    }
    @OnClick(R.id.discovery)
    public void onDiscoveryclick()
    {
        Intent intent=new Intent(this,Dashboard.class);
        intent.putExtra("KEY","discovery");
        startActivity(intent);
        finish();
        /*processFragment(new DiscoveryFragment());*/
    }
    @OnClick(R.id.favorites)
    public void onFavoriteClick()
    {
        Intent intent=new Intent(this,Dashboard.class);
        intent.putExtra("KEY","setting");
        startActivity(intent);
        finish();

    }
    @OnClick(R.id.profile)
    public void onProfileCllick()
    {
        Intent intent=new Intent(this,Dashboard.class);
        intent.putExtra("KEY","profile");
        startActivity(intent);
        finish();

    }
    @OnClick(R.id.nearBy)
    public void onNearByCllick()
    {
        Intent intent=new Intent(this,Dashboard.class);
        intent.putExtra("KEY","nearby");
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.message)
    public void onMessageClick()
    {
        Intent intent=new Intent(this,Dashboard.class);
        intent.putExtra("KEY","message");
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            setResult();
           finish();
        } else
        {
            super.onBackPressed();
        }
    }
    private void setResult()
    {
        Intent intent=new Intent();
        intent.putExtra("POS",position);
        intent.putExtra("Like",isLike);
        intent.putExtra("Block",isBlock);
        setResult(2000,intent);
    }
    @Override
    public void blockUser(String userId)
    {
        if (userId.equalsIgnoreCase("Yes"))
        {
            isBlock=true;
        }

            //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(ParticulerUserData.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(ParticulerUserData.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(ParticulerUserData.this,PasscodeActivity.class);
                    startActivity(intent);

                }

            }
        }
        myApp.stopActivityTransitionTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((VeeDatersApp)this.getApplication()).startActivityTransitionTimer();
    }

    @Override
    public void likeResponse(boolean flag) {
        isLike=flag;
    }

    @Override
    public void playFragment(Fragment fragment) {
        processFragment(fragment);
    }
}
