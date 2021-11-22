package com.example.finalproject1_todo_list_app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject1_todo_list_app.Utils.DatabaseHelper;

import java.text.SimpleDateFormat;
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
        holder.Id.setText(task_id.get(position));
        holder.Task.setText(task_title.get(position));
        holder.Date.setText(task_date.get(position));
        holder.Time.setText(task_time.get(position));
        myDB = new DatabaseHelper(context);
        holder.Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = holder.Id.getText().toString();
                myDB.deleteTask(id);
                refresh();
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
                final int t1hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int t1minute = calendar.get(Calendar.MINUTE);

                EditText etDate = dialog.findViewById(R.id.textTanggal2);
                EditText etTime = dialog.findViewById(R.id.textWaktu2);

                DatePickerDialog.OnDateSetListener Date= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,dd MMM,yyyy");
                        etDate.setText(sdf.format(calendar.getTime()));
                    }
                };
                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(context,Date,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                etTime.setTextIsSelectable(false);

                etTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etDate.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Input date", Toast.LENGTH_SHORT).show();
                        } else {
                            TimePickerDialog timePickerDialog = new TimePickerDialog(
                                    context, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            calendar.set(0, 0, 0, hourOfDay, minute);
                                            etTime.setText(DateFormat.format("hh:mm aa", calendar));
                                        }
                                    }, 12, 0, false
                            );
                            timePickerDialog.updateTime(t1hour, t1minute);
                            timePickerDialog.show();
                        }
                    }
                });
                AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.saveEdit);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        judul = ((EditText)dialog.findViewById(R.id.textJudul2)).getText().toString();
                        tanggal = ((EditText)dialog.findViewById(R.id.textTanggal2)).getText().toString();
                        waktu = ((EditText)dialog.findViewById(R.id.textWaktu2)).getText().toString();
                        if(judul.isEmpty()){
                            Toast.makeText(context,"Task title is empty",Toast.LENGTH_SHORT).show();
                        }else {
                            myDB.updateTask(id, judul, tanggal, waktu);
                            refresh();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    void refresh() {
        Activity activity = (Activity) context;
        Intent intent = new Intent(context,MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(0,0);

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