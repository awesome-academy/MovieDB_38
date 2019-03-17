package com.ptit.filmdictionary.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({CategoryName.TITLE_NOW_PLAYING, CategoryName.TITLE_POPULAR,
        CategoryName.TITLE_TOP_RATE, CategoryName.TITLE_UP_COMING})
public @interface CategoryName {
    String TITLE_NOW_PLAYING = "Now Playing";
    String TITLE_UP_COMING = "Up Coming";
    String TITLE_TOP_RATE = "Top Rate";
    String TITLE_POPULAR = "Popular";
}
