package com.example.myapplication.activities.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String userId;
    ArrayList<State> states = new ArrayList<State>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = getIntent().getExtras().getString("id");
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
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
    private void setInitialData(){
        for (int i = 0; i < 20; i++) {
            states.add(new State ("Бразилия", "Бразилиа", R.drawable.brazilia));
            states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.brazilia));
            states.add(new State ("Колумбия", "Богота", R.drawable.brazilia));
            states.add(new State ("Уругвай", "Монтевидео", R.drawable.brazilia));
            states.add(new State ("Чили", "Сантьяго",R.drawable.brazilia));
        }

    }
}