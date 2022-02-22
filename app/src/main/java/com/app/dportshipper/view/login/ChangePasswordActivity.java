package com.app.dportshipper.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityChangePasswordBinding;
import com.app.dportshipper.databinding.ActivityForgotPasswordBinding;
import com.app.dportshipper.model.request.ReqForgotPassword;
import com.app.dportshipper.model.response.ResValidationCode;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;
    private String email,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = getIntent().getStringExtra("email");
        code = getIntent().getStringExtra("code");

        binding.btnKirimNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPassword = binding.etPassword.getText().toString();

                // Check if all strings are null or not
                if (getPassword.equals("") || getPassword.length() == 0) {

                    new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Belum Dilengkapi")
                            .show();
                } else {
                    loginProcess();
                }
            }
        });
    }

    private void loginProcess() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqForgotPassword reqValidationCode = new ReqForgotPassword();
        reqValidationCode.setEmail(email);
        reqValidationCode.setCode(Integer.valueOf(code));
        reqValidationCode.setNew_password(binding.etPassword.getText().toString());
        Call<ResValidationCode> callValidationCode = API.service().resetPassword(reqValidationCode);
        callValidationCode.enqueue(new Callback<ResValidationCode>() {
            @Override
            public void onResponse(Call<ResValidationCode> call, Response<ResValidationCode> response) {
                Log.d("Forgot Password : ", response.code() + "");
                if (response.code() == 200){

                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil ubah password")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    ResValidationCode resValidationCode = response.body();
                                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                    intent.putExtra("email",resValidationCode.getEmail());
                                    intent.putExtra("code",resValidationCode.getCode());
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();


                }
            }

            @Override
            public void onFailure(Call<ResValidationCode> call, Throwable t) {
                t.printStackTrace();
                //progress.setVisibility(View.GONE);
                new SweetAlertDialog(ChangePasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });

    }
}