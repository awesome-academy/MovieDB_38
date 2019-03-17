package com.ptit.filmdictionary.base;

import android.support.v7.widget.RecyclerView;

import com.ptit.filmdictionary.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected List<T> mData;
    protected ItemListener<T> mItemListener;

    public BaseRecyclerViewAdapter() {
        mData = new ArrayList<>();
    }

    public void setItemListener(ItemListener<T> listener) {
        if (listener != null)
            mItemListener = listener;
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> data) {
        if (data != null) {
            int startPosition = data.size();
            mData.addAll(data);
            notifyItemRangeChanged(startPosition, data.size() - Constants.OFFSET_LIST);
        }
    }

    public void addData(T data) {
        if (data != null) {
            mData.add(data);
            notifyItemInserted(mData.size() - 1);
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void addItem(T t) {
        mData.add(t);
        notifyItemInserted(mData.size() - 1);
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface ItemListener<T> {
        void onItemClicked(T t, int position);
        void onElementClicked(T t, int position);
    }

}
