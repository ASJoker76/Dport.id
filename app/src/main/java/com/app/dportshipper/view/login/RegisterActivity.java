package com.app.dportshipper.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityRegisterBinding;
import com.app.dportshipper.model.request.ReqRegister;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.utils.Utils;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton btnMasuk;
    private EditText etNama, etTelepon, etEmail, etKataSandi, etKataSandi2;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnLoginRegShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(RegisterActivity.this, PinVerifikasiActivity.class);
                startActivity(intent);*/
                
                /* Validasi Register */
                validasiRegister();
            
            }
        });

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pindahkeawal();
            }
        });
    }

    private void validasiRegister() {

        String getNama = binding.etNamaRegShipper.getText().toString();
        String getNoTelp = binding.etNotelRegShipper.getText().toString();
        String getEmail = binding.etEmailRegShipper.getText().toString();
        String getPassword = binding.etPasswordRegShipper.getText().toString();
        String getUlangiPassword = binding.etUlangiPasswordRegShipper.getText().toString();

        // Check if all strings are null or not
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmail);

        if (getNama.equals("") || getNama.length() == 0
                || getNoTelp.equals("") || getNoTelp.length() == 0
                || getEmail.equals("") || getEmail.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getUlangiPassword.equals("") || getUlangiPassword.length() == 0) {

            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Data Belum Dilengkapi")
                    .show();
        }
        else if (!m.find()) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Format Email Salah")
                    .show();
        } else if (getPassword.length() < 8){
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Password minimal 8 karakter")
                    .show();
        }else if (!getUlangiPassword.equals(getPassword)){
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Password tidak sama")
                    .show();
        }
        /*else if (getUlangiPassword.isEmpty()){
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Masukan Ulangi Password")
                    .show();
        }*/
//        else if (binding.etPasswordReg.getText().toString().equals(binding.etUlangiPasswordReg.getText().toString())) {
//            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
//                    .setTitleText("Password Tidak Sama")
//                    .show();
//        }
        else {
            registerProcess();
        }


    }

    private void registerProcess() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqRegister reqRegister = new ReqRegister();
        reqRegister.setNama_perusahaan(binding.etNamaRegShipper.getText().toString());
        reqRegister.setNo_phone(binding.etNotelRegShipper.getText().toString());
        reqRegister.setEmail(binding.etEmailRegShipper.getText().toString());
        reqRegister.setPassword(binding.etPasswordRegShipper.getText().toString());
        Call<ResUtama> callRegister = API.service().register(reqRegister);
        callRegister.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                pDialog.dismissWithAnimation();
                Log.d("reza register", response.code() + "");
                if (response.code() == 200) {
//                    progress.setVisibility(View.GONE);
                    pDialog.dismissWithAnimation();
                    ResUtama resLogin = response.body();
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Registrasi Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeotp();
                                }
                            })
                            .show();
                }else if (response.code() == 204) {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Email Sudah Digunakan")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }else {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Register Gagal")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });

    }

    private void pindahkeotp() {

        Intent intent = new Intent(RegisterActivity.this, PinVerifikasiActivity.class);
        intent.putExtra("email",etEmail.getText().toString());
        finish();
        startActivity(intent);

    }
    private void pindahkeawal() {

        Intent intent = new Intent(RegisterActivity.this, LoginRegisterActivity.class);
        finish();
        startActivity(intent);

    }
}