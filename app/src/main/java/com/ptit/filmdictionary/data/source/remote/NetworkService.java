package com.ptit.filmdictionary.data.source.remote;

import android.content.Context;

public class NetworkService {
    private static ApiRequest sApiRequest;

    public static ApiRequest getInstance(Context context) {
        if (sApiRequest == null) {
            sApiRequest = RetrofitBuilder.getInstance(context).create(ApiRequest.class);
        }
        return sApiRequest;
    }
}
