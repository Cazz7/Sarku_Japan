package com.example.cristian.restaurante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alejandro on 24/05/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME = "contactos";
    public static final String CN_ID = "_id";  // Nombre columna
    public static final String CN_NAME = "nombre";
    public static final String CN_LATITUD = "latitud";
    public static final String CN_LONGITUD = "longitud";


    public static final String CREATE_TABLE = "create table "+ TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_LATITUD + " real not null,"
            +  CN_LONGITUD+ " real not null);";

    DbHelper helper;
    SQLiteDatabase db;
    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String Nombre,String latitud,String longitud){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME,Nombre);
        valores.put(CN_LATITUD, latitud);
        valores.put(CN_LONGITUD,longitud);

        return valores;
    }

    public void insertar(String Nombre, String latitud,String longitud){
        db.insert(TABLE_NAME, null, generarContentValues(Nombre,latitud, longitud ));
    }

    public Cursor cargarCursorContactos(){
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

    public Cursor buscarContacto(String Nombre){
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return db.query(TABLE_NAME,columnas,CN_NAME + "=?",new String[]{Nombre},null,null,null);

    }

    public void eliminar(String nombre){
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void Modificardatos(String nombre,String nuevalatitud,String nuevalongitud){
        db.update(TABLE_NAME,generarContentValues(nombre,nuevalatitud,nuevalongitud),CN_NAME+"=?",new String[]{nombre});
    }


}
