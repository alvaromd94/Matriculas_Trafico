package com.proyecto.matriculas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.matriculas.model.Matricula;
import com.proyecto.matriculas.model.Usuarios;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private String APIserver = "https://proyectomatriculas.com/proyecto/";

    EditText editTextUsuario;
    EditText editTextPassword;
    Button btnInicio;
    Intent menuPrincipal;

    // String usuario = editTextUsuario.getText().toString();
    //String contrasena = editTextPassword.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        menuPrincipal = new Intent(this, MenuPrincipalActivity.class);
        editTextUsuario =  findViewById(R.id.editTextUsuario);
        editTextPassword =  findViewById(R.id.editTextPassword);

        btnInicio = findViewById(R.id.btnInicio);



        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PruebaJson().execute("login.php?Usuario=" + "'" + editTextUsuario.getText().toString() + "'" + "&Contrasena=" +  "'" + editTextPassword.getText().toString() + "'");





            }
        });

    }
    public void onLoginSuccess() {
        btnInicio.setEnabled(true);
        finish();
    }


    //  public void cickInicio(View view) {
    //  if (editTextUsuario.length() > 0) {
    //     Integer id = Integer.parseInt(editTextUsuario.getText().toString());
    //    new ConsultaActivity.PruebaJson().execute("obtenermatriculas.php?id=" + id);
    //  } else {
    //   new ConsultaActivity.PruebaJson().execute("get-list-products.php");
    //   }
//
    //      new PruebaJson().execute("login.php?Usuario=" + editTextUsuario.getText().toString() + "&Contrasena=" + editTextPassword.getText().toString());
    //startActivity(new Intent(getApplicationContext(), MenuPrincipalActivity.class));
    //  }

    // public void cickLimpiar(View view) {
    //     editTextUsuario.setText("");
    //     editTextPassword.setText("");
    //     //startActivity(new Intent(getApplicationContext(), ConsultaActivity.class));
    // }
    public void meterMatricula(View view)
    {
        DB db= new DB(getApplicationContext(),null,null,1);
        String nombre = editTextUsuario.getText().toString();
        String apellido = editTextPassword.getText().toString();
        String mensaje =db.guardar(nombre, apellido);
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();

    }
    public class PruebaJson extends AsyncTask<String, Void, Boolean> {

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
        public void onLoginFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            btnInicio.setEnabled(true);
        }

        protected void onPostExecute(Boolean inicioCorrecto) {
            try {
                if (inicioCorrecto) {
                    JSONArray response = new JSONArray(json);

                    List<Usuarios> lista = new ArrayList<Usuarios>();
                    for (int i = 0; i < response.length(); i++) {
                        lista.add(new Usuarios(
                                response.getJSONObject(i).getInt("IDUsuario")
                                , response.getJSONObject(i).getString("Usuario")
                                , response.getJSONObject(i).getString("Contrasena")
                        ));
                    }
                    if(!lista.isEmpty())
                    {

                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Comprobando...");
                        progressDialog.show();

                        String email = editTextUsuario.getText().toString();
                        String password = editTextPassword.getText().toString();

                        // TODO: Implement your own authentication logic here.

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        startActivity(menuPrincipal);
                                        finish();
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        onLoginSuccess();
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                        //System.out.println("Ha entrado");
                        //Log.i("datos", "Se escribe:" + usuarios.get(0).getIDUsuario());
                    }
                    else{
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Comprobando...");
                        progressDialog.show();

                        String email = editTextUsuario.getText().toString();
                        String password = editTextPassword.getText().toString();

                        // TODO: Implement your own authentication logic here.

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.inicioNoOk),  Toast.LENGTH_LONG).show();

                                        // On complete call either onLoginSuccess or onLoginFailed
                                        //onLoginSuccess();
                                        onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);

                    }
                }
            } catch (JSONException e) {
                System.out.println("FALLO: " + e.getMessage());
            }
        }



    }
}

