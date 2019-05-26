package com.proyecto.matriculas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.proyecto.matriculas.model.Matricula;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ConsultaActivity extends AppCompatActivity {

    private String APIserver = "http://proyectomatriculas.com/proyecto/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
    }

    /*public void clicBuscar(View view) {
        EditText txtidRegistro = findViewById(R.id.txtIdRegistro);
        if (txtidRegistro.length() > 0) {
            Integer id = Integer.parseInt(txtidRegistro.getText().toString());
            new PruebaJson().execute("busqueda.php?id=" + id);
        } else {
            new PruebaJson().execute("busqueda.php");
        }
    }*/

    private class PruebaJson extends AsyncTask<String, Void, Boolean> {

        private String json;

        protected Boolean doInBackground(String... urls) {
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(APIserver + urls[0]));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
                return false;
            }
            return true;
        }

        /*protected void onPostExecute(Boolean isOk) {
            try {
                if (isOk) {
                    JSONArray response = new JSONArray(json);

                    List<Matricula> lst = new ArrayList<Matricula>();
                    for (int i = 0; i < response.length(); i++) {
                        lst.add(new Matricula(
                                response.getJSONObject(i).getInt("N_Registro")
                                , response.getJSONObject(i).getString("Infraccion")
                                , response.getJSONObject(i).getString("Fecha_Infraccion")
                                , response.getJSONObject(i).getString("N_Matricula")
                                , response.getJSONObject(i).getInt("IDPropietariosFK")
                        ));
                    }

                    StringBuilder result = new StringBuilder();
                    for (Matricula p : lst) {
                        result.append(p.toString() + "\n");
                    }

                    TextView txtResult = findViewById(R.id.txtResult);
                    txtResult.setText(result);
                }

            } catch (Exception e) {
                System.out.println("FALLO: " + e.getMessage());
            }
        }*/
    }
}
