package com.example.myapplication.activities.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.datalayer.models.ToDo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TodoAdapter adapter;
    private MainVM mainVM;
    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private FloatingActionButton floatingActionButton;
    private CheckBox checkBox;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainVM = new ViewModelProvider(this).get(MainVM.class);
        mainVM.setUserId(getIntent().getExtras().getString("id"));

        // начальная инициализация списка
        //setInitialData();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        adapter = new TodoAdapter();
        adapter.setDeleteToDoClick(new TodoAdapter.OnItemClick() {
            @Override
            public void onClick(ToDo value) {
                mainVM.deleteToDo(value.getId());
            }
        });
        adapter.setToggleChecked(new TodoAdapter.OnItemClick() {
            @Override
            public void onClick(ToDo value) {
                mainVM.patchToDo(value);
            }
        });



        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        Log.d(TAG,"ADAPTER SETTLED!");
        mainVM
                .initLiveDataList()
                .observe(this, new Observer<List<ToDo>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<ToDo> toDos) {
                        adapter.setData(toDos);
                        Log.d(TAG,"DATA IS CHANGED!");
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
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainVM.createToDo(MainActivity.this);

            }
        }
        );


    }

}