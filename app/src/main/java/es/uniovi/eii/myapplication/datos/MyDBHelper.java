package es.uniovi.eii.myapplication.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * MyDHelper
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * Nombre y version de la base de datos
     */
    private static final String DATABASE_NAME = "virbuds.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLA_USUARIOS = "tabla_usuarios";

    public static final String COLUMNA_USERNAME_USUARIOS = "username_usuarios";
	public static final String COLUMNA_NAME_USUARIOS = "name_usuarios";
    public static final String COLUMNA_CONTRASENA_USUARIOS = "contraseña_usuarios";
    public static final String COLUMNA_BIOGRAFIA_USUARIOS = "biografia_usuarios";
	public static final String COLUMNA_UNIVERSIDAD_USUARIOS = "universidad_usuarios";
    public static final String COLUMNA_CARRERA_USUARIOS = "carrera_usuarios";
    public static final String COLUMNA_CURSO_USUARIOS = "curso_usuarios";
    public static final String COLUMNA_FOTO_USUARIOS = "foto_usuarios";



    public static final String TABLA_SEGUIDOS = "tabla_seguidos";

    public static final String COLUMNA_SEGUIDOR = "username_seguidor";
	public static final String COLUMNA_SEGUIDO = "username_seguido";

    /**
     * Script para crear la base datos en SQL
     */
    private static final String CREATE_TABLA_USUARIOS = "create table if not exists " + TABLA_USUARIOS
            + "( " +
            COLUMNA_USERNAME_USUARIOS + " " + "text primary key, " +
            COLUMNA_NAME_USUARIOS + " text not null, " +
            COLUMNA_CONTRASENA_USUARIOS + " text not null, " +
            COLUMNA_BIOGRAFIA_USUARIOS + " text not null, " +
            COLUMNA_UNIVERSIDAD_USUARIOS + " text not null, " +
            COLUMNA_CARRERA_USUARIOS + " text not null, " +
            COLUMNA_CURSO_USUARIOS + " text not null, " +
            COLUMNA_FOTO_USUARIOS + " text" +
            ");";

    private static final String CREATE_TABLA_SEGUIDOS = "create table if not exists " + TABLA_SEGUIDOS
            + "( " +
            COLUMNA_SEGUIDOR + " " + "text, " +
            COLUMNA_SEGUIDO + " text, " +
            " primary key (" + COLUMNA_SEGUIDOR +", " + COLUMNA_SEGUIDO + ")"
            + ");";

    /**
     * Script para borrar la base de datos (SQL)
     */
    private static final String DATABASE_DROP_USUARIOS = "DROP TABLE IF EXISTS " + TABLA_USUARIOS;
    private static final String DATABASE_DROP_SEGUIDOS = "DROP TABLE IF EXISTS " + TABLA_SEGUIDOS;



    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //invocamos execSQL pq no devuelve ningún tipo de dataset
        db.execSQL(CREATE_TABLA_USUARIOS);
        db.execSQL(CREATE_TABLA_SEGUIDOS);

        Log.i("ONCREATE", "EJECUTO CREACION");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DROP_USUARIOS);
        db.execSQL(DATABASE_DROP_SEGUIDOS);
        this.onCreate(db);

    }
}