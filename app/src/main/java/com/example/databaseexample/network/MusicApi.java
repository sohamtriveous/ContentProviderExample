package com.example.databaseexample.network;

import com.example.databaseexample.model.MusicmatchReesponse;

import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by sohammondal on 24/04/15.
 */
public class MusicApi {
    private static final String API_URL = "https://www.kimonolabs.com";
    private static MusicInterface musicInterface = null;

    public static MusicInterface getApi() {
        if(musicInterface == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .build();
            musicInterface = restAdapter.create(MusicInterface.class);
        }
        return musicInterface;
    }

    public interface MusicInterface {
        @GET("/api/619szn6c")
        MusicmatchReesponse getLatestMusic(@Query("apikey") String apiKey);

        @GET("/api/619szn6c")
        void getLatestMusic(@Query("apikey") String apiKey, Callback<MusicmatchReesponse> callback);
    }
}
