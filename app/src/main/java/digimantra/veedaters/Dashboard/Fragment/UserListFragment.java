package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Adapter.DividerItemDecoration;
import digimantra.veedaters.Adapter.UserListAdapter;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.MessageActivity;
import digimantra.veedaters.Dashboard.VeeDatersApp;
import digimantra.veedaters.Model.ChatMode;
import digimantra.veedaters.Model.UserLikesResponse;
import digimantra.veedaters.Model.UserModel;
import digimantra.veedaters.R;
import digimantra.veedaters.networking.ApiClient;
import digimantra.veedaters.utility.CommonUtility;
import digimantra.veedaters.utility.KeyValue;
import digimantra.veedaters.utility.PreferenceConnector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dmlabs on 9/11/17.
 */

public class UserListFragment extends Fragment {
    @BindView(R.id.userList)
    public RecyclerView userList;
    DashboardInteractor interactor;
    ArrayList<ChatMode.UserMessage> arrayList=new ArrayList<>();
    HashMap<String,ChatMode.UserMessage> map=new HashMap<>();
    private int REQ_CODE=10001;
    UserListAdapter adapter;
    int lastClick;
    private String name[]={"Justin Adam",
            "Geoof Jenny",
            "Ethyl Stansbury",
            "Brody larson",
            "Design Team",
            "Leslie Crew"
    };
    private String message[]={"I believe I have a very good chance.",
            "No rush! I'm around over the weekend too if i can be of any help.",
            "ok :)",
            "Thanks guys!",
            "Basketball was fun! Thank's for coming.",
            "I know who I am. I'm not a mistake"
    };
    private int images[]={

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_list_fragment,container,false);
        ButterKnife.bind(this,view);
        interactor.setTitle("Message");


