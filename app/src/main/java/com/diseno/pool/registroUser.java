package com.diseno.pool;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class registroUser extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText NombreUsuario = findViewById(R.id.crearNombreUserEditText);
        EditText crearNip = findViewById(R.id.crearNipEditText);
        EditText confirmarNip = findViewById(R.id.confirmarNipEditText);
        Button registroButton= findViewById(R.id.registroButton);
        Button irAInicioSesion = findViewById(R.id.irAInicioSesion);

        irAInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registroUser.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Usuarios")
                        .whereEqualTo("Nombre",NombreUsuario.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(!task.getResult().isEmpty()){
                                        Toast.makeText(registroUser.this, "Ese usuario ya se encuentra registrado.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if( crearNip.getText().toString().equals(confirmarNip.getText().toString())){
                                            //Toast.makeText(registroUser.this, "Contraseñas igual.", Toast.LENGTH_SHORT).show();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Nombre", NombreUsuario.getText().toString());
                                            user.put("NIP", crearNip.getText().toString());
                                            db.collection("Usuarios")
                                                    .add(user)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            //Toast.makeText(registroUser.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(registroUser.this, inputForm.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(registroUser.this, "Fallo al registrar el usuario.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }else{
                                            Toast.makeText(registroUser.this, "Las Contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
            }
        });


    }
}