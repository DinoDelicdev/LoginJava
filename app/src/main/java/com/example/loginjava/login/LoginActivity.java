
package com.example.loginjava.login;

// These are the necessary imports from the Android framework.
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.loginjava.databinding.ActivityLoginBinding; // This is generated from your activity_login.xml file.

// This is your Activity class. It controls the login screen.
public class LoginActivity extends AppCompatActivity {

    // This variable holds direct references to your UI elements (buttons, inputs).
    private ActivityLoginBinding binding;

    // This variable will hold the reference to your ViewModel, which contains the logic.
    private LoginViewModel viewModel;

    // This is the first function that runs when your screen is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line "inflates" your XML layout into usable Java objects.
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // This line tells the app to show the layout we just inflated.
        setContentView(binding.getRoot());

        // This is the Java replacement for Kotlin's 'by viewModels()'.
        // It correctly gets or creates the ViewModel for this activity.
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Call the helper methods to set up listeners and observers.
        setupListeners();
        observeUiState();
    }

    // This method sets up all the "event listeners" for your UI.
    private void setupListeners() {
        // This listens for every character change in the email field.
        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // When the text changes, we tell the ViewModel.
                viewModel.onEmailChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Same thing for the password field.
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

        // This sets the click listener for the login button.
        binding.loginButton.setOnClickListener(v -> viewModel.handleLogin());

        // This sets the click listener for the register button.
        binding.registerButton.setOnClickListener(v -> {
            // TODO: Put your code to handle registration here.
            Toast.makeText(LoginActivity.this, "Register button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    // This method sets up the "observer" that watches for changes from the ViewModel.
    // This is the Java replacement for your 'lifecycleScope.launch' and 'collect' block.
    private void observeUiState() {
        viewModel.getUiState().observe(this, state -> {
            // 'state' is the LoginUiState object that tells us what to show.
            // This code runs every time the state changes in the ViewModel.

            // If isLoading is true, the button is disabled. If false, it's enabled.
            binding.loginButton.setEnabled(!state.isLoading());

            // Set the error messages on the input layouts. If the error is null, the message disappears.
            binding.emailInputLayout.setError(state.getEmailError());
            binding.passwordInputLayout.setError(state.getPasswordError());

            // If the login was a success, show a pop-up message.
            if (state.isLoginSuccess()) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                // TODO: Navigate to the next screen here.
            }
        });
    }
}