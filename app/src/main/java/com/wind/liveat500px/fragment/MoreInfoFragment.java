package com.wind.liveat500px.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;
import com.wind.liveat500px.R;
import com.wind.liveat500px.dao.PhotoItemDAO;


public class MoreInfoFragment extends Fragment {

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private PhotoItemDAO dao;

    public MoreInfoFragment() {
        super();
    }

    public static MoreInfoFragment newInstance(PhotoItemDAO dao) {
        MoreInfoFragment fragment = new MoreInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao",dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    }


    private void initInstances(View rootView, Bundle savedInstanceState) {

        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return PhotoSummaryFragment.newInstance(dao);
                    case 1:
                        return PhotoInfoFragment.newInstance(dao);
                    case 2:
                        return PhotoTagsFragment.newInstance(dao);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "Summary";
                    case 1:
                        return "Info";
                    case 2:
                        return  "Tags";
                    default:
                        return "";
                }
            }
        });

        slidingTabLayout = (SlidingTabLayout)rootView.findViewById(R.id.slidingTabLayout);
        slidingTabLayout.setViewPager(viewPager);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_more_info,menu);

        MenuItem menuItem = (MenuItem) menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        shareActionProvider.setShareIntent(getShareIntent());


    }


    private Intent getShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);     // สร้าง Intent ที่ไม่ระบุผู้รับ  จะให้ User เป็นคนเลือกปลายทาง
        intent.setType("text/plain");                       // MIME Type
        intent.putExtra(Intent.EXTRA_SUBJECT,"Subject");    // มาตรฐานการส่ง text/plain
        intent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
        return intent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    private void onRestoreInstanceState(Bundle savedInstanceState) {

    }

}
