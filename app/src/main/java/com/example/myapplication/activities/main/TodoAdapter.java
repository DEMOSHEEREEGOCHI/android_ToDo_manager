package com.example.myapplication.activities.main;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.datalayer.models.ToDo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoViewHolder> {

    private static final String TAG = TodoAdapter.class.getSimpleName();

    public interface OnItemClick {

        void onClick(ToDo value);
    }

    private List<ToDo> data;
    private OnItemClick toggleChecked;
    private OnItemClick deleteToDoClick;


    public TodoAdapter() {
        this.data = new ArrayList<>();
    }

    public TodoAdapter setToggleChecked(OnItemClick onItemClick) {
        this.toggleChecked = onItemClick;
        return this;
    }

    public TodoAdapter setDeleteToDoClick(OnItemClick onItemClick) {
        this.deleteToDoClick = onItemClick;
        return this;
    }


    @NotNull
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        private final TextView tvId;
        private final TextView tvTitle;
        private final ImageButton btnDelete;
        private final CheckBox checkBox;

        ToDoViewHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.id);
            tvTitle = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        void bind(ToDo item) {
            Log.d(TAG, "TITLE:" + item.getTitle());
            tvTitle.setText(item.getTitle());
            tvId.setText(item.getId());
            checkBox.setChecked(item.isComplete());
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteToDoClick.onClick(item);
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleChecked.onClick(item);
                }
            });

        }

    }
}