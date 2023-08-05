package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Adapter.FavoriteAdapter;
import digimantra.veedaters.Adapter.UserAdapter;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.FilterActivity;
import digimantra.veedaters.Dashboard.ParticulerUserData;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 6/11/17.
 */

public class FavoritesFragment extends Fragment {
    ArrayList<UserData.User> arrayList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    DashboardInteractor interactor;
    FavoriteAdapter adapter;
    @BindView(R.id.nothingFound)
    TextView nothingFound;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.favourite_layout,container,false);
        ButterKnife.bind(this,view);
        getFavouriteList();
        interactor.setTitle("Favorites");



        return view;
    }
    private void getFavouriteList()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().favouriteUser(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID))).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                CommonUtility.hideProgress();
                UserData data=response.body();
                if (data!=null && data.getIsSuccess())
                {
                    setDataToList(data);
                }else {
                    emptyData();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                CommonUtility.hideProgress();
                emptyData();

            }
        });
    }

    private void setDataToList(final UserData data) {
        if (data.getUser()!=null && !data.getUser().isEmpty()) {


            arrayList = (ArrayList<UserData.User>) data.getUser();
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            adapter = new FavoriteAdapter(arrayList, getActivity(), getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            adapter.setOnItemClick(new FavoriteAdapter.OnItemClick() {
                @Override
                public void onClick(int position) {
                    Intent intent=new Intent(getActivity(),ParticulerUserData.class);
                    intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(position).getId());
                    intent.putExtra(KeyValue.POSITION,position);
                    startActivityForResult(intent,1001);
                }
            });
        }else {
            emptyData();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1001:
                if (data!=null)
                {
                    boolean islike=data.getBooleanExtra("Like",false);
                    boolean isblock=data.getBooleanExtra("Block",false);
                    int  pos=data.getIntExtra("POS",0);
                    if (islike)
                    {
                        if (arrayList.get(pos).getUseMeta()!=null)
                        arrayList.get(pos).getUseMeta().setLike(1);
                    }else {
                        if (arrayList.get(pos).getUseMeta()!=null)
                        arrayList.get(pos).getUseMeta().setLike(0);
                        arrayList.remove(pos);
                    }
                    if (isblock)
                    {
                        arrayList.remove(pos);
                    }
                    if (arrayList.isEmpty())
                    {
                        emptyData();
                    }
                    adapter.notifyItemChanged(pos);
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            inflater.inflate(R.menu.search_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_view:
                startActivity(new Intent(getActivity(), FilterActivity.class));
               *//* CharacterPickerWindow mOptions = new CharacterPickerWindow(getActivity());
                ArrayList<String> strings=new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add(String.valueOf(i));
                }
                mOptions.setPicker(strings);
              //  setPickerData(mOptions.getPickerView());
                mOptions.showAtLocation(view, Gravity.BOTTOM, 0, 0);*//*
                return true;
        }
        return false;

    }*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    /*private void getData()
    {
        arrayList.clear();
        arrayList.add(R.drawable.image1);
        arrayList.add(R.drawable.image2);
        arrayList.add(R.drawable.image3);
        arrayList.add(R.drawable.image4);
        arrayList.add(R.drawable.image5);
        arrayList.add(R.drawable.image6);
        arrayList.add(R.drawable.image7);
        arrayList.add(R.drawable.image8);
    }*/

    @Override
    public void onResume() {
        super.onResume();
       // ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.fav_select);

        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting_color);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
        //((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
    }

    @Override
    public void onPause() {
        super.onPause();
       // ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.fav);

        ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
        ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.seting);
        ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
       // ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
    }
    private void emptyData()
    {
        nothingFound.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
