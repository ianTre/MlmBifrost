package com.example.bishoppc.pdfaddpager;


import java.sql.Date;

public class Global
{

    private static Global instance;
    private static String userName;

    private static String path;

    private Global(){

    }

    public void setuserName(String name) {
        Global.userName=name;
    }

    public String getuserName() {
        return Global.userName;
    }

    public static String getPath() { return Global.path; }

    public static void setPath(String path) { Global.path = path; }

    public static synchronized Global getInstance(){
        if(instance == null)
            instance=new Global();

        return instance;
    }

}
