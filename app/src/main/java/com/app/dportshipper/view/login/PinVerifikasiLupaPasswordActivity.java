package com.app.dportshipper.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityPinVerifikasiBinding;
import com.app.dportshipper.model.request.ReqKirimUlang;
import com.app.dportshipper.model.request.ReqValidateCode;
import com.app.dportshipper.model.response.ResKirimUlang;
import com.app.dportshipper.model.response.ResValidateCode;
import com.app.dportshipper.view.inputDataDiri.InputDataDiriActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinVerifikasiLupaPasswordActivity extends AppCompatActivity {

    String email;
    private ActivityPinVerifikasiBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPinVerifikasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInit();
        binding.tvKirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading ...");
                pDialog.setCancelable(false);
                pDialog.show();

                ReqKirimUlang reqKirimUlang = new ReqKirimUlang();
                reqKirimUlang.setEmail(email);
                Call<ResKirimUlang> callKirimUlang = API.service().kirimUlang(reqKirimUlang);
                callKirimUlang.enqueue(new Callback<ResKirimUlang>() {
                    @Override
                    public void onResponse(Call<ResKirimUlang> call, Response<ResKirimUlang> response) {
                        Log.d("sidik Res", response.code() + "");
                        if (response.code() == 200) {
                            pDialog.dismissWithAnimation();
                            ResKirimUlang resKirimUlang = response.body();
                            if(resKirimUlang.getMessage().equals("sukses")){
                                new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Berhasil Kirim Ulang Code")
                                        .show();
                            }
                        }
                        else{
                            pDialog.dismissWithAnimation();
                            new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(response.code()+ "")
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResKirimUlang> call, Throwable t) {
                        t.printStackTrace();
                        pDialog.dismissWithAnimation();
                        new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Ada Kesalahan Sistem")
                                .show();
                    }
                });

                /*btnPinMasuk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkValidation();
                    }
                });*/


            }
        });
    }

    private void checkValidation() {

        // Get all edittext texts

        String getPin = binding.txtPin.getText().toString();


        // Check if all strings are null or not
        if (getPin.equals("") || getPin.length() == 0) {

            new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Pin Belum Dilengkapi")
                    .show();
        }
        else {
            //checkCaptcha();
            VerifikasiCodeProcess("reza");
        }

    }

    private void VerifikasiCodeProcess(String reza) {

        ReqValidateCode reqValidationCode = new ReqValidateCode();
        reqValidationCode.setEmail(email);
        reqValidationCode.setForget_code(binding.txtPin.getText().toString());
        Call<ResValidateCode> callValidateCode = API.service().verifikasiCode(reqValidationCode);
        callValidateCode.enqueue(new Callback<ResValidateCode>() {
            @Override
            public void onResponse(Call<ResValidateCode> call, Response<ResValidateCode> response) {
                Log.d("agus", response.code() + "");
                if (response.code() == 200) {
                    Intent intent = new Intent(PinVerifikasiLupaPasswordActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("code", binding.txtPin.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else if(response.code()== 201){
                    message("Code Salah");
                }
                else if(response.code()== 202){
                    message("Waktu Sudah Habis");
                }
                else{
                    message("Error");
                }
            }

            @Override
            public void onFailure(Call<ResValidateCode> call, Throwable t) {
                t.printStackTrace();
                //progress.setVisibility(View.GONE);
                new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });

    }


    private void message(String pesan){
        new SweetAlertDialog(PinVerifikasiLupaPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(pesan)
                .show();
    }

    private void setInit() {

        //btnPinMasuk = findViewById(R.id.btn_login_pin);

        binding.btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent moveInput = new Intent(PinVerifikasiActivity.this, InputDataShipperActivity.class);
                startActivity(moveInput);*/
                checkValidation();
            }
        });

        email = getIntent().getStringExtra("email");

    }

}