package com.example.finalproject1_todo_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnAdd;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    List<ListItem> listItems;

    String judul,waktu,tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.taskRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        adapter = new viewadapter(listItems,this);
        recyclerView.setAdapter(adapter);

        btnAdd = findViewById(R.id.flButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });




    }

    private void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_task);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);
        dialog.show();

        AppCompatButton btnSave = (AppCompatButton) dialog.findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = ((EditText)dialog.findViewById(R.id.textJudul)).getText().toString();
                tanggal = ((EditText)dialog.findViewById(R.id.textTanggal)).getText().toString();
                waktu = ((EditText)dialog.findViewById(R.id.textWaktu)).getText().toString();

                ListItem listItem = new ListItem(
                        judul,
                        tanggal,
                        waktu
                );
                listItems.add(listItem);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }





}