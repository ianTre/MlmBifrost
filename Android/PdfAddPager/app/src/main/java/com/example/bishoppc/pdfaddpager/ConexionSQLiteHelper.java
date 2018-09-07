package com.example.bishoppc.pdfaddpager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.bishoppc.pdfaddpager.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
