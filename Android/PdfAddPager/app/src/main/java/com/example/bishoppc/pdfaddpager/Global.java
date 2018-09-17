package com.example.bishoppc.pdfaddpager;

public class Global
{

    private static Global instance;
    private static String username;
    private static String pathArchGenerado;
    private static String pathPrograma;

    private Global(){ }

    public void setuserName(String name) {
        Global.username = name;
    }

    public String getuserName() {
        return Global.username;
    }

    public String getPathArchGenerado() { return Global.pathArchGenerado; }

    public void setPathArchGenerado(String path) { Global.pathArchGenerado = path; }

    public String getPathPrograma() { return pathPrograma; }

    public void setPathPrograma(String pathPrograma) { Global.pathPrograma = pathPrograma; }

    public static synchronized Global getInstance(){
        if(instance == null)
            instance=new Global();

        return instance;
    }

}
