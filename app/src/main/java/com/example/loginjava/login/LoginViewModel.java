package com.example.loginjava.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loginjava.network.ApiService;
import com.example.loginjava.network.LoginRequest; // We will create this next
import com.example.loginjava.network.LoginResponse; // We will create this next
import com.example.loginjava.network.RetrofitClient; // We will create this next

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    // We use MutableLiveData to hold the state, which the Activity can observe.
    private final MutableLiveData<LoginUiState> _uiState = new MutableLiveData<>(new LoginUiState());

    // This is the public, non-changeable version of the state that the Activity will see.
    public LiveData<LoginUiState> getUiState() {
        return _uiState;
    }

    // This assumes you have a RetrofitClient setup. We'll add a placeholder for it.
    private final ApiService apiService = RetrofitClient.getApiService();

    // Called when the user types in the email field.
    public void onEmailChanged(String email) {
        LoginUiState currentState = _uiState.getValue();
        if (currentState != null) {
            // In Java, we create a new state object with the updated email.
            LoginUiState newState = new LoginUiState(email, currentState.getPassword(), null, currentState.getPasswordError(), currentState.isLoading(), false);
            _uiState.postValue(newState);
        }
    }

    // Called when the user types in the password field.
    public void onPasswordChanged(String password) {
        LoginUiState currentState = _uiState.getValue();
        if (currentState != null) {
            LoginUiState newState = new LoginUiState(currentState.getEmail(), password, currentState.getEmailError(), null, currentState.isLoading(), false);
            _uiState.postValue(newState);
        }
    }

    // This is the Java translation of your 'validateInputs' function. Logic is the same.
    private boolean validateInputs() {
        LoginUiState currentState = _uiState.getValue();
        if (currentState == null) return false;

        String email = currentState.getEmail();
        String password = currentState.getPassword();

        // Android's built-in email pattern validator.
        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 6;

        String emailError = null;
        String passwordError = null;

        if (!isEmailValid) {
            emailError = "Uneseni email nije u pravom formatu";
        }
        if (!isPasswordValid) {
            passwordError = "Šifra mora biti duga najmanje 6 karaktera";
        }

        // If there are errors, update the UI state and return false.
        if (emailError != null || passwordError != null) {
            _uiState.postValue(new LoginUiState(email, password, emailError, passwordError, false, false));
            return false;
        }

        return true;
    }

    // This is the translation of your 'handleLogin' function.
    public void handleLogin() {
        if (!validateInputs()) {
            return;
        }

        LoginUiState currentState = _uiState.getValue();
        if (currentState == null) return;

        // Set loading state to true.
        _uiState.postValue(new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, null, true, false));

        // Create the request object for the API call.
        LoginRequest request = new LoginRequest(currentState.getEmail(), currentState.getPassword());

        // This is how you do an asynchronous network call with Retrofit in Java, using enqueue with a Callback.
        // It's the replacement for viewModelScope.launch.
        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Success!
                    String token = response.body().getToken();
                    // TODO: Save the token securely
                    LoginUiState successState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, null, false, true);
                    _uiState.postValue(successState);
                } else {
                    // API returned an error (e.g., 401 Unauthorized)
                    LoginUiState errorState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, "Invalid credentials", false, false);
                    _uiState.postValue(errorState);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Network error (e.g., no internet connection)
                LoginUiState failureState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, "Network error", false, false);
                _uiState.postValue(failureState);
            }
        });
    }
}