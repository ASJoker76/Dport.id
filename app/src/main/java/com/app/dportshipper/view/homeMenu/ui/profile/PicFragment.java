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
import com.app.dportshipper.databinding.FragmentKataSandiBinding;
import com.app.dportshipper.databinding.FragmentPicBinding;
import com.app.dportshipper.model.response.ResGetPic;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PicFragment extends Fragment {

    private FragmentPicBinding binding;
    private String token;
    private String nama_perusahaan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadapi();
        onclick();


        return root;
    }

    private void onclick() {
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("nama_perusahaan",nama_perusahaan);
                EditPicFragment fragementIntent = new EditPicFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment fragementIntent = new ProfileFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
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

                    binding.tvNamePic.setText(nama_perusahaan);
                    binding.tvNamaPerusahaan.setText(resGetPic.getNama_pic());
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