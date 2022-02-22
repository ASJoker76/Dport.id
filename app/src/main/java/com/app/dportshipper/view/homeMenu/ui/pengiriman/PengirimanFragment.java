package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterPengirimanNew;
import com.app.dportshipper.adapter.AdapterStatus;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentPengirimanBinding;
import com.app.dportshipper.model.MLimit;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.model.response.ResStatusTab;
import com.app.dportshipper.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengirimanFragment extends Fragment implements AdapterStatus.OnKlikListener {

    private FragmentPengirimanBinding binding;
    private AdapterStatus adapterStatus;
    private ArrayList<ResStatusTab> resStatusTabArrayList;
    private String token;

    private ArrayList<MResListPengiriman> allResBursaPengirimanArrayList;
    private AdapterPengirimanNew adapterRekomendasiTransporter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        inisiasi();
        loadtable();
        loadtable2();
        loadapistatus();
        loaddatapengiriman(18);

        return root;
    }

    private void loadtable2() {
        allResBursaPengirimanArrayList = new ArrayList<>();
        adapterRekomendasiTransporter = new AdapterPengirimanNew(this, allResBursaPengirimanArrayList);
        binding.rvPengiriman.setAdapter(new AlphaInAnimationAdapter(adapterRekomendasiTransporter));
        binding.rvPengiriman.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvPengiriman.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvPengiriman.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterRekomendasiTransporter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void inisiasi() {
        //kecilin ukuran drawble search
        Drawable dr = getResources().getDrawable(R.drawable.search);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 15, 15, true));

        //setCompoundDrawablesWithIntrinsicBounds (image to left, top, right, bottom)
        binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");
    }

    private void loadtable() {
        resStatusTabArrayList = new ArrayList<>();
        adapterStatus = new AdapterStatus(this, resStatusTabArrayList,this);
        binding.rvStatus.setAdapter(new AlphaInAnimationAdapter(adapterStatus));
        binding.rvStatus.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.HORIZONTAL, false));
        binding.rvStatus.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvStatus.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterStatus);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void loadapistatus() {
        Call<List<ResStatusTab>> getDataStatus = API.service().callStatusTab(token);
        getDataStatus.enqueue(new Callback<List<ResStatusTab>>() {
            @Override
            public void onResponse(Call<List<ResStatusTab>> call, Response<List<ResStatusTab>> response) {
                if (response.code()==200) {
                    List<ResStatusTab> resStatusTabList = response.body();
                    resStatusTabArrayList.addAll(resStatusTabList);
                    adapterStatus.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ResStatusTab>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onKlikClick(int position) {
        ResStatusTab resStatusTab = resStatusTabArrayList.get(position);
        int idStatus = resStatusTab.getId_status();

        loaddatapengiriman(idStatus);
    }

    private void loaddatapengiriman(int idStatus) {
        MLimit mLimit = new MLimit();
        mLimit.setStatus(idStatus);
        mLimit.setLimit(10);
        mLimit.setOffset(0);
        Call<List<MResListPengiriman>> callListDataPengiriman = API.service().getDataListPengiriman(token,mLimit);
        callListDataPengiriman.enqueue(new Callback<List<MResListPengiriman>>() {
            @Override
            public void onResponse(Call<List<MResListPengiriman>> call, Response<List<MResListPengiriman>> response) {
                if (response.code()==200){
                    allResBursaPengirimanArrayList.clear();
                    binding.rvPengiriman.setVisibility(View.VISIBLE);
                    List<MResListPengiriman> resListPengirimen = response.body();
                    allResBursaPengirimanArrayList.addAll(resListPengirimen);
                    adapterRekomendasiTransporter.notifyDataSetChanged();
                }
                else if(response.code()==204){
                    binding.rvPengiriman.setVisibility(View.GONE);
                }

            }


            @Override
            public void onFailure(Call<List<MResListPengiriman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}