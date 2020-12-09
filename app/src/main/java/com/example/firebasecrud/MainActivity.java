package com.example.firebasecrud;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etAPaterno, etAMaterno, etSexo, etDireccion, etFacebook, etInstagram, etTelefono;
    FloatingActionButton fabGuardar, fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateId, updateNombre, updateApaterno, updateAmaterno, updateSexo, updateDireccion, updateFacebook, updateInstagram, updateTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etAPaterno = findViewById(R.id.etAPaterno);
        etAMaterno = findViewById(R.id.etAMaterno);
        etSexo = findViewById(R.id.etSexo);
        etDireccion = findViewById(R.id.etDireccion);
        etFacebook = findViewById(R.id.etFacebook);
        etInstagram = findViewById(R.id.etInstagram);
        etTelefono = findViewById(R.id.etTelefono);

        fabGuardar = findViewById(R.id.fabGuardar);
        fabListar = findViewById(R.id.fabListar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateId = bundle.getString("updateId");
            updateNombre = bundle.getString("updateNombre");
            updateApaterno = bundle.getString("updateApaterno");
            updateAmaterno = bundle.getString("updateAmaterno");
            updateSexo = bundle.getString("updateSexo");
            updateDireccion = bundle.getString("updateDireccion");
            updateFacebook = bundle.getString("updateFacebook");
            updateInstagram = bundle.getString("updateInstagram");
            updateTelefono = bundle.getString("updateTelefono");

            etNombre.setText(updateNombre);
            etAPaterno.setText(updateApaterno);
            etAMaterno.setText(updateAmaterno);
            etSexo.setText(updateSexo);
            etDireccion.setText(updateDireccion);
            etFacebook.setText(updateFacebook);
            etInstagram.setText(updateInstagram);
            etTelefono.setText(updateTelefono);

        } else {
            actionBar.setTitle("Agregar");
        }


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = updateId;
                    String nombre = etNombre.getText().toString().trim();
                    String apaterno = etAPaterno.getText().toString().trim();
                    String amaterno = etAMaterno.getText().toString().trim();
                    String sexo = etSexo.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String facebook = etFacebook.getText().toString().trim();
                    String instagram = etInstagram.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();

                    actualizarDatos(id, nombre, apaterno, amaterno, sexo, direccion, facebook, instagram, telefono);

                } else {
                    String nombre = etNombre.getText().toString().trim();
                    String apaterno = etAPaterno.getText().toString().trim();
                    String amaterno = etAMaterno.getText().toString().trim();
                    String sexo = etSexo.getText().toString().trim();
                    String direccion = etDireccion.getText().toString().trim();
                    String facebook = etFacebook.getText().toString().trim();
                    String instagram = etInstagram.getText().toString().trim();
                    String telefono = etTelefono.getText().toString().trim();

                    cargarDatos(nombre, apaterno, amaterno, sexo, direccion, facebook, instagram, telefono);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityPerson.class));
                finish();
            }
        });

    }


    private void cargarDatos(String nombre, String apaterno, String amaterno, String sexo, String direccion, String facebook, String instagram, String telefono) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("apaterno", apaterno);
        doc.put("amaterno", amaterno);
        doc.put("sexo", sexo);
        doc.put("direccion", direccion);
        doc.put("facebook", facebook);
        doc.put("instagram", instagram);
        doc.put("telefono", telefono);


        db.collection("Documents").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String id, String nombre, String apaterno, String amaterno, String sexo, String direccion, String facebook, String instagram, String telefono) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();

        /*
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("apaterno", apaterno);
        doc.put("amaterno", amaterno);
        doc.put("sexo", sexo);
        doc.put("direccion", direccion);
        doc.put("facebook", facebook);
        doc.put("instagram", instagram);
        doc.put("telefono", telefono);

         */


        db.collection("Documents")
                .document(id).update(
                "nombre", nombre,
                "apaterno", apaterno,
                "amaterno", amaterno,
                "sexo", sexo,
                "direccion", direccion,
                "facebook", facebook,
                "instagram", instagram,
                "telefono", telefono
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Actualización exitosa...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}