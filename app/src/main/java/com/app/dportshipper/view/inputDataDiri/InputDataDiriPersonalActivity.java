package com.app.dportshipper.view.inputDataDiri;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityInputDataDiriPersonalBinding;
import com.app.dportshipper.model.request.ReqInputData;
import com.app.dportshipper.model.response.ResGetProfileFull;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.view.homeMenu.HomeActivity;
import com.kofigyan.stateprogressbar.StateProgressBar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputDataDiriPersonalActivity extends AppCompatActivity {

    private ActivityInputDataDiriPersonalBinding binding;
    private int idShipper;
    private String token,username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputDataDiriPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadsession();
        loadidShipper();

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputDataDiriPersonalActivity.this, TypeUsahaActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                finish();
                startActivity(intent);
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void loadsession() {

        SharedPreferences prefs = InputDataDiriPersonalActivity.this.getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
    }

    private void loadidShipper() {

        Call<ResGetProfileFull> getProfilFull = API.service().getProfilFull(token);
        getProfilFull.enqueue(new Callback<ResGetProfileFull>() {
            @Override
            public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                if (response.code() == 200){
                    ResGetProfileFull resGetProfileFull = response.body();
                    idShipper = resGetProfileFull.getId_shipper();
                }
            }

            @Override
            public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void pindahkehome() {

        SharedPreferences.Editor editor = getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("email", email);

        Intent intent = new Intent(InputDataDiriPersonalActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);

    }

    private void validasi() {
        String getValidasi1 = binding.etNoKtp.getText().toString();
        String getValidasi2 = binding.etNpwp.getText().toString();

        // Check if all strings are null or not
        if (getValidasi1.matches("") || getValidasi1.length() == 0)
        {
            Toast.makeText(this, "You did not enter a No Ktp", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi2.matches("") || getValidasi2.length() == 0){
            Toast.makeText(this, "You did not enter a No Npwp", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            inputPersonal();
        }
    }

    private void inputPersonal() {
        ReqInputData reqInputData = new ReqInputData();
        reqInputData.setParam("1");
        reqInputData.setId_shipper(String.valueOf(idShipper));
        reqInputData.setShipper_ktp(binding.etNoKtp.getText().toString());
        reqInputData.setShipper_npwp(binding.etNpwp.getText().toString());
        Call<ResUtama> getListCall = API.service().inputData(token, reqInputData);
        getListCall.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    new SweetAlertDialog(InputDataDiriPersonalActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Input Data Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkehome();
                                }
                            })
                            .show();
                }else if (response.code() == 204){
                    new SweetAlertDialog(InputDataDiriPersonalActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Gagal")
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
            }
        });
    }
}