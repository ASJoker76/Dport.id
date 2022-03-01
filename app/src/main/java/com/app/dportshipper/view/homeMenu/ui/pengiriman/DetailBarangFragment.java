package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.app.dportshipper.adapter.AdapterBarangDetail;
import com.app.dportshipper.adapter.AdapterBongkar;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.ActivityChangePasswordBinding;
import com.app.dportshipper.databinding.FragmentDetailBarangBinding;
import com.app.dportshipper.databinding.FragmentDetailPengirimanBinding;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailPengirimanBarang;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
import com.google.gson.Gson;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangFragment extends Fragment {

    private FragmentDetailBarangBinding binding;
    private int id_order;
    private String token;
    private ArrayList<ResDetailPengirimanBarang> listBarang;
    private AdapterBarangDetail adapterBarangDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBarangBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadtable();
        onclick();

        return root;
    }

    private void loadtable() {
        adapterBarangDetail = new AdapterBarangDetail(getActivity(), listBarang);
        binding.rvDataBarang.setAdapter(new AlphaInAnimationAdapter(adapterBarangDetail));
        binding.rvDataBarang.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvDataBarang.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvDataBarang.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterBarangDetail);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        adapterBarangDetail.notifyDataSetChanged();
    }

    private void onclick() {
        binding.lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order", id_order);
                DetailPengirimanFragment fragementIntent = new DetailPengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_order = bundle.getInt("id_order");
            listBarang = bundle.getParcelableArrayList("listBarang");
        }
    }
}