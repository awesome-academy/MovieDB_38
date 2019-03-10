package com.ptit.filmdictionary.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({CategoryKey.CATEGORY_POPULAR, CategoryKey.CATEGORY_NOW_PLAYING,
        CategoryKey.CATEGORY_UP_COMING, CategoryKey.CATEGORY_TOP_RATE})
public @interface CategoryKey {
    String CATEGORY_POPULAR = "popular";
    String CATEGORY_NOW_PLAYING = "now_playing";
    String CATEGORY_UP_COMING = "upcoming";
    String CATEGORY_TOP_RATE = "top_rated";
}
