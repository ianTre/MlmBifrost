package com.example.bishoppc.pdfaddpager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bishoppc.pdfaddpager.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="bd_usuarios";
    private static final int DB_SCHEME_VERSION = 3;

    //public ConexionSQLiteHelper(Context context, "bd_usuarios", null, 3) {
    //public ConexionSQLiteHelper(Context context) {
    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, DB_NAME, null, DB_SCHEME_VERSION);
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Genera las Tablas o Scripts correspondientes de nuestras Entidades
        db.execSQL(Utilidades.CREAR_TABLA_OPERACIONES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Cada vez que ejecutamos nuestra aplicacion, cada vez que la instalamos nuevamente
        // verifica si ya existe antes una version antigua de nuestra base de Datos
        db.execSQL("DROP TABLE IF EXISTS operaciones");
        onCreate(db);
    }

}

