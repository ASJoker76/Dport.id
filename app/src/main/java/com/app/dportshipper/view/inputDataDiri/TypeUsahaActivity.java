package com.app.dportshipper.view.inputDataDiri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.dportshipper.databinding.ActivityTypeUsahaBinding;
import com.app.dportshipper.view.login.LoginActivity;

public class TypeUsahaActivity extends AppCompatActivity {

    private ActivityTypeUsahaBinding binding;
    private String token;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTypeUsahaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadsession();

        binding.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = binding.spType.getSelectedItem().toString();
                Log.e("isi type", type);
                if(type.equals("Perusahaan")){
                    Intent intent = new Intent(TypeUsahaActivity.this, InputDataDiriCompanyActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    finish();
                    startActivity(intent);
                }
                else if(type.equals("Personal")){
                    Intent intent = new Intent(TypeUsahaActivity.this, InputDataDiriPersonalActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("email", email);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void loadsession() {

        SharedPreferences prefs = TypeUsahaActivity.this.getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
    }
}