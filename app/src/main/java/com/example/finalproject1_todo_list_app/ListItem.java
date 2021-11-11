package com.example.finalproject1_todo_list_app;

public class ListItem {

    private String head;
    private String tanggal;
    private String waktu;

    public ListItem(String head,String tanggal,String waktu){
        this.head = head;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getHead(){
        return head;
    }

    public String getTanggal(){
        return tanggal;
    }

    public String getWaktu(){
        return waktu;
    }
}
