package com.example.finalproject1_todo_list_app;

public class ListItem {
    private int id;
    private String head;
    private String tanggal;
    private String waktu;


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setWaktu(String waktu) {
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
