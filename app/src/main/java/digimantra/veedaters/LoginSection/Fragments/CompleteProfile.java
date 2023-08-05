package digimantra.veedaters.LoginSection.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 8/1/18.
 */

public class CompleteProfile extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.complete_profile,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
