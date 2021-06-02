package com.example.myapplication.datalayer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetClient {

    private static final String TAG = NetClient.class.getSimpleName();

    public interface NetClientListener<T> {
        void dataReady(T data);
    }

    public Api routes;

    private static final NetClient instance = new NetClient();

    NetClient() {
        init();
    }

    private void init() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        okhttp3.Request.Builder builder = chain.request().newBuilder();
                        return chain.proceed(builder.build());
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(30, TimeUnit.SECONDS) //После этого времени сработает замыкание - ошибка запроса
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.1.66:9000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        routes = retrofit.create(Api.class);
    }
    private static Callback<JsonObject> createCallback(NetClientListener<JsonObject> listener){
        return new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {

                //Запрос успешный
                if (response.isSuccessful()) {
                    Log.d(TAG,"из запроса" + response.body());
                    if (response.body() != null && !response.body().isJsonNull()) {
                        listener.dataReady(response.body());
                    } else {
                        listener.dataReady(null);
                    }
                } else {
                    //Запрос неудачный
                    String errorString = getResponseErrorString(response.errorBody(), response.code());
                    Log.d(TAG,"ошибка запроса="+errorString);
                    listener.dataReady(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.d(TAG,"ошибка запроса",t);
                listener.dataReady(null);
            }
        };
    }
    //Получение информации об ошибке
    static String getResponseErrorString(ResponseBody errorBody, int code) {
        String errorString = "" + code;
        try {
            if (errorBody == null) return errorString;

            JSONObject jObjError = new JSONObject(errorString);
            errorString = errorBody.string() + "\n" + jObjError.getString("error");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return errorString;
    }
    public static void login(JsonObject jsonObject, NetClientListener<JsonObject> netClientListener){
        instance.routes.login(jsonObject).enqueue(createCallback(netClientListener));
    }
    public static void registration(JsonObject jsonObject, NetClientListener<JsonObject> netClientListener){
        instance.routes.registration(jsonObject).enqueue(createCallback(netClientListener));
    }
    public static void getTodos(JsonObject jsonObject, NetClientListener<JsonObject> netClientListener){
        instance.routes.getTodos(jsonObject).enqueue(createCallback(netClientListener));
    }
}
