package com.example.trabajo_clase_preferencias_compartidas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //CREAMOS LOS OBJETOS DE LA INTERFAZ GRAFICA
    EditText txtNombre, txtDni, txtEdad;
    TextView mensaje;

    //DEFINIMOS LA PROPEIDAD DE LAS PREFERENCIAS COMPARTIDAS
    private SharedPreferences myPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREAMOS EL METODO PARA ASIGNAR EL ID CON EL ELEMENTO DE LA INTERFAZ
        iniciarInterfaz();
    }

    private void iniciarInterfaz() {
        //VAMOS A ASIGANAR EL ID DE LOS ELEMNTOS DE LA INTERFAZ CON LAS PROPIEDADES DEFINIDAS
        txtNombre =findViewById(R.id.txtNombre);
        txtDni =findViewById(R.id.txtDni);
        txtEdad = findViewById(R.id.txtEdad);
        mensaje = findViewById(R.id.txtMensaje);
    }

    public void GuardarPref(View view) {
        try {
            // Verificar si los valores ya existen en las preferencias compartidas
            if (validarPreferencias()) {
                Toast.makeText(getApplicationContext(), "Los valores ya existen en las preferencias compartidas", Toast.LENGTH_LONG).show();
                return; // No guardar los valores en las preferencias compartidas
            }

            // Guardar los valores en las preferencias compartidas
            myPref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPref.edit();
            editor.putString("usuario", txtNombre.getText().toString().toUpperCase());
            editor.putString("dni", txtDni.getText().toString());
            editor.putString("edad", txtEdad.getText().toString());
            editor.apply();

            String myName = myPref.getString("usuario", "no exite");
            String myDni = myPref.getString("dni", "no exite");
            String myEdad = myPref.getString("edad", "no exite");

            // Dar formato al texto que se retorna en el TextView
            String mensajeGuardado = ("Sus datos son: \n" +
                    "Nombre: " + myName + "\n" +
                    "Identificacion: " + myDni + "\n" +
                    "Edad: " + myEdad + " años");

            mensaje.setText(mensajeGuardado);
        } catch (Exception ex) {
            // Mostrar una alerta por pantalla con el error
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //CREAMOS UNA VALIDACION PARA LOS DATOS
    private boolean validarPreferencias() {
        //CONFIGURAMOS LA VARIABLE EN LA CUAL SE ENCUENTRAN LAS PREFERENCIAS COMPARTIDAS
        myPref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        //SE OPTIENEN LOS DATOS INGRESADOS POR EL USUARIO
        String nombre = txtNombre.getText().toString().toUpperCase();
        String dni = txtDni.getText().toString();
        String edad = txtEdad.getText().toString();

        // COMPROBAMOS QUE LOS DATOS QUE SE ENVIEN NO EXISTAN PREVIAMENTE
        if (myPref.getString("usuario", "").equals(nombre) &&
                myPref.getString("dni", "").equals(dni) &&
                myPref.getString("edad", "").equals(edad)) {
            return true;
        } else {
            return false;
        }
    }

    //CREAMOS LA FUNCION DE CONSULTAR LOS DATOS
    public void consultarDatos(View view) {
        //CONFIGURAMOS LA VARIABLE EN LA CUAL SE ENCUENTRAN LAS PREFERENCIAS COMPARTIDAS
        myPref = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        //SE OPTIENEN LOS DATOS INGRESADOS POR EL USUARIO
        String dni = txtDni.getText().toString();

        // COMPROBAMOS QUE LOS DATOS QUE SE ENVIEN NO EXISTAN PREVIAMENTE
        if (myPref.getString("dni", "").equals(dni)) {
            String myName = myPref.getString("usuario", "no exite");
            String myDni = myPref.getString("dni", "no exite");
            String myEdad = myPref.getString("edad", "no exite");

            // Dar formato al texto que se retorna en el TextView
            String mensajeGuardado = ("Ya existen este usuario y sus datos son: \n" +
                    "Nombre: " + myName + "\n" +
                    "Identificacion: " + myDni + "\n" +
                    "Edad: " + myEdad + " años");
            mensaje.setText(mensajeGuardado);
        } else {
            String mensajeGuardado = "No existe ningun usuario con este Dni";
            mensaje.setText(mensajeGuardado);
        }
    }

}