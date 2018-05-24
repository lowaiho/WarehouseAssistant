package com.otel.warehouseassistant.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.otel.warehouseassistant.R;

public class MenuViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView menuText;
    public ImageView menuImage;
    public RelativeLayout relativeLayout;

    public MenuViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        menuText = (TextView) itemView.findViewById(R.id.menu_text);
        menuImage = (ImageView) itemView.findViewById(R.id.menu_image);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
