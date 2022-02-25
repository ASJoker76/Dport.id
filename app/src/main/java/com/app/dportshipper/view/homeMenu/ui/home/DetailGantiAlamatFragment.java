package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterAlamatPengirim;
import com.app.dportshipper.adapter.AdapterRekomendasiTransporter;
import com.app.dportshipper.adapter.AdapterStatus;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentBookingPengirimanBinding;
import com.app.dportshipper.databinding.FragmentDetailGantiAlamatBinding;
import com.app.dportshipper.model.MAlamatPenerima;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.response.ResDataAlamatPenerima;
import com.app.dportshipper.model.response.ResStatusTab;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGantiAlamatFragment extends Fragment implements AdapterAlamatPengirim.OnKlikListener {


    private FragmentDetailGantiAlamatBinding binding;

    private ArrayList<MListDataBarang> listDataBarang;
    private ArrayList<MAlamatPenerima> listDataAlamat;

    private String token;
    private ArrayList<ResDataAlamatPenerima> alamatPengirim;
    private AdapterAlamatPengirim adapterAlamatPengirim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailGantiAlamatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadtable();
        loadgantialamat();
        loadapi();

        return root;
    }

    private void loadgantialamat() {
        binding.clTambahAlamat.setVisibility(View.GONE);
        binding.btnSimpan.setVisibility(View.GONE);
    }

    private void loadtable() {
        alamatPengirim = new ArrayList<>();
        adapterAlamatPengirim = new AdapterAlamatPengirim(this, alamatPengirim,this);
        binding.rvAlamatPengirim.setAdapter(new AlphaInAnimationAdapter(adapterAlamatPengirim));
        binding.rvAlamatPengirim.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvAlamatPengirim.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvAlamatPengirim.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterAlamatPengirim);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
            listDataAlamat = bundle.getParcelableArrayList("listDataAlamat");
        }
    }

    private void loadapi() {
        Call<List<ResDataAlamatPenerima>> callAlamat = API.service().getAlamatPenerima(token);
        callAlamat.enqueue(new Callback<List<ResDataAlamatPenerima>>() {
            @Override
            public void onResponse(Call<List<ResDataAlamatPenerima>> call, Response<List<ResDataAlamatPenerima>> response) {
                if (response.code() == 200){
                    List<ResDataAlamatPenerima> resDataAlamatPenerima = response.body();
                    alamatPengirim.addAll(resDataAlamatPenerima);
                    adapterAlamatPengirim.notifyDataSetChanged();

                }else if (response.code() == 204){
                    binding.rvAlamatPengirim.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ResDataAlamatPenerima>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onKlikClick(int position) {
        ResDataAlamatPenerima resDataAlamatPenerima = alamatPengirim.get(position);
        String alamat = resDataAlamatPenerima.getAlamat();
        String nama_penerima = resDataAlamatPenerima.getNama_penerima();
        String no_tlp = resDataAlamatPenerima.getNo_hp();
        String provisi = resDataAlamatPenerima.getProv();
        String kabupaten = resDataAlamatPenerima.getKab();
        String kecamatan = resDataAlamatPenerima.getKec();
        String kelurahan = resDataAlamatPenerima.getKel();
        int kodepos = resDataAlamatPenerima.getKode_pos();

        SharedPreferences.Editor editor = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
        editor.putBoolean("loadalamat", true);
        editor.putString("alamat", alamat);
//        editor.putString("nama_penerima", nama_penerima);
//        editor.putString("no_tlp", no_tlp);
        editor.putString("provisi", provisi);
        editor.putString("kabupaten", kabupaten);
        editor.putString("kecamatan", kecamatan);
        editor.putString("kelurahan", kelurahan);
        editor.putInt("kodepos", kodepos);
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
        bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
//        bundle.putString("alamat", alamat);
//        bundle.putString("nama_penerima", nama_penerima);
//        bundle.putString("no_tlp", no_tlp);


        BookingPengirimanFragment ringkasanPesananFragment = new BookingPengirimanFragment();
        ringkasanPesananFragment.setArguments(bundle);
        FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransactionKorlap = fragmentManager.beginTransaction();
        fragmentTransactionKorlap.replace(R.id.nav_host_fragment, ringkasanPesananFragment).addToBackStack(null);
        fragmentTransactionKorlap.commit();
    }
}