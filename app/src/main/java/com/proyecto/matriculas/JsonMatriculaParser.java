package com.proyecto.matriculas;

import android.util.JsonReader;

import com.proyecto.matriculas.model.Matricula;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonMatriculaParser {
    public List<Matricula> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia JsonReader
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            // Leer Array
            return leerArrayMatriculas(reader);
        } finally {
            reader.close();
        }

    }

    public List leerArrayMatriculas(JsonReader reader) throws IOException {
        // Lista temporal
        ArrayList matriculas = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            // Leer objeto
            matriculas.add(leerMatricula(reader));
        }
        reader.endArray();
        return matriculas;
    }

    public Matricula leerMatricula(JsonReader reader) throws IOException {
        Integer N_Registro= null;
        String Infraccion= null;
        String Fecha_Infraccion= null;
        String N_Matricula= null;
        Integer IDPropietariosFK= null;
        Integer IDPropietarios= null;
        String DNI= null;
        String Nombre_Apellidos= null;
        String Direccion= null;
        Integer Telefono= null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "N_Registro":
                    N_Registro = reader.nextInt();
                    break;
                case "Infraccion":
                    Infraccion = reader.nextString();
                    break;
                case "Fecha_Infraccion":
                    Fecha_Infraccion = reader.nextString();
                    break;
                case "N_Matricula":
                    N_Matricula = reader.nextString();
                    break;
                case "IDPropietariosFK":
                    IDPropietariosFK = reader.nextInt();
                    break;
                case "IDPropietarios":
                    IDPropietarios = reader.nextInt();
                    break;
                case "DNI":
                    DNI = reader.nextString();
                    break;
                case "Nombre_Apellidos":
                    Nombre_Apellidos = reader.nextString();
                    break;
                case "Direccion":
                    Direccion = reader.nextString();
                    break;
                case "Telefono":
                    Telefono = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Matricula(N_Registro, Infraccion, Fecha_Infraccion, N_Matricula,IDPropietariosFK, IDPropietarios, DNI, Nombre_Apellidos, Direccion, Telefono );
    }
}
