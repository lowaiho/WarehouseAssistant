package com.otel.warehouseassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.otel.warehouseassistant.NewImagePostActivity;
import com.otel.warehouseassistant.R;
import com.otel.warehouseassistant.fragment.GalleryItemClickListener;
import com.otel.warehouseassistant.model.Capture;
import com.otel.warehouseassistant.model.ImageInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Capture> MainImageInfoList;
    private final GalleryItemClickListener galleryItemClickListener;

    private Context context;

    public RecyclerViewAdapter(List<Capture> TempList, GalleryItemClickListener galleryItemClickListener) {
        this.MainImageInfoList = TempList;
        this.galleryItemClickListener = galleryItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Capture capture = MainImageInfoList.get(position);

        final ImageInfo UploadInfo = capture.ListImage.get(0);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if (capture.Type.equals(NewImagePostActivity.IN_BOUND))
            holder.SKUTextView.setText("[" + context.getString(R.string.inbound) + "]" + capture.SKU);
        else if (capture.Type.equals(NewImagePostActivity.OUT_BOUND))
            holder.SKUTextView.setText("[" + context.getString(R.string.outbound) + "]" + capture.SKU);
        else if (capture.Type.equals(NewImagePostActivity.DAMAGE))
            holder.SKUTextView.setText("[" + context.getString(R.string.damage) + "]" + capture.SKU);
        else if (capture.Type.equals(NewImagePostActivity.OTHERS))
            holder.SKUTextView.setText("[" + context.getString(R.string.others) + "]" + capture.SKU);


        if (capture.Type.equals(NewImagePostActivity.IN_BOUND) || capture.Type.equals(NewImagePostActivity.OUT_BOUND)) {
            holder.ASNLotLPN.setText(capture.OrderNo);
        } else {
            holder.ASNLotLPN.setText(capture.LotNo + ", " + capture.LPN);
        }

        holder.imageNameTextView.setText(UploadInfo.getImageName());
        holder.imageDateTimeTextView.setText(formatter.format(capture.CreateOn));

        //Loading image from Glide library.
        Glide.with(holder.imageView.getContext())
                .load(UploadInfo.getImageURL())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                galleryItemClickListener.onGalleryItemClickListener(holder.getAdapterPosition(), UploadInfo, holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (MainImageInfoList != null)
            return MainImageInfoList.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView imageNameTextView;
        public TextView imageDateTimeTextView;

        public TextView SKUTextView;
        public TextView ASNLotLPN;

        public ViewHolder(View itemView) {
            super(itemView);

            SKUTextView = itemView.findViewById(R.id.SKUTextView);
            ASNLotLPN = itemView.findViewById(R.id.ASNLotLPN);

            imageDateTimeTextView = itemView.findViewById(R.id.imageDateTimeTextView);
            imageView = itemView.findViewById(R.id.galleryImage);
            imageNameTextView = itemView.findViewById(R.id.imageNameTextView);
        }
    }
}
