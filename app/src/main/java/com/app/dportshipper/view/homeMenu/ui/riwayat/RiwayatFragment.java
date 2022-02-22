package com.app.dportshipper.view.homeMenu.ui.riwayat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterPengirimanNew;
import com.app.dportshipper.adapter.RiwayatAdapter;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentPengirimanBinding;
import com.app.dportshipper.databinding.FragmentRiwayatBinding;
import com.app.dportshipper.model.response.ResBursaPengiriman;
import com.app.dportshipper.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RiwayatFragment extends Fragment {


    private String token;
    private ArrayList<ResBursaPengiriman> allRiwayatArrayList;
    private RiwayatAdapter adapterriwayat;
    private FragmentRiwayatBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        inisiasi();
        loadtable2();
        loadapi();

        return root;
    }

    private void loadapi() {
        Call<List<ResBursaPengiriman>> getListRiwayat = API.service().listBursaPengiriman(token);
        getListRiwayat.enqueue(new Callback<List<ResBursaPengiriman>>() {
            @Override
            public void onResponse(Call<List<ResBursaPengiriman>> call, Response<List<ResBursaPengiriman>> response) {
                if (response.code() == 200){
                    allRiwayatArrayList.clear();
                    List<ResBursaPengiriman> resBursaPengirimen = response.body();
                    System.out.println("The List is not empty");
                    allRiwayatArrayList.addAll(resBursaPengirimen);
                    adapterriwayat.notifyDataSetChanged();
                    binding.rvRiwayat.setVisibility(View.VISIBLE);
                }else if (response.code() == 204){
                    //Toast.makeText(getActivity(), "Kosong", Toast.LENGTH_SHORT).show();
                    System.out.println("The List is empty");
                    allRiwayatArrayList.clear();
                    adapterriwayat.notifyDataSetChanged();
                    binding.rvRiwayat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ResBursaPengiriman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    private void loadtable2() {
        allRiwayatArrayList = new ArrayList<>();
        adapterriwayat = new RiwayatAdapter(this, allRiwayatArrayList);
        binding.rvRiwayat.setAdapter(new AlphaInAnimationAdapter(adapterriwayat));
        binding.rvRiwayat.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvRiwayat.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvRiwayat.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterriwayat);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }
}