        return view;
    }
    private void clearChat(final int pos, String id)
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().clearChat(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),id).enqueue(new Callback<UserLikesResponse>() {
            @Override
            public void onResponse(Call<UserLikesResponse> call, Response<UserLikesResponse> response) {
                CommonUtility.hideProgress();
                if (response.isSuccessful())
                {
                    arrayList.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserLikesResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("REq",requestCode +""+resultCode +"   "+VeeDatersApp.getLastClick());
        if (requestCode==REQ_CODE && resultCode==200)
        {
            if (data!=null && data.getBooleanExtra("CLEAR",false))
            {
                if (!arrayList.isEmpty() && VeeDatersApp.getLastClick()<arrayList.size())
                arrayList.remove(VeeDatersApp.getLastClick());
                adapter.notifyDataSetChanged();
            }else {

            }
        }
    }

    private void getData()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().getMessage(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)).enqueue(new Callback<ChatMode>() {
            @Override
            public void onResponse(Call<ChatMode> call, Response<ChatMode> response) {
                ChatMode chatMode=response.body();
                arrayList.clear();
                if (chatMode!=null && chatMode.getList()!=null)
                {
                    arrangeData(chatMode.getList());
                   // arrayList.addAll(chatMode.getList());
                   // adapter.notifyDataSetChanged();
                }
                CommonUtility.hideProgress();
            }

            @Override
            public void onFailure(Call<ChatMode> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }

    private void arrangeData(List<ChatMode.UserMessage> list)
    {
        for (int i = 0; i <list.size() ; i++)
        {
            ChatMode.UserMessage userMessage=list.get(i);
            if (userMessage.getInfo().getSender().getId().equalsIgnoreCase(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID))))
            {
                if (userMessage.getInfo().getReciever()!=null)
                map.put(userMessage.getInfo().getReciever().getId(),userMessage);
            }else {
                if (userMessage.getInfo().getSender()!=null)
                map.put(userMessage.getInfo().getSender().getId(),userMessage);
            }

        }
        Iterator<Map.Entry<String, ChatMode.UserMessage>> itr = map.entrySet().iterator();

        while(itr.hasNext())
        {
            Map.Entry<String, ChatMode.UserMessage> entry = itr.next();
        arrayList.add(entry.getValue());
        }
        setData();
        adapter.notifyDataSetChanged();
    }
    private void setData()
    {
        adapter=new UserListAdapter(arrayList,getActivity());
        userList.setLayoutManager(new LinearLayoutManager(getActivity()));
        userList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        userList.setAdapter(adapter);
        adapter.setListener(new UserListAdapter.OnitemClickListener() {
            @Override
            public void onClick(int position) {
                VeeDatersApp.setLastClick(position);
               // Intent intent=new Intent(getActivity(),MessageActivity.class);
                Intent intent=new Intent(getActivity(),MessageNewActivity.class);
                if (String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)).equalsIgnoreCase(arrayList.get(position).getInfo().getSender().getId()))
                {
                    VeeDatersApp.setMyName(arrayList.get(position).getInfo().getSender().getUsername());
                    VeeDatersApp.setYourName(arrayList.get(position).getInfo().getReciever().getUsername());
                    intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(position).getInfo().getReciever().getId());
                    if (arrayList.get(position).getInfo().getReciever()!=null && arrayList.get(position).getInfo().getReciever().getUserPhoto()!=null)
                    {

                        VeeDatersApp.setPhotoPath(arrayList.get(position).getInfo().getReciever().getUserPhoto().getPhotoPath());
                        VeeDatersApp.setSenderPic(arrayList.get(position).getInfo().getReciever().getUserPhoto().getPhotoPath());
                        if (arrayList.get(position).getInfo().getSender()!=null && arrayList.get(position).getInfo().getSender().getSuserPhoto()!=null ) {
                            VeeDatersApp.setMyPic(arrayList.get(position).getInfo().getSender().getSuserPhoto().getPhotoPath());
                        }
                    }else {
                        VeeDatersApp.setPhotoPath(null);
                        VeeDatersApp.setSenderPic(null);
                        VeeDatersApp.setMyPic(null);
                    }
                    /*intent.putExtra(KeyValue.RECEIVER_PHOTO,arrayList.get(position).getInfo().getReciever().);*/
                }else {
                    VeeDatersApp.setMyName(arrayList.get(position).getInfo().getReciever().getUsername());
                    VeeDatersApp.setYourName(arrayList.get(position).getInfo().getSender().getUsername());
                    intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(position).getInfo().getSender().getId());
                    if (arrayList.get(position).getInfo().getSender()!=null && arrayList.get(position).getInfo().getSender().getSuserPhoto()!=null )
                    {
                        VeeDatersApp.setSenderPic(arrayList.get(position).getInfo().getSender().getSuserPhoto().getPhotoPath());
                        if (arrayList.get(position).getInfo().getReciever()!=null && arrayList.get(position).getInfo().getReciever().getUserPhoto()!=null) {
                            VeeDatersApp.setMyPic(arrayList.get(position).getInfo().getReciever().getUserPhoto().getPhotoPath());
                        }
                        VeeDatersApp.setPhotoPath(arrayList.get(position).getInfo().getSender().getSuserPhoto().getPhotoPath());
                    }else {
                        VeeDatersApp.setPhotoPath(null);
                        VeeDatersApp.setSenderPic(null);
                        VeeDatersApp.setMyPic(null);
                    }
                    /*intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(position).getInfo().getSender().getId());*/
                }

                startActivityForResult(intent,REQ_CODE);
                // startActivity(new Intent(getActivity(), MessageActivity.class));
            }

            @Override
            public void onDelete(int position) {
                if (String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)).equalsIgnoreCase(arrayList.get(position).getInfo().getSender().getId()))
                {
                    if (arrayList.get(position).getInfo().getReciever()!=null)
                        clearChat(position,arrayList.get(position).getInfo().getReciever().getId());
                    /*intent.putExtra(KeyValue.RECEIVER_PHOTO,arrayList.get(position).getInfo().getReciever().);*/
                }else {
                    clearChat(position,arrayList.get(position).getInfo().getSender().getId());
                    /*intent.putExtra(KeyValue.RECEIVER_ID,arrayList.get(position).getInfo().getSender().getId());*/
                }

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
        ((Dashboard)getActivity()).messageImage.setImageResource(R.drawable.message_select);
        ((Dashboard)getActivity()).messageText.setTextColor(getResources().getColor(R.color.pink));

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @Override
    public void onPause() {
        super.onPause();
        ((Dashboard)getActivity()).messageImage.setImageResource(R.drawable.message);
        ((Dashboard)getActivity()).messageText.setTextColor(getResources().getColor(R.color.gray));
    }
}
