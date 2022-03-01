package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentKataSandiBinding;
import com.app.dportshipper.databinding.FragmentProfilBinding;
import com.app.dportshipper.model.request.ReqGantiPasswordProfil;
import com.app.dportshipper.model.response.ResUtama;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KataSandiFragment extends Fragment {


    private FragmentKataSandiBinding binding;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentKataSandiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        onclick();

        return root;
    }

    private void onclick() {

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });

        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragementIntent = new ProfileFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void validasi() {
        String getValidasi1 = binding.etPassword.getText().toString();
        String getValidasi2 = binding.etPasswordBaru.getText().toString();
        String getValidasi3 = binding.etPasswordBaruKonfirm.getText().toString();

        // Check if all strings are null or not
        if (getValidasi1.equals("") || getValidasi1.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Password Lama")
                    .show();
        }else if (getValidasi2.equals("") || getValidasi2.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Password Baru")
                    .show();
        }else if (getValidasi3.equals("") || getValidasi3.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Password Konfirmasi")
                    .show();
        }
        else if (getValidasi2.length() < 8){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Password minimal 8 karakter")
                    .show();
        }
        else if (!getValidasi2.equals(getValidasi3)){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Password baru tidak sama")
                    .show();
        }
        else {
            onloadProcess(getValidasi1,getValidasi2,getValidasi3);
        }
    }

    private void onloadProcess(String getValidasi1, String getValidasi2, String getValidasi3) {
        ReqGantiPasswordProfil reqGantiPasswordProfil = new ReqGantiPasswordProfil();
        reqGantiPasswordProfil.setOld_password(getValidasi1);
        reqGantiPasswordProfil.setNew_password(getValidasi2);
        reqGantiPasswordProfil.setNew_password_confirmation(getValidasi3);
        Call<ResUtama> getCallChangePassword = API.service().gantiPassword(token, reqGantiPasswordProfil);
        getCallChangePassword.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("ganti password profil",response.code()+"");
                if (response.code() == 200){
                    ResUtama resUtama = response.body();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Update Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else if(response.code() == 400){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Password Lama Salah")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Update Password Gagal "+response.code())
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

    private void pindahkeprofile() {
        ProfileFragment fragementIntent = new ProfileFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragementIntent);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");
    }
}