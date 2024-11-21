package com.diseno.pool;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class inputForm extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String tipo = "Chisme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_form);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonChisme = findViewById(R.id.buttonChisme);
        Button buttonAnecdota = findViewById(R.id.buttonAnecdota);
        Button buttonConfesion = findViewById(R.id.buttonConfesion);
        EditText titleInput = findViewById(R.id.titleInput);
        EditText contentInput = findViewById(R.id.contentInput);
        Button submitButton = findViewById(R.id.submitButton);
        buttonChisme.setBackgroundColor(Color.parseColor("#8803A9"));
        buttonChisme.setTextColor(Color.WHITE);

        buttonChisme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonChisme.setBackgroundColor(Color.parseColor("#8803A9"));
                buttonChisme.setTextColor(Color.WHITE);
                buttonAnecdota.setBackgroundColor(Color.WHITE);
                buttonAnecdota.setTextColor(Color.BLACK);
                buttonConfesion.setBackgroundColor(Color.WHITE);
                buttonConfesion.setTextColor(Color.BLACK);
                tipo = "Chisme";
            }
        });

        buttonAnecdota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAnecdota.setBackgroundColor(Color.parseColor("#8803A9"));
                buttonAnecdota.setTextColor(Color.WHITE);
                buttonChisme.setBackgroundColor(Color.WHITE);
                buttonChisme.setTextColor(Color.BLACK);
                buttonConfesion.setBackgroundColor(Color.WHITE);
                buttonConfesion.setTextColor(Color.BLACK);
                tipo = "Anécdota";
            }
        });

        buttonConfesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonConfesion.setBackgroundColor(Color.parseColor("#8803A9"));
                buttonConfesion.setTextColor(Color.WHITE);
                buttonAnecdota.setBackgroundColor(Color.WHITE);
                buttonAnecdota.setTextColor(Color.BLACK);
                buttonChisme.setBackgroundColor(Color.WHITE);
                buttonChisme.setTextColor(Color.BLACK);
                tipo = "Confesión";
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleInput.getText().toString().isEmpty() || contentInput.getText().toString().isEmpty()){
                    Toast.makeText(inputForm.this, "No puede haber elementos vacios.", Toast.LENGTH_SHORT).show();
                }else{
                    //agregar a base de datos.
                    Map<String, Object> chisme = new HashMap<>();
                    chisme.put("Contenido", contentInput.getText().toString());
                    chisme.put("Titulo", titleInput.getText().toString());
                    chisme.put("Tipo", tipo);

                    db.collection("Chismes")
                            .add(chisme)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //Toast.makeText(inputForm.this, "Chisme ingresado.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(inputForm.this, feed_principal.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(inputForm.this, "Fallo al guardar.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}