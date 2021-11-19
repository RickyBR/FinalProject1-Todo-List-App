package com.example.finalproject1_todo_list_app.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
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
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added To Do List",Toast.LENGTH_SHORT).show();
        }


    }

    public void updateTask(int id , String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }


    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete("TODO_TABLE" , "ID=?" , new String[]{String.valueOf(id)});
    }

    public Cursor getAllTasks(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery("SELECT * FROM TODO_TABLE",null);
        }
//        db.beginTransaction();
//        try {
//            cursor = db.query("TODO_TABLE" , null , null , null , null , null , null);
//            if (cursor !=null){
//                if (cursor.moveToFirst()){
//                    do {
//                        ListItem task = new ListItem();
//                        task.setId(cursor.getInt(cursor.getColumnIndex("ID")));
//                        task.setHead(cursor.getString(cursor.getColumnIndex("TASK")));
//                        modelList.add(task);
//
//                    }while (cursor.moveToNext());
//                }
//            }
//        }finally {
//            db.endTransaction();
//            cursor.close();
//        }
        return cursor;
    }

}
