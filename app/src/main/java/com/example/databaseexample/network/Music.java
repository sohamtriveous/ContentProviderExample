package com.example.databaseexample.network;

import com.example.databaseexample.model.MusicmatchReesponse;

import retrofit.Callback;

/**
 * Created by sohammondal on 24/04/15.
 */
public class Music {
    private static final String API_KEY = "ZdSnJCoFzxb1gjUS3m9wSx97NrSPDbdQ";

    private static String getApiKey() {
        return API_KEY;
    }

    public static void getLatestMusic(Callback<MusicmatchReesponse> callback) {
        MusicApi.getApi().getLatestMusic(getApiKey(), callback);
    }

    public static MusicmatchReesponse getLatestMusic() {
        return MusicApi.getApi().getLatestMusic(getApiKey());
    }


}
