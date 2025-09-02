
package com.example.loginjava.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.loginjava.databinding.ActivityLoginBinding; // This is generated from your activity_login.xml file.


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setupListeners();
        observeUiState();
    }


    private void setupListeners() {

        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onEmailChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onPasswordChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        binding.loginButton.setOnClickListener(v -> viewModel.handleLogin());


        binding.registerButton.setOnClickListener(v -> {
            // TODO: Kod za registraciju Ako je potrebna
            Toast.makeText(LoginActivity.this, "Register button clicked", Toast.LENGTH_SHORT).show();
        });
    }


    private void observeUiState() {
        viewModel.getUiState().observe(this, state -> {
            binding.loginButton.setEnabled(!state.isLoading());
            binding.emailInputLayout.setError(state.getEmailError());
            binding.passwordInputLayout.setError(state.getPasswordError());

            if (state.isLoginSuccess()) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                // TODO: NAVIGACIJA dALJE
            }
        });
    }
}