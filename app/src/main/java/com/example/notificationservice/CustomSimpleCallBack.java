package com.example.notificationservice;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomSimpleCallBack extends ItemTouchHelper.SimpleCallback {
    List<Model> modelList;
    CustomListAdapter customListAdapter;
    public CustomSimpleCallBack(List<Model> modelList, CustomListAdapter customListAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.modelList = modelList;
        this.customListAdapter = customListAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
            int index = viewHolder.getAdapterPosition();
            modelList.remove(index);
            customListAdapter.notifyItemRemoved(index);
        }
    }
}
