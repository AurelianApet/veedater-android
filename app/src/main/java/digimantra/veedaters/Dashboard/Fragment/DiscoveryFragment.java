package digimantra.veedaters.Dashboard.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import digimantra.veedaters.Adapter.FavoriteAdapter;
import digimantra.veedaters.Adapter.UserAdapter;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.FilterActivity;
import digimantra.veedaters.Dashboard.ParticulerUserData;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.User;
import digimantra.veedaters.Model.UserData;
import digimantra.veedaters.Model.UserLikesResponse;
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

public class DiscoveryFragment extends Fragment {
    public List<UserData.User> arrayList=new ArrayList<>();
   public List<UserData.User> filterList=new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    DashboardInteractor interactor;
    private final static int FILTER_REQUEST_CODE = 3000;
    UserAdapter adapter;
    View view;
    int value;
    int flag;
    @BindView(R.id.srarchBAr)
    SearchView srarchBAr;
    String age,distance,gender;
    @BindView(R.id.nothingFound)
    TextView nothingFound;
    Boolean isScrolling=false;
    int totalItemCount,currentItem,scrolloutItem;
    int currentPage=1;
    GridLayoutManager   mLayoutManager;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    boolean status;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.discovery_fragment,container,false);
        ButterKnife.bind(this,view);
        arrayList.clear();
        filterList.clear();
        adapter=new UserAdapter(filterList,getActivity(),getActivity(),DiscoveryFragment.this);
        mLayoutManager=   new GridLayoutManager(getActivity(),2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        interactor.setTitle("Vsearch");
        ((Dashboard)getActivity()).filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), FilterActivity.class),FILTER_REQUEST_CODE);
            }
        });
        getFiler();
        srarchBAr.setQueryHint("Search by name...");
        srarchBAr.setFocusable(true);
        srarchBAr.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter!=null)
                {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
     /*   srarchBAr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (adapter!=null)
                {
                    adapter.getFilter().filter(editable.toString());
                }
            }
        });*/
        return view;
    }
    private void getUserList()
    {
        Log.e("userParam","");
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().userList(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),String.valueOf(0)).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //CommonUtility.successToast(getContext(),response.body().getUser().size()+"");
                CommonUtility.hideProgress();
                UserData data=response.body();
                if (data!=null)
                {
                    setDataToList(data);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                CommonUtility.hideProgress();
                showListIsEmpty();
            }
        });
    }

    private void showListIsEmpty()
    {
        nothingFound.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    private void  setDataToList(final UserData data) {
        //arrayList.clear();
        //filterList.clear();
       if (data.getUser()!=null && !data.getUser().isEmpty())
       {
           nothingFound.setVisibility(View.GONE);
           recyclerView.setVisibility(View.VISIBLE);
           arrayList.addAll(arrayList.size(),data.getUser());
           filterList.addAll(filterList.size(),data.getUser());
           adapter.notifyDataSetChanged();
           adapter.setOnItemClick(new UserAdapter.OnItemClick() {

               @Override
               public void onClick(int position) {
                  // ((Dashboard)getActivity()).processFragment(DetailImages.getInstance(2,data.getUser().get(position)));
                   if (filterList.get(position).getUseMeta().getLike()==1)
                   {
                       status=true;
                   }else {
                       status=false;
                   }
                   Intent intent=new Intent(getActivity(),ParticulerUserData.class);
                   intent.putExtra(KeyValue.RECEIVER_ID,filterList.get(position).getId());
                   intent.putExtra(KeyValue.POSITION,position);
                   intent.putExtra(KeyValue.STATE,status);

                   startActivityForResult(intent,101);
               }

               @Override
               public void onLikeclick(final int position) {
                   CommonUtility.showProgress(getActivity(),"Please wait...");
                  // HashMap<String,Integer> map=new HashMap<String, String>();
                   // TODO: 8/12/17 crash is here 
                    if (filterList.get(position).getUseMeta()!=null && filterList.get(position).getUseMeta().getLike()==1)
                    {
                       // map.put("User[user_id]",arrayList.get(position).getId());
                        //map.put("User[review]",0);
                        value=0;
                        flag=0;
                        //

                    }else if (filterList.get(position).getUseMeta()!=null && filterList.get(position).getUseMeta().getLike()==0){
                       // map.put("User[user_id]",arrayList.get(position).getId());
                       // map.put("User[review]",1);
                        value=1;
                        flag=1;
                        // / arrayList.get(position).getUseMeta().setLike(1);
                    }
                   ApiClient.getClient().likeUser(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID), Integer.parseInt(arrayList.get(position).getId()),flag).enqueue(new Callback<UserLikesResponse>() {
                       @Override
                       public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
                           CommonUtility.hideProgress();
                            if (response.body().getIsSuccess())
                            {
                                filterList.get(position).getUseMeta().setLike(value);
                                adapter.notifyDataSetChanged();
                            }else {

                            }
                       }

                       @Override
                       public void onFailure(Call<UserLikesResponse> call, Throwable t) {
                           CommonUtility.hideProgress();
                       }
                   });
               }

           });
       }else {
           showListIsEmpty();
       }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                scrolloutItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (isScrolling && totalItemCount==scrolloutItem+currentItem)
                {
                    isScrolling=false;
                    currentPage++;
                    getFiler();
                }
            }
        });
       // adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    private void getFiler()
    {
        HashMap<String,String> map=new HashMap<>();
        if (age!=null)map.put("age",age);
        if (distance!=null)map.put("distance",distance.equalsIgnoreCase("World wide")? "":distance);
        if (gender!=null) map.put("gender",gender.equalsIgnoreCase("All")? "":gender);
        map.put("nearby",String.valueOf(0));
        map.put("page",String.valueOf(currentPage));
        Log.e("FilterParam",map.toString());
        if (currentPage==1)
        {
            CommonUtility.showProgress(getActivity(),"Please wait...");
        }else {
            progressBar.setVisibility(View.VISIBLE);
        }

        ApiClient.getClient().nearBy(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //CommonUtility.successToast(getContext(),response.body().getUser().size()+"");

                if (currentPage==1)
                {
                    CommonUtility.hideProgress();
                }else {
                    progressBar.setVisibility(View.GONE);
                }
                UserData data=response.body();
                if (data!=null)
                {
                    setDataToList(data);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                if (currentPage==1)
                {
                    CommonUtility.hideProgress();
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
    private void getFiler(int currentPage)
    {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String,String> map=new HashMap<>();
        if (age!=null)map.put("age",age);
        if (distance!=null)map.put("distance",distance.equalsIgnoreCase("World wide")? "":distance);
        if (gender!=null) map.put("gender",gender.equalsIgnoreCase("All")? "":gender);
        map.put("nearby",String.valueOf(0));
        map.put("page",String.valueOf(currentPage));
        Log.e("FilterParam",map.toString());

        ApiClient.getClient().nearBy(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),map).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //CommonUtility.successToast(getContext(),response.body().getUser().size()+"");
                progressBar.setVisibility(View.GONE);
                UserData data=response.body();
                if (data!=null)
                {
                    arrayList.addAll(arrayList.size(),data.getUser());
                    filterList.addAll(filterList.size(),data.getUser());
                    adapter.notifyDataSetChanged();

                    //setDataToList(data);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
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
*/
    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).filter.setVisibility(View.VISIBLE);
        ((Dashboard)getActivity()).discoveryImage.setImageResource(R.drawable.discov_sel);
        ((Dashboard)getActivity()).discoveryText.setTextColor(getResources().getColor(R.color.pink));


    }

    @Override
    public void onPause() {
        super.onPause();
        ((Dashboard)getActivity()).filter.setVisibility(View.GONE);
        ((Dashboard)getActivity()).discoveryImage.setImageResource(R.drawable.discovery);
        ((Dashboard)getActivity()).discoveryText.setTextColor(getResources().getColor(R.color.gray));
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search_view:

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

    }
*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case FILTER_REQUEST_CODE:
                age=data.getStringExtra("AGE");
                distance=data.getStringExtra("DISTANCE");
                gender=data.getStringExtra("GENDER");
                arrayList.clear();
                filterList.clear();
                currentPage=1;
                getFiler();
                break;
            case 101:
                if (data!=null)
                {
                    boolean islike=data.getBooleanExtra("Like",false);
                    boolean isblock=data.getBooleanExtra("Block",false);
                    int  pos=data.getIntExtra("POS",0);
                    if (islike)
                    {
                        filterList.get(pos).getUseMeta().setLike(1);
                        adapter.notifyItemChanged(pos);
                    }else {
                        filterList.get(pos).getUseMeta().setLike(0);
                        adapter.notifyItemChanged(pos);
                    }
                    if (isblock)
                    {
                        filterList.remove(pos);
                        arrayList.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                    if (filterList.isEmpty())
                    {
                        nothingFound.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                }
                break;
        }
    }
}
