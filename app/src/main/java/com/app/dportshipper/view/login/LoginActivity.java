package com.app.dportshipper.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityLoginBinding;
import com.app.dportshipper.model.request.ReqLoginShipper;
import com.app.dportshipper.model.response.ResLoginShipper;
import com.app.dportshipper.utils.SharedPrefManager;
import com.app.dportshipper.view.homeMenu.HomeActivity;
import com.app.dportshipper.view.inputDataDiri.InputDataDiriCompanyActivity;
import com.app.dportshipper.view.inputDataDiri.TypeUsahaActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Boolean isHidden = true;
    private SharedPreferences.Editor editor;
    SharedPrefManager sharedPrefManager;
    private SharedPreferences sharedPreferences;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setInitLogin();

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveForget = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(moveForget);
            }
        });

        binding.tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveDaftar = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(moveDaftar);
            }
        });

        binding.btnLoginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidasi();
            }
        });

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pindahkeawal();
            }
        });
    }

    private void setInitLogin() {
        sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPrefManager = new SharedPrefManager(this);
    }

    private void checkValidasi() {

        String getEmail = binding.etLoginEmail.getText().toString();
        String getPassword = binding.etLoginPassword.getText().toString();

        // Check if all strings are null or not
        if (getEmail.equals("") || getEmail.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Data Belum Dilengkapi")
                    .show();
        } else {
            /*if (getEmail.equals("Reza") && getPassword.equals("cekhost")) {
                Toast.makeText(this, Host.getHost(), Toast.LENGTH_LONG).show();
                return;
            }*/
            loginProses("reza");
        }

    }

    private void loginProses(String reza) {

        final SweetAlertDialog sDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sDialog.setTitleText("Loading ...");
        sDialog.setCancelable(false);
        sDialog.show();
        /* Set Req Login */
        ReqLoginShipper reqLoginShipper = new ReqLoginShipper();
        reqLoginShipper.setEmail(binding.etLoginEmail.getText().toString());
        reqLoginShipper.setPassword(binding.etLoginPassword.getText().toString());
        reqLoginShipper.setTipe(2);
        /* Set Res Login */
        Call<ResLoginShipper> callLogin = API.service().loginRequest(reqLoginShipper);
        callLogin.enqueue(new Callback<ResLoginShipper>() {
            @Override
            public void onResponse(Call<ResLoginShipper> call, Response<ResLoginShipper> response) {

                sDialog.dismissWithAnimation();
                Log.d("reza login", response.code() + "");
                if (response.code() == 200) {
//                    progress.setVisibility(View.GONE);
                    sDialog.dismissWithAnimation();
                    ResLoginShipper resLogin = response.body();
                    Log.d("sidik status",resLogin.getStatus()+"");

                    if (resLogin.getStatus() == 1) {
                        Intent intent = new Intent(LoginActivity.this, PinVerifikasiActivity.class);
                        intent.putExtra("email", binding.etLoginEmail.getText().toString());
                        intent.putExtra("username", resLogin.getUsername());
                        finish();
                        startActivity(intent);
                    } else if (resLogin.getStatus() == 2) {
                        SharedPreferences.Editor editor = getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                        editor.putString("token", resLogin.getToken());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, TypeUsahaActivity.class);
                        intent.putExtra("username", resLogin.getUsername());
                        intent.putExtra("email", resLogin.getEmail());
                        finish();
                        startActivity(intent);
                    } else if (resLogin.getStatus() == 3) {
                        if (resLogin.getRole().equals("shipper")) {
                            SharedPreferences.Editor editor = getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
                            editor.putString("username", resLogin.getUsername());
                            editor.putString("token", resLogin.getToken());
                            editor.putString("email", resLogin.getEmail());
                            editor.apply();
                            pindahkehome();
                        } else {
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Role Salah")
                                    .show();
                        }
                    }

                } else if (response.code() == 405) {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Invalid Password")
                            .show();
                } else if (response.code() == 404) {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("User Tidak ditemukan")
                            .show();
                } else {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error Sistem")
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResLoginShipper> call, Throwable t) {
                sDialog.dismissWithAnimation();
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });

    }

    private void pindahkehome() {

        Intent moveLogin = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(moveLogin);
        finish();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void pindahkeawal() {

        Intent intent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
        finish();
        startActivity(intent);

    }
}