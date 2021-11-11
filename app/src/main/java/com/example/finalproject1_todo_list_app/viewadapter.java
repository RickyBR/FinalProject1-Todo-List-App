package com.example.finalproject1_todo_list_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class viewadapter extends RecyclerView.Adapter<viewadapter.ViewHolder> {

    private List<ListItem> listItems;

    public viewadapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        ListItem listItem = listItems.get(position);

        holder.textviewHead.setText(listItem.getHead());
    }

    @Override
    public int getItemCount(){
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textviewHead;

        public ViewHolder(View itemView){
            super(itemView);

            textviewHead = (TextView) itemView.findViewById(R.id.teksview);
        }
    }
}
