package com.example.applock;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.applock.utils.PrefManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager prefManager = new PrefManager(this);

        if (!prefManager.isRegistered()) {
            // First time open -> Open registration screen
            startActivity(new Intent(this, RegisterActivity.class));
        } else {
            // Already registered -> Open Lock / Auth screen
            startActivity(new Intent(this, UnlockActivity.class));
        }
        finish();
    }
}