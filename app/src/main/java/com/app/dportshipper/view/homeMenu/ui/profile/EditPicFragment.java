package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.app.dportshipper.databinding.FragmentEditPicBinding;
import com.app.dportshipper.model.request.ReqPic;
import com.app.dportshipper.model.response.ResGetPic;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditPicFragment extends Fragment {


    private FragmentEditPicBinding binding;
    private String token;
    private String nama_perusahaan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditPicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadapi();
        onclick();


        return root;
    }

    private void onclick() {
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("nama_perusahaan",nama_perusahaan);
                PicFragment fragementIntent = new PicFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
    }

    private void validasi() {
        String getValidasi1 = binding.etNamaPic.getText().toString();
        String getValidasi2 = binding.etKtpPic.getText().toString();
        String getValidasi3 = binding.etEmailPic.getText().toString();
        String getValidasi4 = binding.etNoTlpPic.getText().toString();
        String getValidasi5 = binding.etAlamatPic.getText().toString();

        if (getValidasi1.equals("") || getValidasi1.length() == 0
                || getValidasi2.equals("") || getValidasi2.length() == 0
                || getValidasi3.equals("") || getValidasi3.length() == 0
                || getValidasi4.equals("") || getValidasi4.length() == 0
                || getValidasi5.equals("") || getValidasi5.length() == 0) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Data Belum Dilengkapi")
                    .show();
        } else {
            updatepic();
        }
    }

    private void updatepic() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqPic reqPic = new ReqPic();
        reqPic.setNama_pic(binding.etNamaPic.getText().toString());
        reqPic.setNoktp_pic(binding.etKtpPic.getText().toString());
        reqPic.setEmail_pic(binding.etEmailPic.getText().toString());
        reqPic.setNotelp_pic(binding.etNoTlpPic.getText().toString());
        reqPic.setAlamat_pic(binding.etAlamatPic.getText().toString());
        Call<ResGetPic> getListPic = API.service().updatePIC(token, reqPic);
        getListPic.enqueue(new Callback<ResGetPic>() {
            @Override
            public void onResponse(Call<ResGetPic> call, Response<ResGetPic> response) {
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Update Pic Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<ResGetPic> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void loadapi() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResGetPic> getListPic = API.service().getPIC(token);
        getListPic.enqueue(new Callback<ResGetPic>() {
            @Override
            public void onResponse(Call<ResGetPic> call, Response<ResGetPic> response) {
                Log.d("Data Pic", response.code() + "");
                if (response.code() == 200) {
                    ResGetPic resGetPic = response.body();

                    binding.etNamaPic.setText(resGetPic.getNama_pic());
                    binding.etNoTlpPic.setText(resGetPic.getNoktp_pic());
                    binding.etEmailPic.setText(resGetPic.getEmail_pic());
                    binding.etNoTlpPic.setText(String.valueOf(resGetPic.getNotelp_pic()));
                    binding.etAlamatPic.setText(resGetPic.getAlamat_pic());

                    pDialog.dismissWithAnimation();

                }
            }

            @Override
            public void onFailure(Call<ResGetPic> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });

    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nama_perusahaan = bundle.getString("nama_perusahaan");
        }
    }
}