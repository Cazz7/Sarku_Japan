package com.example.cristian.restaurante;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> listaFragments) {
        super(fragmentManager);
        // TODO Auto-generated constructor stub
        this.fragments = listaFragments;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
    }
}