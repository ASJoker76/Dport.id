package com.app.dportshipper.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.databinding.ActivityLoginRegisterBinding;
import com.app.dportshipper.utils.SharedPrefManager;
import com.app.dportshipper.view.homeMenu.HomeActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    SharedPrefManager sharedPrefManager;
    private SharedPreferences.Editor editor;
    private ActivityLoginRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setInitLogReg();
        loadsession();


    }

    private void loadsession() {
        SharedPreferences prefs = getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username = prefs.getString("username","");
        if(username.equals("")){

        }
        else{
            pindahkehome();
        }
    }

    private void pindahkehome() {
        Intent moveLogin = new Intent(LoginRegisterActivity.this, HomeActivity.class);
        startActivity(moveLogin);
        finish();
    }

    private void setInitLogReg() {
        /* Pindah ke Activity Login */
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveLogin = new Intent(LoginRegisterActivity.this, LoginActivity.class);
                startActivity(moveLogin);
            }
        });

        /* Pindah ke Activity Register */
        binding.lblCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveRegister = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
                startActivity(moveRegister);
            }
        });

//        txtNantiSaja.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent moveRegister = new Intent(LoginRegisterActivity.this, HomeBLActivity.class);
//                startActivity(moveRegister);
//            }
//        });

    }
}