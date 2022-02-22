package com.app.dportshipper.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityResetPasswordBinding;
import com.app.dportshipper.model.request.ReqEmail;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInit();


    }

    private void setInit() {

        binding.btnKirimNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pindahkeawal();
            }
        });
    }

    private void validasi() {
        String getEmail = binding.etNewPassword.getText().toString();

        // Check if all strings are null or not
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmail);

        if (getEmail.equals("") || getEmail.length() == 0) {

            new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Email Belum Dilengkapi")
                    .show();
        } else if (!m.find()) {
            new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Format Email Salah")
                    .show();
        } else {
            kirimData();
        }

    }

    private void pindahkeawal() {

        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);

    }
    private void kirimData() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqEmail reqEmail = new ReqEmail();
        reqEmail.setEmail(binding.etNewPassword.getText().toString());
        reqEmail.setType(2);
        Call<ResUtama> callReset = API.service().forgotPassword(reqEmail);
        callReset.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                pDialog.dismissWithAnimation();
                Log.d("Reset Password", response.code() + "");
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Kirim Email Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
//                                    Intent intent = new Intent(ResetPasswordActivity.this, PinVerifikasiResetPasswordActivity.class);
//                                    intent.putExtra("email",binding.etNewPassword.getText().toString());
//                                    finish();
//                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });

    }

}