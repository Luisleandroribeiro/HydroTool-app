package com.example.hydrotool_app;

import android.os.Bundle;
import android.util.Log; // Importa a classe Log
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity"; // Define uma tag para o log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recebe o e-mail passado pela RegisterActivity
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            // Exibe o e-mail na tela
            Toast.makeText(this, "Bem-vindo, " + email, Toast.LENGTH_SHORT).show();
            // Loga o e-mail recebido
            Log.d(TAG, "Email recebido: " + email); // Adiciona o log aqui
        } else {
            Log.d(TAG, "Nenhum e-mail recebido."); // Log se o e-mail for nulo
        }
    }
}
