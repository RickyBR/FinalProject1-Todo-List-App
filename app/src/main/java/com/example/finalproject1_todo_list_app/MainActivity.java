package com.example.finalproject1_todo_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_date.add(cursor.getString(2));
                task_time.add(cursor.getString(3));
        }
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_task);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);
        dialog.show();
        Calendar calendar  = Calendar.getInstance();

        EditText etTime = dialog.findViewById(R.id.textWaktu2);
        EditText etDate = dialog.findViewById(R.id.textTanggal2);

        final int t1hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int t1minute = calendar.get(Calendar.MINUTE);

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
                new DatePickerDialog(MainActivity.this,Date,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etTime.setTextIsSelectable(false);

        etTime.setOnClickListener(view -> {
            if(etDate.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Input date",Toast.LENGTH_SHORT).show();
            }else {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
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
        });


        AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.saveEdit);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = ((EditText)dialog.findViewById(R.id.textJudul2)).getText().toString();
                tanggal = ((EditText)dialog.findViewById(R.id.textTanggal2)).getText().toString();
                waktu = ((EditText)dialog.findViewById(R.id.textWaktu2)).getText().toString();
                if(judul.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Task title is empty",Toast.LENGTH_SHORT).show();
                }else{
                    myDB.insertTask(judul,tanggal,waktu);
                    refresh();
                    dialog.dismiss();
                }
            }
        });
    }


    public void refresh(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}