package com.example.applock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class PrefManager {
    private static final String PREF_NAME = "applock_secure_prefs";
    private static final String KEY_IS_REGISTERED = "is_registered";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_MOBILE = "user_mobile";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PIN = "user_pin";
    private static final String KEY_BIOMETRIC_ENABLED = "biometric_enabled";

    private SharedPreferences prefs;

    public PrefManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public boolean isRegistered() {
        return prefs.getBoolean(KEY_IS_REGISTERED, false);
    }

    public void registerUser(String name, String mobile, String email, String pin) {
        prefs.edit()
                .putString(KEY_NAME, name)
                .putString(KEY_MOBILE, mobile)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PIN, pin)
                .putBoolean(KEY_IS_REGISTERED, true)
                .apply();
    }

    public boolean verifyPin(String enteredPin) {
        String savedPin = prefs.getString(KEY_PIN, "");
        return savedPin.equals(enteredPin);
    }

    public void setBiometricEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_BIOMETRIC_ENABLED, enabled).apply();
    }

    public boolean isBiometricEnabled() {
        return prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false);
    }
}