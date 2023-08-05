package digimantra.veedaters.Dashboard.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import digimantra.veedaters.Adapter.ImageAdapter;
import digimantra.veedaters.Adapter.ImageDetailAdapter;
import digimantra.veedaters.Dashboard.Dashboard;
import digimantra.veedaters.Dashboard.FilterActivity;
import digimantra.veedaters.Dashboard.MessageActivity;
import digimantra.veedaters.Dashboard.ParticulerUserData;
import digimantra.veedaters.Model.BlockResponse;
import digimantra.veedaters.Model.BlockUserList;
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
 * Created by dmlabs on 8/11/17.
 */

public class DetailImages extends Fragment implements ActionSheet.ActionSheetListener {
    @BindView(R.id.pager)
    ViewPager pager;
    ArrayList<Integer> arrayList=new ArrayList<>();
    DashboardInteractor interactor;
    public static int commingFrom;
    UserData.User user;
   static int lastPos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.detail_images,container,false);
        ButterKnife.bind(this,view);

       // getData();
            user=getArguments().getParcelable("DATA");
        if (user!=null)
        {
            interactor.setTitle(user.getUsername()+"");
          //  ImageDetailAdapter imageAdapter=new ImageDetailAdapter(getFragmentManager(),user,getActivity());
          //  pager.setAdapter(imageAdapter);
            pager.setPageTransformer(true, new ZoomOutSlideTransformer());
            pager.setClipToPadding(false);
            pager.setPageMargin(5);
        }


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactor=(DashboardInteractor)context;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            inflater.inflate(R.menu.three_icon, menu);
            super.onCreateOptionsMenu(menu, inflater);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static DetailImages getInstance(int pos,UserData.User user)
    {

        commingFrom=pos;
        Bundle bundle=new Bundle();
        bundle.putInt("KEY",pos);
        bundle.putParcelable("DATA",user);
        DetailImages detailImages=new DetailImages();
        detailImages.setArguments(bundle);
        return detailImages;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Dashboard)getActivity()).backButton.setVisibility(View.VISIBLE);

        if (commingFrom==0)
        {
            ((Dashboard)getActivity()).nearByImage.setImageResource(R.drawable.map_select);
            ((Dashboard)getActivity()).nearByText.setTextColor(getResources().getColor(R.color.pink));
        }else if (commingFrom==1)
        {
            ((Dashboard)getActivity()).nearByImage.setImageResource(R.drawable.map_select);
            ((Dashboard)getActivity()).nearByText.setTextColor(getResources().getColor(R.color.pink));
            //((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.fav_select);
           // ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.pink));
        }else if (commingFrom==2){
            ((Dashboard)getActivity()).discoveryImage.setImageResource(R.drawable.discov_sel);
            ((Dashboard)getActivity()).discoveryText.setTextColor(getResources().getColor(R.color.pink));
        }
    }


    @Override
    public void onPause() {
        ((Dashboard)getActivity()).backButton.setVisibility(View.GONE);
        super.onPause();
        if (commingFrom==0)
        {
            ((Dashboard)getActivity()).nearByImage.setImageResource(R.drawable.map);
            ((Dashboard)getActivity()).nearByText.setTextColor(getResources().getColor(R.color.gray));
        }else if (commingFrom==1){
            ((Dashboard)getActivity()).favoritesImage.setImageResource(R.drawable.fav);
            ((Dashboard)getActivity()).favoritesText.setTextColor(getResources().getColor(R.color.gray));
        }
        else if (commingFrom==2){
            ((Dashboard)getActivity()).discoveryImage.setImageResource(R.drawable.discovery);
            ((Dashboard)getActivity()).discoveryText.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    private void getData()
    {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.threeDot:
                openDialog();
                return true;
        }
        return false;

    }
    private void openDialog()
    {
        ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("View Profile", "Message", "Block User")
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        switch (index)
        {
            case 0:

                Intent intent=new Intent(getActivity(),ParticulerUserData.class);
                intent.putExtra(KeyValue.RECEIVER_ID,user.getId());
                startActivity(intent);
                break;

            case 1:
                Intent intent1=new Intent(getActivity(),MessageActivity.class);
                intent1.putExtra(KeyValue.RECEIVER_ID,user.getId());
                startActivity(intent1);
                break;
            case 2:
                if (user!=null)
                {
                    blockUser();
                }
                break;
            case 3:
                break;
        }
    }

    private void blockUser()
    {
        CommonUtility.showProgress(getActivity(),"Please wait...");
        ApiClient.getClient().blockUser(String.valueOf(PreferenceConnector.getInstance(getActivity()).readInt(KeyValue.USER_ID)),user.getId()).enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                CommonUtility.hideProgress();
                BlockResponse blockResponse=response.body();
                if (blockResponse!=null ) {
                    if (blockResponse.getIsSuccess()) {
                        CommonUtility.successToast(getActivity(), blockResponse.getMessage() + "");
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        CommonUtility.successToast(getActivity(), blockResponse.getError() + "");
                    }
                }
            }
            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {
                CommonUtility.hideProgress();
            }
        });
    }
}
