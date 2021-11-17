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

import java.util.List;

public class viewadapter extends RecyclerView.Adapter<viewadapter.ViewHolder> {
    private List<ListItem> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public viewadapter(DatabaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ListItem> mList){
        this.mList = mList;
        notifyDataSetChanged();

    }

    public void deleteTask(int position){
        ListItem item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ListItem item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getHead());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textviewHead;

        public ViewHolder(View itemView){
            super(itemView);

            textviewHead = (TextView) itemView.findViewById(R.id.textJudul);
        }
    }
}