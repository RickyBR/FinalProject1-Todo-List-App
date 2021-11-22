package com.example.finalproject1_todo_list_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject1_todo_list_app.Utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class viewadapter extends RecyclerView.Adapter<viewadapter.ViewHolder> {
    Context context;
    ArrayList<String> task_id,task_title,task_date,task_time;
    DatabaseHelper myDB;
    String id,judul,tanggal,waktu;
    EditText etTitle,etDate,etTime;

    public viewadapter(Context context, ArrayList<String> task_id,
                       ArrayList<String> task_title,
                       ArrayList<String> task_date,
                       ArrayList<String> task_time){
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_date = task_date;
        this.task_time = task_time;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.Id.setText(String.valueOf(task_id.get(position)));
        holder.Task.setText(String.valueOf(task_title.get(position)));
        holder.Date.setText(String.valueOf(task_date.get(position)));
        holder.Time.setText(String.valueOf(task_time.get(position)));
        myDB = new DatabaseHelper(context);
        holder.Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = holder.Id.getText().toString();
                myDB.deleteTask(id);
                notifyDataSetChanged();


            }

        });
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_task);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);
                dialog.show();

                id = holder.Id.getText().toString();
                etTitle = dialog.findViewById(R.id.textJudul2);
                etDate = dialog.findViewById(R.id.textTanggal2);
                etTime = dialog.findViewById(R.id.textWaktu2);

                etTitle.setText(String.valueOf(task_title.get(position)));
                etDate.setText(String.valueOf(task_date.get(position)));
                etTime.setText(String.valueOf(task_time.get(position)));

                Calendar calendar  = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                final int t1hour = calendar.get(Calendar.HOUR_OF_DAY);

                final int t1minute = calendar.get(Calendar.MINUTE);

                EditText etDate = dialog.findViewById(R.id.textTanggal2);
                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month+1;
                                String date = day+"/"+month+"/"+year;
                                etDate.setText(date);
                            }
                        },year,month,day);
                        datePickerDialog.show();
                    }
                });
                EditText etTime = dialog.findViewById(R.id.textWaktu2);
                etTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                context,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        //Initialize Calendar
                                        calendar.set(0,0,0,hourOfDay,minute);
                                        //Set selected time on text view
                                        etTime.setText(DateFormat.format("hh:mm aa",calendar));
                                    }
                                },12,0,false
                        );
                        //Displayed previus selected time
                        timePickerDialog.updateTime(t1hour,t1minute);
                        //Show dialog
                        timePickerDialog.show();
                    }
                });
                AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.saveEdit);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        judul = ((EditText)dialog.findViewById(R.id.textJudul2)).getText().toString();
                        tanggal = ((EditText)dialog.findViewById(R.id.textTanggal2)).getText().toString();
                        waktu = ((EditText)dialog.findViewById(R.id.textWaktu2)).getText().toString();
                        myDB.updateTask(id,judul,tanggal,waktu);
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Id,Task, Date, Time;
        AppCompatButton Finish;
        LinearLayout mainLayout;

        public ViewHolder(View itemView){
            super(itemView);
            Id = (TextView) itemView.findViewById(R.id.id);
            Task = (TextView) itemView.findViewById(R.id.task);
            Date = (TextView) itemView.findViewById(R.id.tanggal);
            Time = (TextView) itemView.findViewById(R.id.jam);
            Finish = itemView.findViewById(R.id.btn_finish);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }


}