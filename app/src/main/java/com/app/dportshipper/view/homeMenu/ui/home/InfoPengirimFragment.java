package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dportshipper.R;
import com.app.dportshipper.databinding.FragmentDetailGantiAlamatBinding;
import com.app.dportshipper.databinding.FragmentInfoPengirimBinding;
import com.app.dportshipper.model.MAlamatPenerima;
import com.app.dportshipper.model.MListDataBarang;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class InfoPengirimFragment extends Fragment {

    private FragmentInfoPengirimBinding binding;

    private ArrayList<MListDataBarang> listDataBarang;
    private ArrayList<MAlamatPenerima> listDataAlamat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoPengirimBinding.inflate(inflater, container, false);
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
    }

    private void loadsession() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
            listDataAlamat = bundle.getParcelableArrayList("listDataAlamat");
        }
    }

    private void validasi() {
        String getNama = binding.etNama.getText().toString();
        String getNotel = binding.etNoTlp.getText().toString();

        // Check if all strings are null or not
        if (getNama.equals("") || getNama.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi nama penerima")
                    .show();
        }else if (getNotel.equals("") || getNotel.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi nomor telepon penerima")
                    .show();
        }
        else {
            onloadProcess(getNama,getNotel);
        }
    }

    private void onloadProcess(String getNama, String getNotel) {
        SharedPreferences.Editor editor = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
        editor.putString("nama_penerima", getNama);
        editor.putString("no_tlp", getNotel);
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
        bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
        BookingPengirimanFragment fragementIntent = new BookingPengirimanFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragementIntent);
        fragementIntent.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}