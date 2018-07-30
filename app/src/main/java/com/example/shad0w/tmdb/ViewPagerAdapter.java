package com.example.shad0w.tmdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

String query;
Bundle bundle;

    public ViewPagerAdapter(FragmentManager fm,String query) {

        super(fm);
        this.query=query;
        bundle=new Bundle();
        bundle.putString(Contact.Name,query);
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            Fragment fragment = new SearchMovieFragment();
            fragment.setArguments(bundle);
            return fragment;
        } else if (position == 1){
            Fragment fragment = new SearchTvShowFragment();
            fragment.setArguments(bundle);
            return fragment;
    }else if(position==2) {
            Fragment fragment= new SearchPersonFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
