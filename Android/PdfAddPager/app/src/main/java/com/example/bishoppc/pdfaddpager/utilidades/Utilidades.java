package com.example.bishoppc.pdfaddpager.utilidades;

public class Utilidades {
    //Constantes campos Tabla Operaciones
    public static final String TABLA_OPERACIONES = "operaciones";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_LEGAJO = "legajo";
    public static final String CAMPO_NOMBRE_APELLIDO = "nombre";
    public static final String CAMPO_DNI = "dni";
    public static final String CAMPO_PATH = "path";
    public static final String CAMPO_FIRMADO = "firmado";

    public static final String CREAR_TABLA_OPERACIONES = "CREATE TABLE " + TABLA_OPERACIONES
            + "("
            + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CAMPO_FECHA + " DATETIME, "
            + CAMPO_LEGAJO + " TEXT, "
            + CAMPO_NOMBRE_APELLIDO + " TEXT, "
            + CAMPO_DNI + " TEXT, "
            + CAMPO_PATH + " TEXT, "
            + CAMPO_FIRMADO + " BOOLEAN)";
}
