package digimantra.veedaters.Dashboard;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.R;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.videoView)
    UniversalVideoView videoView;
    @BindView(R.id.media_controller)
    UniversalMediaController media_controller;
    public static   String videopath;
    @BindView(R.id.close)
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("VIDEOPATH"))
        {
            videopath=getIntent().getStringExtra("VIDEOPATH");
        }
        videoView.setMediaController(media_controller);
        media_controller.setOnLoadingView(R.layout.loading);
        videoView.setVideoPath(videopath);
        videoView.start();
    }
    @OnClick(R.id.close)
    public void onClose()
    {
        finish();
    }
}
