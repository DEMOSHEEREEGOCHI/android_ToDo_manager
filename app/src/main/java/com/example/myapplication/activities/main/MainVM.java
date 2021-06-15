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

    private MutableLiveData<List<ToDo>> toDoLiveList;
    private String userId;

    public MainVM(@NonNull @NotNull Application application) {
        super(application);
    }

    public MainVM setUserId(String userId){
        this.userId = userId;
        return this;
    }

    public LiveData<List<ToDo>> getData() {
        if (toDoLiveList == null) {
            toDoLiveList = new MutableLiveData<>();
            loadData();
        }
        return toDoLiveList;
    }

    private void loadData() {
        NetClient.getTodos(userId, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"TODOS:"+data.get("todos").getAsJsonArray());
                Log.d(TAG,"USER_ID:"+userId);
                MainVM.this.toDoLiveList.postValue(ParseManager.jsonArrayToArrayList(data.get("todos").getAsJsonArray(),ToDo[].class));
            }
        });
    }
    public void deleteToDo(String id){
        NetClient.deleteToDoById(id, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"DELETED!");
                MainVM.this.loadData();
            }
        });
    }
}
