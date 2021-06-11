package com.example.myapplication.activities.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.login.LoginVM;
import com.example.myapplication.datalayer.ParseManager;
import com.example.myapplication.datalayer.models.ToDo;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String userId;
    private TodoAdapter adapter;
    private MainVM mainVM;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    //ArrayList<State> states =MainVM.getInstance().getData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = getIntent().getExtras().getString("id");
        mainVM = new ViewModelProvider(this).get(MainVM.class);


        // начальная инициализация списка
       //setInitialData();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        adapter = new TodoAdapter(
//                new TodoAdapter.OnItemClick() {
//                    @Override
//                    public void onClick(String value) {
//                    //MainVM.toggleChecked(String todoId)
//                    }
//                },
//                new TodoAdapter.OnItemClick() {
//                    @Override
//                    public void onClick(String value) {
//                        mainVM.deleteToDo();
//
//                    }
//                }
//
               );
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);

       mainVM
               .getData(userId)
               .observe(this, new Observer<List<ToDo>>() {
                   @Override
                   public void onChanged(List<ToDo> toDos) {
                       adapter.setData(toDos);
                   }
               });

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}