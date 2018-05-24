package com.otel.warehouseassistant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.otel.warehouseassistant.fragment.ImageDetailFragment;
import com.otel.warehouseassistant.model.Capture;

import java.util.ArrayList;

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Capture> images;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<Capture> images){
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        Capture image = images.get(position);
        return ImageDetailFragment.newInstance(image.ListImage.get(0), image.ListImage.get(0).getImageName());
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
