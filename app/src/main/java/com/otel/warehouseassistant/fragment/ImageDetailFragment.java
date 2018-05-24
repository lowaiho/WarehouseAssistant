package com.otel.warehouseassistant.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.otel.warehouseassistant.R;
import com.otel.warehouseassistant.model.ImageInfo;

public class ImageDetailFragment extends Fragment {
    private static final String EXTRA_IMAGE = "image_item";
    private static final String EXTRA_TRANSITION_NAME= "transition_name";

    public ImageDetailFragment(){

    }

    public static ImageDetailFragment newInstance(ImageInfo imageUploadInfo, String transitionName) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_IMAGE, imageUploadInfo);
        args.putString(EXTRA_TRANSITION_NAME, transitionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_detail, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppBarLayout) mToolbar.getParent()).setVisibility(View.GONE);
        //mToolbar.setVisibility(View.GONE);

        final ImageInfo image = getArguments().getParcelable(EXTRA_IMAGE);
        String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

        final PhotoView imageView = (PhotoView) view.findViewById(R.id.detail_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(transitionName);
        }

        Glide.with(getActivity())
                .load(image.getImageURL())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    @TargetApi(26)
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        startPostponedEnterTransition();
                        imageView.setImageBitmap(resource);
                    }


                });
    }
}
