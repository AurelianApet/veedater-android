package digimantra.veedaters.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.StackView;

import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Adapter.ImageDetailAdapter;
import digimantra.veedaters.Adapter.StackAdapter;
import digimantra.veedaters.R;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;

public class ViewImages extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
  /*  @BindView(R.id.stackView)
    StackView stackView;*/
    int index;
    ArrayList<String> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("IMAGES"))
        {
            arrayList=getIntent().getStringArrayListExtra("IMAGES");
            index=getIntent().getIntExtra("INDEX",0);
        }
       /* StackAdapter adapt = new StackAdapter(this, R.layout.detail_adapter_layout, arrayList);
        stackView.setAdapter(adapt);
        stackView.setHorizontalScrollBarEnabled(true);
        stackView.setBackgroundColor(Color.rgb(230, 255, 255));*/
        ImageDetailAdapter imageAdapter=new ImageDetailAdapter(getSupportFragmentManager(),arrayList,this);
        pager.setAdapter(imageAdapter);
        pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        pager.setClipToPadding(false);
        pager.setPageMargin(5);
        pager.setCurrentItem(index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(ViewImages.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(ViewImages.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(ViewImages.this,PasscodeActivity.class);
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
}
