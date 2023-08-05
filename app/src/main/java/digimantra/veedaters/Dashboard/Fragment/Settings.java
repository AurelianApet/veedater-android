package digimantra.veedaters.Dashboard.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.FavoritesActivity;
import digimantra.veedaters.R;

/**
 * Created by dmlabs on 1/12/17.
 */
public class Settings extends android.support.v4.app.Fragment
{
    @BindView(R.id.blockList)
    TextView blockList;
    @BindView(R.id.favorites)
    TextView favorites;
    @BindView(R.id.preference)
    TextView preference;
    @BindView(R.id.changePassword)
    TextView changePassword;
    @BindView(R.id.manageAccount)
    RelativeLayout manageAccount;
    DashboardInteractor interactor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Settings");
        return view;
    }
    @OnClick(R.id.manageAccount)
    public void manageAccount()
    {
        ((Dashboard)getActivity()).processFragment(new ManageAccount());
    }
    @OnClick(R.id.preference)
    public void setPreference()
    {
        ((Dashboard)getActivity()).processFragment(new PreferencesFragment());
    }
    @OnClick(R.id.blockList)
    public void onBlockClick()
    {
        startActivity(new Intent(getActivity(), FavoritesActivity.class));
    }
    @OnClick(R.id.favorites)
    public void onFavoritesClick()
    {
        ((Dashboard)getActivity()).processFragment(new FavoritesFragment());
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @OnClick(R.id.changePassword)
    public void changePassword()
    {
        ((Dashboard)getActivity()).processFragment(new ChangePassword());
    }
    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
    }
    @Override
    public void onPause() {
        super.onPause();
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
    }
}
