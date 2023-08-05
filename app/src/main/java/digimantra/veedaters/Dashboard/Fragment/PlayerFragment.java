package digimantra.veedaters.Dashboard.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 11/1/18.
 */

public class PlayerFragment extends Fragment {
    @BindView(R.id.videoView)
    UniversalVideoView videoView;
      @BindView(R.id.media_controller)
      UniversalMediaController media_controller;
  public static   String videopath;
    public static PlayerFragment getInstance(String path)
    {
        videopath=path;
        PlayerFragment fragment=new PlayerFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fullvideo_view,container,false);
        ButterKnife.bind(this,view);
        videoView.setMediaController(media_controller);
        videoView.setVideoPath(videopath);
        videoView.start();
        return view;
    }

}
