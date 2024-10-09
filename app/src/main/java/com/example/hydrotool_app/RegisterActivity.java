package com.example.hydrotool_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hydrotool_app.api.UserAuthApi;
import com.example.hydrotool_app.domain.UserAuth;
import com.example.hydrotool_app.requests.UserAuthPostRequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, emailEditText; // Mantive o usernameEditText
    private RelativeLayout registerButton; // Alterar para RelativeLayout
    private CheckBox termsCheckbox; // Checkbox para os termos de uso

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Seu layout de registro

        usernameEditText = findViewById(R.id.usernameEditText); // Inicializa o campo de nome de usuário
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        registerButton = findViewById(R.id.registerButton);
        termsCheckbox = findViewById(R.id.termsOfUseCheckBox); // Inicializar o checkbox

        // Adicionar listener ao TextView dentro do RelativeLayout
        TextView registerTextView = registerButton.findViewById(R.id.loginTextView);
        registerTextView.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString(); // Mantive a leitura do nome de usuário
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        // Verifique se os campos não estão vazios antes de prosseguir
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifique se o checkbox de termos de uso está marcado
        if (!termsCheckbox.isChecked()) {
            Toast.makeText(this, "Você deve aceitar os termos de uso.", Toast.LENGTH_SHORT).show();
            return;
        }

        UserAuthPostRequestBody userAuthPostRequestBody = new UserAuthPostRequestBody(username, password, email);
        UserAuthApi userAuthApi = RetrofitClient.getRetrofitInstance().create(UserAuthApi.class);

        Call<UserAuth> call = userAuthApi.registerUser(userAuthPostRequestBody);
        call.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();

                    // Cria um Intent para ir para a MainActivity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("email", email); // Passa o e-mail do usuário logado
                    startActivity(intent);
                    finish(); // Finaliza a atividade de registro
                } else if (response.code() == 409) { // Verifica se o erro é de conflito (e-mail já existente)
                    Toast.makeText(RegisterActivity.this, "E-mail já cadastrado.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Erro no registro: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
