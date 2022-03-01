package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterAlamatPengirim;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDetailGantiAlamatBinding;
import com.app.dportshipper.model.MAlamatPenerima;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.response.ResDataAlamatPenerima;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
import com.app.dportshipper.view.homeMenu.ui.home.BookingPengirimanFragment;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarAlamatSayaFragment extends Fragment implements AdapterAlamatPengirim.OnKlikListener {


    private FragmentDetailGantiAlamatBinding binding;

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
        //loadgantialamat();
        loadapi();
        onclick();

        return root;
    }

    private void onclick() {
        binding.clTambahAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahAlamatFragment fragementIntent = new TambahAlamatFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
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

//    private void loadgantialamat() {
//        binding.clTambahAlamat.setVisibility(View.GONE);
//        binding.btnSimpan.setVisibility(View.GONE);
//    }

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

//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
//            listDataAlamat = bundle.getParcelableArrayList("listDataAlamat");
//        }
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
        Bundle bundle = new Bundle();
        bundle.putInt("id_alamat_shipper", resDataAlamatPenerima.getId_alamat_shipper());
        bundle.putString("alamat", resDataAlamatPenerima.getAlamat());
        bundle.putString("prov", resDataAlamatPenerima.getProv());
        bundle.putString("kota", resDataAlamatPenerima.getKab());
        bundle.putString("kec", resDataAlamatPenerima.getKec());
        bundle.putString("kel", resDataAlamatPenerima.getKel());
        bundle.putInt("kodepos", resDataAlamatPenerima.getKode_pos());
        bundle.putInt("flag", resDataAlamatPenerima.getFlag());
        UpdateAlamatFragment fragementIntent = new UpdateAlamatFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragementIntent);
        fragementIntent.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}