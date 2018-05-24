package com.otel.warehouseassistant.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otel.warehouseassistant.fragment.ImageService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApiUtils {
    private static final String BASE_URL = "http://waswebapi.ontime-express.com:654";

    private static OkHttpClient getRequestHeader() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public static ImageService getCartonService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ImageService.class);
    }

}
