package com.proyecto.matriculas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;


public class DB extends SQLiteOpenHelper {


    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Prueba", factory, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table datos(matricula text, infraccion text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String guardar(String matricula, String infraccion){
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("matricula",matricula);
        contenedor.put("infraccion", infraccion);
        try {
            database.insertOrThrow("datos",null,contenedor);
            mensaje="Ingresado Correctamente";
        }catch (SQLException e){
            mensaje="No Ingresado";
        }
        database.close();
        return mensaje;
    }


    public String[] buscar_reg(String buscar){
        String[] datos= new String[3];
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos WHERE matricula ='"+buscar+"'";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            for(int i = 0 ; i<2;i++){
                datos[i]= registros.getString(i);

            }

            datos[2]="Encontrado";






        }else{

            datos[2]="Esa matrícula no se encuentra en la base de datos";
        }
        database.close();
        return datos;
    }

    public void infraccion(String buscar)
    {
        String infraccion="";
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT infraccion FROM datos WHERE matricula ='"+buscar+"'";
        Cursor c = database.rawQuery(q, null);
        if(c.moveToFirst())
        {

            do {
                infraccion = c.getString(0);
            } while(c.moveToNext());

        }
        database.close();
        App.infraccion=infraccion;

    }



}