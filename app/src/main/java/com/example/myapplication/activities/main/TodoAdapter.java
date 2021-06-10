package com.example.myapplication.activities.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.datalayer.models.ToDo;

import java.util.ArrayList;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoViewHolder> {



    public interface OnItemClick {
        void onClick(String value);
    }

    private List<ToDo> data;
    private OnItemClick toggleChecked;
    private OnItemClick deleteToDo;


    public TodoAdapter(OnItemClick toggleChecked,OnItemClick deleteToDo) {
        this.data = new ArrayList<>();
        this.toggleChecked = toggleChecked;
        this.deleteToDo = deleteToDo;
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

        private TextView tvTitle;
        private ImageButton btnDelete;
        private CheckBox checkBox;

        ToDoViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        void bind( ToDo item) {

                tvTitle.setText(ToDo.getTitle());
                checkBox.callOnClick();
                btnDelete.setOnClickListener(v -> {

                });


        }

    }
}