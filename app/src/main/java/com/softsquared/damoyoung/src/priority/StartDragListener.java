package com.softsquared.damoyoung.src.priority;

import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    void requestDrag(RecyclerView.ViewHolder viewHolder);
}
