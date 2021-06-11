package com.example.myapplication.activities.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activities.login.LoginVM;
import com.example.myapplication.datalayer.NetClient;
import com.example.myapplication.datalayer.models.ToDo;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoViewHolder> {

    private static final String TAG = TodoAdapter.class.getSimpleName();

//    public interface OnItemClick {
//        void onClick(String value);
//    }

    private List<ToDo> data;
   // private OnItemClick toggleChecked;
   // private OnItemClick deleteToDo;


    public TodoAdapter() {
        this.data = new ArrayList<>();
//        this.toggleChecked = toggleChecked;
//        this.deleteToDo = deleteToDo;
    }



    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ToDo> newData) {
        if (data != null) {

            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            // first initialization
            data = newData;
        }
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId;
        private TextView tvTitle;
        private ImageButton btnDelete;
        private CheckBox checkBox;

        ToDoViewHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.id);
            tvTitle = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        void bind( ToDo item) {
                Log.d(TAG,"TITLE:"+item.getTitle());
                tvTitle.setText(item.getTitle());
                tvId.setText(item.getId());
                checkBox.callOnClick();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      String id =  tvId.getText().toString();
                      NetClient.deleteToDoById(id, new NetClient.NetClientListener<JsonObject>() {
                          @Override
                          public void dataReady(JsonObject data) {
                              Log.d(TAG,"DELETED!");
                          }
                      });
                    }
                });


        }

    }
}