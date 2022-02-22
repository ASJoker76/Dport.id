package com.app.dportshipper.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityForgotPasswordBinding;
import com.app.dportshipper.databinding.ActivityLoginBinding;
import com.app.dportshipper.model.request.ReqEmail;
import com.app.dportshipper.model.request.ReqForgotPassword;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.model.response.ResValidationCode;
import com.google.android.material.button.MaterialButton;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private String email,code;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        email = getIntent().getStringExtra("email");
//        code = getIntent().getStringExtra("code");

        binding.btnKirimNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPassword = binding.etEmail.getText().toString();

                // Check if all strings are null or not
                if (getPassword.equals("") || getPassword.length() == 0) {

                    new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Belum Dilengkapi")
                            .show();
                } else {
                    loginProcess();
                }
            }
        });

    }

    private void loginProcess() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqEmail reqEmail = new ReqEmail();
        reqEmail.setEmail(binding.etEmail.getText().toString());
        reqEmail.setType(2);
        Call<ResUtama> callReset = API.service().forgotPassword(reqEmail);
        callReset.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                pDialog.dismissWithAnimation();
                ResUtama resUtama = response.body();
                Log.d("Reset Password", response.code() + "");
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Kirim Email Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Intent intent = new Intent(ForgotPasswordActivity.this, PinVerifikasiLupaPasswordActivity.class);
                                    intent.putExtra("email",binding.etEmail.getText().toString());
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
//                else {
//                    new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
//                            .setTitleText(resUtama.getMessage().toString()+"")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
//                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });

    }
}