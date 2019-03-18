package com.ptit.filmdictionary.data.source;

import android.databinding.ObservableList;

import com.ptit.filmdictionary.data.model.History;

public interface HistoryDataSource {
    interface Local {
        ObservableList<History> getAllHistories();

        boolean addedHistory(History history);

        boolean deletedHistory(History history);
    }

    interface Remote {

    }
}
