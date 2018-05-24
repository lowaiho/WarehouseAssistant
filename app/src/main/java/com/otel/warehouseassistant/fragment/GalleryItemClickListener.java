package com.otel.warehouseassistant.fragment;

import android.widget.ImageView;

import com.otel.warehouseassistant.model.ImageInfo;

public interface GalleryItemClickListener {
    void onGalleryItemClickListener(int position, ImageInfo imageModel, ImageView imageView);
}
