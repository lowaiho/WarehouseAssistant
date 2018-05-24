package com.otel.warehouseassistant.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.otel.warehouseassistant.NewImagePostActivity;
import com.otel.warehouseassistant.R;
import com.otel.warehouseassistant.adapter.RecyclerViewAdapter;
import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.model.ImageInfo;
import com.otel.warehouseassistant.task.GetImageListTask;

import java.util.List;

public class RecyclerViewFragment extends Fragment implements GalleryItemClickListener {
    public static final String TAG = RecyclerViewFragment.class.getSimpleName();

    List<Capture> listCapture;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    Boolean loading;

    public RecyclerViewFragment() {

    }

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(view.getContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppBarLayout) mToolbar.getParent()).setVisibility(View.VISIBLE);

        if (listCapture == null) {
            LoadImages(view);
        }
        else{
            RecyclerViewAdapter galleryAdapter = new RecyclerViewAdapter(listCapture, this);

            recyclerView.setAdapter(galleryAdapter);
        }
    }

    @Override
    public void onGalleryItemClickListener(int position, ImageInfo imageModel, ImageView imageView) {
        GalleryViewPagerFragment galleryViewPagerFragment = GalleryViewPagerFragment.newInstance(position, listCapture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(imageModel.getImageName());
        }

        //Bundle bundle = new Bundle();
        //bundle.putParcelable("ListState", recyclerView.getLayoutManager().onSaveInstanceState());
        //setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                .addToBackStack(TAG)
                .replace(R.id.content, galleryViewPagerFragment)
                .commit();

    }

    private void LoadImages(final View view) {
        final GalleryItemClickListener listener = this;

        progressDialog.setTitle("Images is loading...");
        loading = true;
        // Showing progressDialog.
        progressDialog.show();

        GetImageListTask task = new GetImageListTask(new GetImageListTask.OnSuccessListener() {
            @Override
            public void OnSuccess(List<Capture> result_list) {

                progressDialog.dismiss();

                if (result_list != null) {
                    listCapture = result_list;
                    RecyclerViewAdapter galleryAdapter = new RecyclerViewAdapter(result_list, listener);

                    recyclerView.setAdapter(galleryAdapter);

                    /*
                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);

                            if (!loading && !recyclerView.canScrollVertically(-1)) {
                                LoadImages(view);
                            }

                        }

                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    });
                    */

                    //if (getArguments() != null && getArguments().getParcelable("ListState") != null) {
                    //    Parcelable listState = getArguments().getParcelable("ListState");
                    //    recyclerView.getLayoutManager().onRestoreInstanceState(listState);
                    //}

                }
                loading = false;
            }
        });

        task.execute((Void) null);
    }
}
