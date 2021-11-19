package com.example.finalproject1_todo_list_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject1_todo_list_app.Utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class viewadapter extends RecyclerView.Adapter<viewadapter.ViewHolder> {
    Context context;
    ArrayList task_id,task_title,task_date,task_time;
    MainActivity activity;
    DatabaseHelper myDB;

    public viewadapter(Context context, ArrayList task_id,
                       ArrayList task_title,
                       ArrayList task_date,
                       ArrayList task_time){
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_date = task_date;
        this.task_time = task_time;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        holder.Task.setText(String.valueOf(task_title.get(position)));
        holder.Date.setText(String.valueOf(task_date.get(position)));
        holder.Time.setText(String.valueOf(task_time.get(position)));
    }

//    public boolean toBoolean(int num){
//        return num!=0;
//    }
//
//    public Context getContext(){
//        return activity;
//    }
//
//    public void setTasks(List<ListItem> mList){
//        this.mList = mList;
//        notifyDataSetChanged();
//
//    }
//
//    public void deleteTask(int position){
//        ListItem item = mList.get(position);
//        myDB.deleteTask(item.getId());
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void editItem(int position){
//        ListItem item = mList.get(position);
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("id" , item.getId());
//        bundle.putString("task" , item.getHead());
//
//        AddNewTask task = new AddNewTask();
//        task.setArguments(bundle);
//        task.show(activity.getSupportFragmentManager() , task.getTag());
//
//    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Task, Date, Time;

        public ViewHolder(View itemView){
            super(itemView);

            Task = (TextView) itemView.findViewById(R.id.task);
            Date = (TextView) itemView.findViewById(R.id.tanggal);
            Time = (TextView) itemView.findViewById(R.id.jam);
        }
    }
}