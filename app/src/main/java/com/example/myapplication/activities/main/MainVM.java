package com.example.myapplication.activities.main;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainVM extends AndroidViewModel {
    private static final String TAG = LoginVM.class.getSimpleName();

    private MutableLiveData<List<ToDo>> toDoLiveList;
    private String userId;

    public MainVM(@NonNull @NotNull Application application) {
        super(application);
    }

    public MainVM setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public LiveData<List<ToDo>> initLiveDataList() {
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
                Log.d(TAG, "TODOS:" + data.get("todos").getAsJsonArray());
                Log.d(TAG, "USER_ID:" + userId);
                ArrayList<ToDo> list = ParseManager.jsonArrayToArrayList(data.get("todos").getAsJsonArray(), ToDo[].class);
                sortList(list);
                MainVM.this.toDoLiveList.postValue(list);
            }
        });
    }

    void sortList(ArrayList<ToDo> data){
        Collections.sort(data,new Comparator<ToDo>(){
            @Override
            public int compare(ToDo o1, ToDo o2) {
                if (o1.isComplete())return -1;
                if (o2.isComplete())return 1;
                else return 0;
            }
        });
    }
    public void deleteToDo(String id) {
        NetClient.deleteToDoById(id, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG, "DELETED!");
                MainVM.this.loadData();
            }
        });
    }


    public void createToDo(Context context) {
        final EditText input = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Новая задача")
                .setView(input)
                .setCancelable(true)
                .setNegativeButton("создать",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("userId", userId);
                                jsonObject.addProperty("title", input.getText().toString());
                                NetClient.createToDo(jsonObject, new NetClient.NetClientListener<JsonObject>() {
                                    @Override
                                    public void dataReady(JsonObject data) {
                                        Log.d(TAG, "DATA IS CREATED!");
                                        MainVM.this.loadData();
                                    }
                                });
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void patchToDo(ToDo todo){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isComplete",!todo.isComplete());
        NetClient.patchToDo(todo.getId(), jsonObject, new NetClient.NetClientListener<JsonObject>() {
            @Override
            public void dataReady(JsonObject data) {
                Log.d(TAG,"PATCHED!");
                MainVM.this.loadData();
            }
        });
    }
}
