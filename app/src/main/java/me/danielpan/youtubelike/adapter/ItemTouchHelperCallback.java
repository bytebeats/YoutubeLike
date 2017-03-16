package me.danielpan.youtubelike.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import me.danielpan.youtubelike.abs.DragAndSwipeHelper;

/**
 * Created by Pan Chen on 16/03/2017 : 17:23.
 * Company : www.CreditEase.cn;
 * Email : chenpan1@creditease.cn;
 * Motto : If you can take it, you can make it.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private DragAndSwipeHelper mHelper;

    public ItemTouchHelperCallback(DragAndSwipeHelper helper) {
        this.mHelper = helper;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int moveFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(moveFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mHelper != null) {
            mHelper.onDraggedAndDropped(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mHelper == null) {
            mHelper.onSwiped(viewHolder.getAdapterPosition());
        }
    }
}
