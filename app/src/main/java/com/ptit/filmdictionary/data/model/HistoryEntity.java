package com.ptit.filmdictionary.data.model;

import android.support.annotation.StringDef;

@StringDef({HistoryEntity.TABLE_HISTORY, HistoryEntity.ID, HistoryEntity.TITLE})
public @interface HistoryEntity {
    String TABLE_HISTORY = "history";
    String ID = "id";
    String TITLE = "title";
}
