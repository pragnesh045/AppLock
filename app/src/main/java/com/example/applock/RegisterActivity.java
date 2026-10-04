package com.example.applock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applock.utils.PrefManager;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etMobile, etEmail, etPin, etConfirmPin;
    private Button btnRegister;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefManager = new PrefManager(this);

        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etPin = findViewById(R.id.etPin);
        etConfirmPin = findViewById(R.id.etConfirmPin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pin = etPin.getText().toString().trim();
        String confirmPin = etConfirmPin.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return;
        }

        if (mobile.length() < 10) {
            etMobile.setError("Enter valid 10-digit mobile number");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            return;
        }

        if (pin.length() != 4) {
            etPin.setError("PIN must be exactly 4 digits");
            return;
        }

        if (!pin.equals(confirmPin)) {
            etConfirmPin.setError("PINs do not match");
            return;
        }

        prefManager.registerUser(name, mobile, email, pin);
        Toast.makeText(this, "Profile Registered Successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to the next setup step: Biometric Setup / Main App List
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}