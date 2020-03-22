package com.example.invoiceamigobusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.invoiceamigobusiness.ui.auth.LoginFragment;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }
}
