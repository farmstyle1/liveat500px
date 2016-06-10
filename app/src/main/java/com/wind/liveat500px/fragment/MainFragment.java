package com.wind.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

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
        // Init 'View' instance(s) with rootView.findViewById here
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
            }
        });

        refreshData();
    }

    private void refreshData(){
        if(photoListManager.getCount() == 0)
            reloadData();
        else
            reloadDataNewer();
    }

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDAO> call = HttpManager.getInstance().getService().loadPhotoListAfter(maxId);
        call.enqueue(new Callback<PhotoItemCollectionDAO>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDAO> call, Response<PhotoItemCollectionDAO> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    PhotoItemCollectionDAO dao = response.body();
                    photoListManager.setDao(dao);
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();

                }else{
                    Log.d("check", "Error " + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<PhotoItemCollectionDAO> call, Throwable t) {

            }
        });
    }

    private void reloadData() {
        Call<PhotoItemCollectionDAO> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemCollectionDAO>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDAO> call, Response<PhotoItemCollectionDAO> response) {
                swipeRefreshLayout.setRefreshing(false);
                if(response.isSuccessful()){
                    PhotoItemCollectionDAO dao = response.body();
                    photoListManager.setDao(dao);
                    listAdapter.setDao(dao);
                    listAdapter.notifyDataSetChanged();
                    Log.d("check", "Success " + dao);
                }else{
                    Log.d("check", "Error " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDAO> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("check", "Failure " + t);
            }
        });
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
