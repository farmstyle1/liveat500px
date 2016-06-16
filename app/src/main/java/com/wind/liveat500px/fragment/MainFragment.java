package com.wind.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.wind.liveat500px.R;
import com.wind.liveat500px.adapter.PhotoListAdapter;
import com.wind.liveat500px.dao.PhotoItemCollectionDAO;
import com.wind.liveat500px.manager.HttpManager;
import com.wind.liveat500px.manager.PhotoListManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    private ListView listView;
    private PhotoListAdapter listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotoListManager photoListManager;
    private Button btnNewPhoto;
    private boolean isLoadingMore = false;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        photoListManager = new PhotoListManager();

        btnNewPhoto = (Button)rootView.findViewById(R.id.btnNewPhoto);
        btnNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(0);
                hideButtonNewPhoto();
            }
        });
        listView = (ListView)rootView.findViewById(R.id.listView);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,  // ตำแหน่งแรกที่ถูกแสดงผล
                                 int visibleItemCount, //  show item อยู่กี่อัน
                                 int totalItemCount) {
                //จะถูกเรียกเมื่อ Scroll
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

                //  เช็คตำแหน่งล่างสุดของ ListView
                if(firstVisibleItem+visibleItemCount >= totalItemCount){
                    if(photoListManager.getCount()>0){

                    }
                }
            }
        });

        refreshData();
    }

    private void refreshData(){
        if(photoListManager.getCount() == 0) {
            Log.d("check", "reload");
            reloadData();
        }
        else {
            Log.d("check","reloadNewer");
            reloadDataNewer();
        }
    }

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDAO> call = HttpManager.getInstance().getService().loadPhotoListAfter(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }

    private void loadMoreData() {
        if(isLoadingMore)  // ถ้า isLoadingMore เป็น True จะออกจาก method เลย
            return;
        isLoadingMore = true;  // เปลี่ยน flag ให้เป็นสถานะ กำลังโหลด  พอระบบวนเข้ามาถาม ก็จะรู้ว่ากำลังโหลดอยู่จะไม่ทำการโหลดซ้ำ
        int minId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDAO> call = HttpManager.getInstance().getService().loadPhotoListBefore(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));
    }

    private void reloadData() {
        Call<PhotoItemCollectionDAO> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    private void showButtonNewPhoto(){
        btnNewPhoto.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),R.anim.zoom_fade_in);
        btnNewPhoto.startAnimation(animation);
    }
    private void hideButtonNewPhoto(){
        btnNewPhoto.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),R.anim.zoom_fade_out);
        btnNewPhoto.startAnimation(animation);
    }

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDAO>{
        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_LOAD_MORE = 3;
        int mode;

        public PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDAO> call, Response<PhotoItemCollectionDAO> response) {
            swipeRefreshLayout.setRefreshing(false);
            if(response.isSuccessful()){
                PhotoItemCollectionDAO dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();  // Check เพื่อป้องกัน NullPointerException c มีโอกาศเป็น null ถ้า Listview ไม่มีข้อมูล

                if(mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                }
                else if(mode == MODE_LOAD_MORE) {
                    photoListManager.appendDaoAtBottomPosition(dao);
                    isLoadingMore = false;
                } else {
                    photoListManager.setDao(dao);
                }
                listAdapter.setDao(photoListManager.getDao());
                listAdapter.notifyDataSetChanged();


                if (mode == MODE_RELOAD_NEWER){
                    int additionalSize =
                            ( dao != null && dao.getData() != null ) ? dao.getData().size() : 0 ;  // Check เพื่อป้องกัน NullPointerException
                    listAdapter.increaseLastPosition(additionalSize);
                    listView.setSelectionFromTop(firstVisiblePosition+additionalSize,top);
                    if(additionalSize>0)
                        showButtonNewPhoto();
                } else {

                }




            }else{
                Log.d("check", "Error " + response.errorBody().toString());
                if(mode == MODE_LOAD_MORE) {
                    isLoadingMore = false;
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDAO> call, Throwable t) {
            if(mode == MODE_LOAD_MORE) {
                isLoadingMore = false;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
