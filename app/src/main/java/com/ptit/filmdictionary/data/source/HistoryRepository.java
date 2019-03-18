package com.ptit.filmdictionary.data.source;

import android.content.Context;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.source.local.HistoryLocalDataSource;
import com.ptit.filmdictionary.utils.MyApplication;

public class HistoryRepository implements HistoryDataSource.Local, HistoryDataSource.Remote {
    private static HistoryRepository sInstance;
    private HistoryLocalDataSource mLocalDataSource;

    private HistoryRepository(HistoryLocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static HistoryRepository getInstance() {
        if(sInstance == null){
            sInstance = new HistoryRepository(HistoryLocalDataSource
                    .getInstance(MyApplication.getContext()));
        }
        return sInstance;
    }

    @Override
    public ObservableList<History> getAllHistories() {
        return mLocalDataSource.getAllHistories();
    }

    @Override
    public boolean addedHistory(History history) {
        return mLocalDataSource.addedHistory(history);
    }

    @Override
    public boolean deletedHistory(History history) {
        return mLocalDataSource.deletedHistory(history);
    }
}
