package com.example.hydrotool_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hydrotool_app.api.UserAuthApi;
import com.example.hydrotool_app.domain.UserAuth;
import com.example.hydrotool_app.requests.UserAuthLoginRequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private RelativeLayout loginButtonLayout;
    private ImageView passwordToggle; // Imagem do olho
    private boolean isPasswordVisible = false; // Estado da visibilidade da senha

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailloginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButtonLayout = findViewById(R.id.loginButtonLayout);
        passwordToggle = findViewById(R.id.passwordToggle); // Inicializa a imagem do olho

        // Adicionar listener ao TextView de login
        TextView loginTextView = findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(v -> loginUser());

        // Adicionar listener ao TextView de registro
        TextView registerTextView = findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Adicionar listener ao ImageView de mostrar/ocultar senha
        passwordToggle.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        UserAuthLoginRequestBody userAuthLoginRequestBody = new UserAuthLoginRequestBody(email, password);
        UserAuthApi userAuthApi = RetrofitClient.getRetrofitInstance().create(UserAuthApi.class);

        Call<UserAuth> call = userAuthApi.loginUser(userAuthLoginRequestBody);
        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                    // Aqui você pode passar o email para a próxima atividade, se necessário
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("email", email); // Passa o email logado
                    startActivity(intent);
                    finish(); // Finaliza a atividade de login
                } else {
                    Log.e("LoginActivity", "Código de status: " + response.code());
                    Log.e("LoginActivity", "Mensagem de erro: " + response.message());
                    Toast.makeText(LoginActivity.this, "Erro no login: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordToggle.setImageResource(R.drawable.hide); // Coloque a imagem do olho fechado
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordToggle.setImageResource(R.drawable.show); // Coloque a imagem do olho aberto
        }
        passwordEditText.setSelection(passwordEditText.getText().length()); // Move o cursor para o final
        isPasswordVisible = !isPasswordVisible; // Alterna o estado
    }
}
