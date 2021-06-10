package com.example.myapplication.activities.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.activities.login.LoginVM;
import com.example.myapplication.datalayer.NetClient;
import com.example.myapplication.datalayer.ParseManager;
import com.example.myapplication.datalayer.models.ToDo;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainVM extends AndroidViewModel {
    private static final String TAG = LoginVM.class.getSimpleName();
    public MainVM(@NonNull @NotNull Application application) {
        super(application);
    }

    MutableLiveData<List<ToDo>> toDoLiveList;

    public LiveData<List<ToDo>> getData(String userId) {
        if (toDoLiveList == null) {
            toDoLiveList = new MutableLiveData<>();
            loadData(userId);
        }
        return toDoLiveList;
    }

    private void loadData(String userId) {
        //JsonObject jsonObject = new JsonObject();
       // jsonObject.addProperty("userId",userId);

        NetClient.getTodos(userId, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"TODOS:"+data.get("todos").getAsJsonArray());
                Log.d(TAG,"registration success!");
                MainVM.this.toDoLiveList.postValue(ParseManager.jsonArrayToArrayList(data.get("todos").getAsJsonArray(),ToDo[].class));

            }
        });
    }
}
