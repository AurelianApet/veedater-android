package digimantra.veedaters.Dashboard;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.marcinmoskala.videoplayview.VideoPlayView;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import digimantra.veedaters.R;


/**
 * Created by dmlabs on 21/12/17.
 */

public class PlayListDialog extends android.support.v4.app.DialogFragment {
    public static String dataToStore;
    private int section;
    public static PlayListDialog newInstance(String data) {
        dataToStore=data;
        return new PlayListDialog();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fullvideo_view,container,false);
        initView(view);
        //getActivity().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View view)
    {
        com.universalvideoview.UniversalVideoView  universalVideoView=(UniversalVideoView)view.findViewById(R.id.videoView);
        UniversalMediaController media_controller=(UniversalMediaController)view.findViewById(R.id.media_controller);
        universalVideoView.setMediaController(media_controller);
        universalVideoView.setVideoPath(dataToStore);
        universalVideoView.start();
    }

}
