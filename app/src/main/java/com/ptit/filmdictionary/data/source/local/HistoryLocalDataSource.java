package com.ptit.filmdictionary.data.source.local;

import android.content.Context;
import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.History;
import com.ptit.filmdictionary.data.source.HistoryDataSource;

public class HistoryLocalDataSource implements HistoryDataSource.Local {
    private static HistoryLocalDataSource sInstance;
    private DataBaseHelper mHelper;

    private HistoryLocalDataSource(Context context) {
        mHelper = new DataBaseHelper(context);
    }

    public static HistoryLocalDataSource getInstance(Context context) {
        if(sInstance == null){
            sInstance = new HistoryLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public ObservableList<History> getAllHistories() {
        return mHelper.getAllHistories();
    }

    @Override
    public boolean addedHistory(History history) {
        return mHelper.addedHistory(history);
    }

    @Override
    public boolean deletedHistory(History history) {
        return mHelper.deletedHistory(history);
    }
}
