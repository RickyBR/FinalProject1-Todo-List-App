package com.example.finalproject1_todo_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.finalproject1_todo_list_app.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerview;
    FloatingActionButton btnAdd;
    DatabaseHelper myDB;
    viewadapter adapter;
    String judul,waktu,tanggal;
    ArrayList<String> task_id,task_title,task_date,task_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.taskRV);
        btnAdd = findViewById(R.id.flButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        myDB = new DatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        task_date = new ArrayList<>();
        task_time = new ArrayList<>();

        displayData();

        adapter = new viewadapter(this,task_id,task_title,task_date,task_time);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

    }

    void displayData(){
        Cursor cursor = myDB.getAllTasks();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_date.add(cursor.getString(2));
                task_time.add(cursor.getString(3));

            }
        }
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_task);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);
        dialog.show();
        Calendar calendar  = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final int t1hour = calendar.get(Calendar.HOUR_OF_DAY);

        final int t1minute = calendar.get(Calendar.MINUTE);

        EditText etDate = dialog.findViewById(R.id.textTanggal);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        EditText etTime = dialog.findViewById(R.id.textWaktu);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
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
        AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.saveButton);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = ((EditText)dialog.findViewById(R.id.textJudul)).getText().toString();
                tanggal = ((EditText)dialog.findViewById(R.id.textTanggal)).getText().toString();
                waktu = ((EditText)dialog.findViewById(R.id.textWaktu)).getText().toString();

                myDB.insertTask(judul,tanggal,waktu);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

}