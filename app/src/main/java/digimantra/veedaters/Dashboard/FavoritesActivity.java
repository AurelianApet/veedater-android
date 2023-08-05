package digimantra.veedaters.Dashboard;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import net.hockeyapp.android.CrashManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digimantra.veedaters.Adapter.BlockAdapter;
import digimantra.veedaters.Adapter.FavoriteAdapter;
import digimantra.veedaters.Dashboard.Fragment.DetailImages;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.BlockUserList;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.settings.FingurePrint;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {
    ArrayList<BlockUserList.BlockedUser> arrayList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nothingFound)
    TextView nothingFound;
    @BindView(R.id.backButton)
    ImageView backButton;
    ArrayList<View>  list=new ArrayList<>();
    ArrayList<String>  Idlist=new ArrayList<>();
    BlockAdapter userAdapter;
    @BindView(R.id.done)
    TextView done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unblockUsers(Idlist);
            }
        });

    }

    private void unblockUsers(ArrayList<String> idlist) {
        if (!idlist.isEmpty())
        {
            CommonUtility.showProgress(this,"Please wait...");
            LinkedHashMap<String,String> hashMap=new LinkedHashMap<>();
            for (int i = 0; i < idlist.size(); i++)
            {
                hashMap.put("User[user_id]["+i+"]",idlist.get(i));
            }
            Log.e("unblockParams",hashMap.toString());
            ApiClient.getClient().unBlockUser(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID)),hashMap).enqueue(new Callback<BlockResponse>() {
                @Override
                public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response)
                {
                    CommonUtility.hideProgress();
                    BlockResponse blockResponse=response.body();
                    if (blockResponse!=null)
                    {
                        if (blockResponse.getIsSuccess())
                        {
                            //CommonUtility.successToast(FavoritesActivity.this,blockResponse.getIsSuccess()+"");
                        finish();
                        }else
                        {

                        }
                    }
                }

                @Override
                public void onFailure(Call<BlockResponse> call, Throwable t)
                {
                    CommonUtility.hideProgress();
                }
            });
        }

    }

    @OnClick(R.id.backButton)
    public void onBackClick()
    {
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getBlockedUser();
        CrashManager.register(this,getResources().getString(R.string.HockeyAppID));
        VeeDatersApp myApp = (VeeDatersApp)this.getApplication();
        if (myApp.wasInBackground)
        {
            //Do specific came-here-from-background code
            if (PreferenceConnector.getInstance(FavoritesActivity.this).readBoolean(KeyValue.ISPASSCODEACTIVE))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent=new Intent(FavoritesActivity.this,FingurePrint.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(FavoritesActivity.this,PasscodeActivity.class);
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

    private void getBlockedUser()
    {
        CommonUtility.showProgress(this,"Please wait...");
        ApiClient.getClient().getBlockUser(String.valueOf(PreferenceConnector.getInstance(this).readInt(KeyValue.USER_ID))).enqueue(new Callback<BlockUserList>() {
            @Override
            public void onResponse(Call<BlockUserList> call, Response<BlockUserList> response) {
                CommonUtility.hideProgress();
                BlockUserList blockUserList=response.body();
                if (blockUserList!=null && blockUserList.getIsSuccess())
                {
                    arrayList= (ArrayList<BlockUserList.BlockedUser>) blockUserList.getBlockedUsers();
                   if (arrayList!=null && !arrayList.isEmpty())
                   {
                       inflateData(arrayList);
                   }else {
                       emptyList();
                   }
                }else {
                    CommonUtility.warningToast(FavoritesActivity.this,"Something went wrong!");
                }

            }

            @Override
            public void onFailure(Call<BlockUserList> call, Throwable t) {
                CommonUtility.hideProgress();
                t.printStackTrace();
                emptyList();

            }
        });
    }

    private void inflateData(final ArrayList<BlockUserList.BlockedUser> arrayList)
    {
        nothingFound.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        userAdapter=new BlockAdapter(arrayList,this,this);
        recyclerView.setAdapter(userAdapter);
        userAdapter.setOnItemClick(new BlockAdapter.OnItemClick() {

            @Override
            public void onClick(View view, int position) {
                if (arrayList.get(position)!=null & arrayList.get(position).isSelecte())
                {
                    list.remove(view);
                    Idlist.remove(arrayList.get(position).getId());
                    arrayList.get(position).setSelecte(false);
                    ImageView imageView=(ImageView) view.findViewById(R.id.blockImage);
                    imageView.setImageResource(R.drawable.block);
                }else if (arrayList.get(position)!=null & !arrayList.get(position).isSelecte()){
                    list.add(view);
                    Idlist.add(arrayList.get(position).getId());
                    arrayList.get(position).setSelecte(true);
                    ImageView imageView=(ImageView) view.findViewById(R.id.blockImage);
                    imageView.setImageResource(R.drawable.check);
                }
            }
        });
    }
    private void emptyList()
    {
        nothingFound.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        done.setVisibility(View.GONE);
    }
}
