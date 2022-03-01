package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterBongkar;
import com.app.dportshipper.adapter.AdapterMuat;
import com.app.dportshipper.databinding.FragmentDetailBongkarBinding;
import com.app.dportshipper.databinding.FragmentDetailMuatBinding;
import com.app.dportshipper.model.response.ResDataFotoMuatBarang;
import com.google.gson.Gson;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class DetailMuatFragment extends Fragment {

    private FragmentDetailMuatBinding binding;
    private ArrayList<ResDataFotoMuatBarang> listMuat;
    private AdapterMuat adapterMuat;
    private int id_order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailMuatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loaddata();
        loadtable();
        onclick();
        
        return root;
    }

    private void onclick() {
        binding.lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order",id_order);
                DetailPengirimanFragment fragementIntent = new DetailPengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.commit();
            }
        });
    }

    private void loaddata() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_order = bundle.getInt("id_order");
            listMuat = bundle.getParcelableArrayList("listMuat");
            String jk = new Gson().toJson(listMuat);
            Log.e("isi bongkar", jk);
        }
    }

    private void loadtable() {
        adapterMuat = new AdapterMuat(getActivity(), listMuat);
        binding.rvBongkar.setAdapter(new AlphaInAnimationAdapter(adapterMuat));
        binding.rvBongkar.setHasFixedSize(true);
        binding.rvBongkar.setLayoutManager(new CardSliderLayoutManager(getActivity()));
        new CardSnapHelper().attachToRecyclerView(binding.rvBongkar);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterMuat);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
        adapterMuat.notifyDataSetChanged();
    }
}