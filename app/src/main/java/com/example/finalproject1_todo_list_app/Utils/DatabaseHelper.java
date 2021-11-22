package com.example.finalproject1_todo_list_app.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private SQLiteDatabase db;


    public DatabaseHelper(Context context ) {
        super(context, "TODO_DATABASE.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TODO_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, DATE TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TODO_TABLE");
        onCreate(db);
    }

    public void insertTask(String title, String date, String time){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TASK", title);
        cv.put("DATE", date);
        cv.put("TIME", time);

        long result = db.insert("TODO_TABLE",null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed to add",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added To Do List",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTask(String row_id, String title, String date, String time){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TASK", title);
        cv.put("DATE", date);
        cv.put("TIME", time);

        long result = db.update("TODO_TABLE",cv,"ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to update",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Task updated",Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteTask(String row_id){
        db = this.getWritableDatabase();
        long result = db.delete("TODO_TABLE" , "ID=?" , new String[]{row_id});
        if(result == -1){
            Toast.makeText(context,"Failed to finish",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Task complete",Toast.LENGTH_SHORT).show();
        }


    }

    public Cursor getAllTasks(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery("SELECT * FROM TODO_TABLE",null);
        }
        return cursor;
    }


}

