package com.upworktest.restcachetest;

import android.app.Application;
import android.graphics.Typeface;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;
import com.upworktest.restcachetest.model.ApiService;
import com.upworktest.restcachetest.model.DataflowService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestApplication extends Application {

    public static String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    private static TestApplication sInstance;
    DataflowService dataService;

    private Map<String, Typeface> fontCache = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Realm.init(this);
        Stetho.initializeWithDefaults(this);

    }

    public static TestApplication getInstance() {
        return sInstance;
    }

    public void putFontCache(String key, String name) {
        if (fontCache.get(key) == null)
            fontCache.put(key, Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/" + name));
    }

    public Typeface getFont(String key) {
        return fontCache.get(key);
    }


    public DataflowService getApiService() {
        if (dataService == null) {
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(getCacheDir(), cacheSize);
            OkHttpClient.Builder builder;
            builder = new OkHttpClient.Builder().cache(cache);
            builder = builder.addNetworkInterceptor(new StethoInterceptor());
            builder = builder.connectTimeout(100, TimeUnit.SECONDS);
            builder = builder.readTimeout(100, TimeUnit.SECONDS);
            builder = builder.writeTimeout(100, TimeUnit.SECONDS);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .build();
            dataService = new DataflowService(retrofit.create(ApiService.class));
        }
        return dataService;
    }
}
