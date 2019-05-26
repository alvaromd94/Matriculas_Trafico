package com.proyecto.matriculas;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.matriculas.model.Matricula;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorDeMatriculas extends ArrayAdapter {



    public AdaptadorDeMatriculas(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View v = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo
            v = inflater.inflate(
                    R.layout.activity_consulta,
                    parent,
                    false);
        }

        //Obteniendo instancias de los elementos
        TextView infraccion = (TextView)v.findViewById(R.id.infraccion);
        TextView fechaInfraccion = (TextView)v.findViewById(R.id.fechaInfraccion);
        TextView nMatricula = (TextView)v.findViewById(R.id.nMatricula);


        //Obteniendo instancia de la Tarea en la posición actual
        Matricula item = (Matricula) getItem(position);
        Log.i("PruebaJson", "PRUEBA --- " +  item.getN_Registro());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getInfraccion());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getFecha_Infraccion());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getN_Matricula());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getIDPropietariosFK());

        infraccion.setText(item.getInfraccion());
        fechaInfraccion.setText(item.getFecha_Infraccion());
        nMatricula.setText(item.getN_Matricula());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getN_Registro());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getInfraccion());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getFecha_Infraccion());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getN_Matricula());
        Log.i("PruebaJson", "PRUEBA --- " +  item.getIDPropietariosFK());



        //Devolver al ListView la fila creada
        return v;

    }

    private int convertirRutaEnId(String nombre){
        Context context = getContext();
        return context.getResources()
                .getIdentifier(nombre, "drawable", context.getPackageName());
    }


}

