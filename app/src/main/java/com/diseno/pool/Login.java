package com.diseno.pool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button iniciarSesionButton = findViewById(R.id.iniciarSesionButton);
        TextView nombreUserEditText = findViewById(R.id.nombreUserEditText);
        TextView nipEditText = findViewById(R.id.nipEditText);
        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Usuarios")
                        .whereEqualTo("Nombre",nombreUserEditText.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(!task.getResult().isEmpty()){
                                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                        String nipUsuario = document.getString("NIP");
                                        if(nipUsuario.equals(nipEditText.getText().toString())){
                                            Intent intent = new Intent(Login.this, WelcomeScreen.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            //mensaje de error, contraseña incorrecta
                                        }
                                    }
                                } else {
                                    //mensaje de error: no se encontro el usuario
                                    //Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }
}