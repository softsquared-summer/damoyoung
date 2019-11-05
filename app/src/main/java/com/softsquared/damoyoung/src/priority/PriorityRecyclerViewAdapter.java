package com.softsquared.damoyoung.src.priority;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;
import java.util.Collections;

public class PriorityRecyclerViewAdapter extends RecyclerView.Adapter<PriorityRecyclerViewAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private ArrayList<String> data;
    private StartDragListener mStartDragListener;
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

    public PriorityRecyclerViewAdapter(ArrayList<String> data,StartDragListener startDragListener) {
        mStartDragListener = startDragListener;
        this.data = data;
    }
    public void changeData(ArrayList<String> data){
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.priority_rv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTvTitle.setText(data.get(position));
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
        return data.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);

    }


}
