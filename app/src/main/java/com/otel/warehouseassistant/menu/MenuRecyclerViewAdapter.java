package com.otel.warehouseassistant.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otel.warehouseassistant.R;

import java.util.List;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuViewHolders> implements View.OnClickListener {
    private List<MenuObject> itemList;
    private Context context;

    private OnItemClickListener mOnItemClickListener = null;

    public MenuRecyclerViewAdapter(Context context, List<MenuObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MenuViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_item, null);
        MenuViewHolders h = new MenuViewHolders(layoutView);

        layoutView.setOnClickListener(this);
        return h;
    }

    @Override
    public void onBindViewHolder(MenuViewHolders holder, int position) {
        holder.menuText.setText(itemList.get(position).getName());
        holder.menuImage.setImageResource(itemList.get(position).getPhoto());
        holder.relativeLayout.setBackgroundColor(context.getResources().getColor(itemList.get(position).getBgColor()));

        holder.itemView.setTag(position);

        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.menuText.getLayoutParams();
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, holder.relativeLayout.getId());
        //holder.menuText.setLayoutParams(params);

//        if (itemList.get(position).getHeight() > 0)
//            holder.relativeLayout.getLayoutParams().height = (itemList.get(position).getHeight());

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}
