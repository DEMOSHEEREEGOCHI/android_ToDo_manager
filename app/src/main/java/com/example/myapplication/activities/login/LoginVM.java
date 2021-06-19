package com.example.myapplication.activities.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.activities.main.MainActivity;
import com.example.myapplication.datalayer.NetClient;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

public class LoginVM extends AndroidViewModel {
    private static final String TAG = LoginVM.class.getSimpleName();

    public LoginVM(@NonNull @NotNull Application application) {
        super(application);

        Context context = getApplication();

    }


    public void registration(String login,String password){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",login);
        jsonObject.addProperty("password",password);
        NetClient.registration(jsonObject, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"registration success!");
            }
        });
    }
    public void login(String login,String password, Context context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",login);
        jsonObject.addProperty("password",password);

        NetClient.login(jsonObject, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"login success!");
                Log.d(TAG,"пользователь"+data.get("id").toString());
                Log.d(TAG,"json:"+data.toString());
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id",data.get("id").getAsString());
                context.startActivity(intent);
            }
        });
    }
}
