package digimantra.veedaters.Dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.hockeyapp.android.CrashManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Fragment.DashboardInteractor;
import digimantra.veedaters.Dashboard.Fragment.DiscoveryFragment;
import digimantra.veedaters.Dashboard.Fragment.EditProfile;
import digimantra.veedaters.Dashboard.Fragment.FavoritesFragment;
import digimantra.veedaters.Dashboard.Fragment.ManageAccount;
import digimantra.veedaters.Dashboard.Fragment.NearByFragment;
import digimantra.veedaters.Dashboard.Fragment.Settings;
import digimantra.veedaters.Dashboard.Fragment.UserListFragment;
import digimantra.veedaters.Dashboard.Fragment.WithoutSubscription;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PermissionUtil;
import digimantra.veedaters.utility.PreferenceConnector;

public class Dashboard extends AppCompatActivity implements DashboardInteractor,PermissionUtil.PermissionResultCallback{

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
    @Nullable
    @BindView(R.id.backButton)
    public ImageView backButton;
    @BindView(R.id.filter)
    public ImageView filter;
    private static final int PERMISSION_RQ = 84;
    @Nullable
    @BindView(R.id.edit)
    public TextView edit;
    public ActionBar actionBar;
   public static boolean isPermissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if (getIntent().hasExtra("KEY"))
        {
            String s=getIntent().getStringExtra("KEY");
            check(s);
        }else  {
            /*if (PreferenceConnector.getInstance(this).readBoolean(KeyValue.HAS_SUBSCRIPTION))
            {
                processFragment(new NearByFragment());
            }else {
                processFragment(new WithoutSubscription());
            }*/
            processFragment(new NearByFragment());
        }
    }

    private void check(String s)
    {
        if (s.equalsIgnoreCase("discovery"))
        {
            onDiscoveryclick();
            return;
        }
        if (s.equalsIgnoreCase("setting"))
        {
            onFavoriteClick();
            return;
        }
        if (s.equalsIgnoreCase("profile"))
        {
            onProfileCllick();
            return;
        }
        if (s.equalsIgnoreCase("nearby"))
        {
            onNearByCllick();
            return;
        }
        if (s.equalsIgnoreCase("message"))
        {
            onMessageClick();
            return;
        }

    }

    @OnClick(R.id.discovery)
    public void onDiscoveryclick()
    {
        processFragment(new DiscoveryFragment());
    }
    @OnClick(R.id.favorites)
    public void onFavoriteClick()
    {
        processFragment(new Settings());
    }
    @OnClick(R.id.profile)
    public void onProfileCllick()
    {
        processFragment(new EditProfile());
    }
    @OnClick(R.id.nearBy)
    public void onNearByCllick()
    {
        processFragment(new NearByFragment());
    }
    @OnClick(R.id.message)
    public void onMessageClick()
    {
        processFragment(new UserListFragment());
    }
    public void processFragment(Fragment fragment)
    {
        String tag=fragment.getClass().getSimpleName();
        FragmentManager manager=getSupportFragmentManager();
        Fragment fragment1=manager.findFragmentByTag(tag);
        if (fragment1==null)
        {
            FragmentTransaction transaction= manager.beginTransaction();
            transaction .replace(R.id.mainContainer,fragment,fragment.getClass().getSimpleName());
            transaction .addToBackStack(fragment.getClass().getSimpleName()).commit();

        }else
            {
            manager.popBackStack(tag,0);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        CrashManager.register(this,getResources().getString(R.string.HockeyAppID));
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {

            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(Dashboard.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(Dashboard.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(Dashboard.this,PasscodeActivity.class);
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
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else
        {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        if (getSupportFragmentManager().getBackStackEntryCount()==1)
        {
            finish();
        }else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> list=getSupportFragmentManager().getFragments();
        for (int i = 0; i <list.size() ; i++) {
            Fragment fragment=list.get(i);
            if (fragment!=null)
            {
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }
    @Override
    public void setTitle(String title)
    {
        this.title.setText(title);
    }

    @Override
    public void PermissionGranted(int request_code) {
        isPermissionGranted=true;
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
