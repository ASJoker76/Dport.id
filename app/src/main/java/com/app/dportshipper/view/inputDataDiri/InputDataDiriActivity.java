package com.app.dportshipper.view.inputDataDiri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityInputDataDiriBinding;
import com.app.dportshipper.databinding.ActivityPinVerifikasiBinding;
import com.app.dportshipper.model.request.ReqInputData;
import com.app.dportshipper.model.response.ResGetProfileFull;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.view.homeMenu.HomeActivity;
import com.app.dportshipper.view.login.LoginActivity;
import com.kofigyan.stateprogressbar.StateProgressBar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputDataDiriActivity extends AppCompatActivity {

    private ActivityInputDataDiriBinding binding;
    private Boolean status = false;
    private Boolean status2 = false;
    private Boolean status3 = false;
    private int idShipper;
    private String token,username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputDataDiriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadsession();
        loadidShipper();

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
                if(status.equals(true)){
                    binding.btnSimpan.setVisibility(View.GONE);
                    binding.btnSimpan2.setVisibility(View.VISIBLE);
                    binding.ly1.setVisibility(View.GONE);
                    binding.ly2.setVisibility(View.VISIBLE);
                    switch (binding.spbView.getCurrentStateNumber()){
                        case 1:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                            break;
                        case 2:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                            break;
                        case 3:
                            binding.spbView.setAllStatesCompleted(true);
                            //showCustomPopup();
                            break;
                    }
                }
            }
        });

        binding.btnSimpan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi2();
                if(status2.equals(true)){
                    binding.btnSimpan2.setVisibility(View.GONE);
                    binding.btnSimpan3.setVisibility(View.VISIBLE);
                    binding.ly2.setVisibility(View.GONE);
                    binding.ly3.setVisibility(View.VISIBLE);
                    switch (binding.spbView.getCurrentStateNumber()){
                        case 1:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                            break;
                        case 2:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                            break;
                        case 3:
                            binding.spbView.setAllStatesCompleted(true);
                            //showCustomPopup();
                            break;
                    }
                }
            }
        });

        binding.btnSimpan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi3();
                if(status3.equals(true)){
                    switch (binding.spbView.getCurrentStateNumber()){
                        case 1:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                            break;
                        case 2:
                            binding.spbView.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                            break;
                        case 3:
                            binding.spbView.setAllStatesCompleted(true);
                            //showCustomPopup();
                            break;
                    }
                    updateDataDiri();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void loadsession() {

        SharedPreferences prefs = InputDataDiriActivity.this.getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
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

    private void updateDataDiri() {
        ReqInputData reqInputData = new ReqInputData();
        reqInputData.setParam("2");
        reqInputData.setId_shipper(String.valueOf(idShipper));
        reqInputData.setShipper_ktp(binding.etNoKtp.getText().toString());
        reqInputData.setShipper_npwp(binding.etNpwp.getText().toString());
        reqInputData.setNo_siup(binding.etSiup.getText().toString());
        reqInputData.setNo_siujpt(binding.etSiup.getText().toString());
        reqInputData.setNo_sppkp(binding.etSppkp.getText().toString());
        reqInputData.setAkta_pendirian(binding.etAktaPendirian.getText().toString());
        reqInputData.setAkta_akhir(binding.etAktaAkhir.getText().toString());
        reqInputData.setSurat_domisili(binding.etSuratDomisili.getText().toString());
        reqInputData.setNama_pic(binding.etNamaPic.getText().toString());
        reqInputData.setNotelp_pic(binding.etNoHandphonePic.getText().toString());
        reqInputData.setNoktp_pic(binding.etKtpPic.getText().toString());
        reqInputData.setEmail_pic(binding.etEmailPic.getText().toString());
        reqInputData.setNo_skt(binding.etSkt.getText().toString());
        reqInputData.setNo_nib_tdp(binding.etTdpNib.getText().toString());
        Call<ResUtama> getListCall = API.service().inputData(token, reqInputData);
        getListCall.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    new SweetAlertDialog(InputDataDiriActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Input Data Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkehome();
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

    private void pindahkehome() {

        SharedPreferences.Editor editor = getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("email", email);

        Intent intent = new Intent(InputDataDiriActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);

    }

    private void validasi() {
        String getValidasi1 = binding.etNoKtp.getText().toString();
        String getValidasi2 = binding.etNpwp.getText().toString();
        String getValidasi3 = binding.etSiup.getText().toString();
        String getValidasi4 = binding.etStujpt.getText().toString();
        String getValidasi5 = binding.etSkt.getText().toString();
        String getValidasi6 = binding.etTdpNib.getText().toString();

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
        else if(getValidasi3.matches("") || getValidasi3.length() == 0){
            Toast.makeText(this, "You did not enter a No Siup", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi4.matches("") || getValidasi4.length() == 0){
            Toast.makeText(this, "You did not enter a No Stujpt", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi5.matches("") || getValidasi5.length() == 0){
            Toast.makeText(this, "You did not enter a No Skt", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi6.matches("") || getValidasi6.length() == 0){
            Toast.makeText(this, "You did not enter a No Tdp Nib", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
//            binding.btnSimpan.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru)));
//            binding.btnSimpan.setEnabled(true);
            status = true;
        }
    }
    private void validasi2() {
        String getValidasi1 = binding.etAktaAkhir.getText().toString();
        String getValidasi2 = binding.etAktaPendirian.getText().toString();
        String getValidasi3 = binding.etSppkp.getText().toString();
        String getValidasi4 = binding.etSuratDomisili.getText().toString();

        // Check if all strings are null or not
        if (getValidasi1.matches("") || getValidasi1.length() == 0)
        {
            Toast.makeText(this, "You did not enter a No Akta Akhir", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi2.matches("") || getValidasi2.length() == 0){
            Toast.makeText(this, "You did not enter a No Akta Pendirian", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi3.matches("") || getValidasi3.length() == 0){
            Toast.makeText(this, "You did not enter a No Sppkp", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi4.matches("") || getValidasi4.length() == 0){
            Toast.makeText(this, "You did not enter a No Surat Domisili", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
//            binding.btnSimpan.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru)));
//            binding.btnSimpan.setEnabled(true);
            status2 = true;
        }
    }
    private void validasi3() {
        String getValidasi1 = binding.etNamaPic.getText().toString();
        String getValidasi2 = binding.etKtpPic.getText().toString();
        String getValidasi3 = binding.etEmailPic.getText().toString();
        String getValidasi4 = binding.etNoHandphonePic.getText().toString();

        // Check if all strings are null or not
        if (getValidasi1.matches("") || getValidasi1.length() == 0)
        {
            Toast.makeText(this, "You did not enter a No Akta Akhir", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi2.matches("") || getValidasi2.length() == 0){
            Toast.makeText(this, "You did not enter a No Akta Pendirian", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi3.matches("") || getValidasi3.length() == 0){
            Toast.makeText(this, "You did not enter a No Sppkp", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(getValidasi4.matches("") || getValidasi4.length() == 0){
            Toast.makeText(this, "You did not enter a No Surat Domisili", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            status3 = true;
        }
    }
}