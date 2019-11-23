package com.softsquared.damoyoung.src.priority;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.sitePriority.SitePriority;

import java.util.ArrayList;
import java.util.Collections;

public class PriorityRecyclerViewAdapter extends RecyclerView.Adapter<PriorityRecyclerViewAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private ArrayList<SitePriority> mData;
    private StartDragListener mStartDragListener;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private ImageView mIvDrawerMenu;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTvTitle = itemView.findViewById(R.id.tv_priority_title);
            mIvDrawerMenu = itemView.findViewById(R.id.iv_priority_drawer_menu);
        }
    }

    public PriorityRecyclerViewAdapter(ArrayList<SitePriority> data, StartDragListener startDragListener, Context context) {
        mStartDragListener = startDragListener;
        this.mData = data;
        this.mContext = context;
    }

    public void changeData(ArrayList<SitePriority> data) {
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.priority_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTvTitle.setText(mData.get(position).getTitle());
        holder.mIvDrawerMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==
                        MotionEvent.ACTION_DOWN) {
                    mStartDragListener.requestDrag(holder);
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mData, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mData, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        if (Build.VERSION.SDK_INT >= 23) {
            myViewHolder.rowView.setBackgroundColor(mContext.getColor(R.color.colorDamoyoungBlueLight));
        } else {
            myViewHolder.rowView.setBackgroundColor(mContext.getResources().getColor(R.color.colorDamoyoungBlueLight));
        }
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }


}
