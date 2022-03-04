package com.app.dportshipper.view.homeMenu.ui.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dportshipper.adapter.AdapterPengirimanFavorite;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.DialogSortirBinding;
import com.app.dportshipper.databinding.DialogSuratWebviewBinding;
import com.app.dportshipper.databinding.FragmentHasilPencarianBinding;
import com.app.dportshipper.model.DataPencarian;
import com.app.dportshipper.model.request.ReqPencarian;
import com.app.dportshipper.model.request.ReqPencarianAuto;
import com.app.dportshipper.model.response.ResPencarian;
import com.app.dportshipper.model.response.ResPencarianAuto;
import com.app.dportshipper.utils.GridSpacingItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HasilPencarianFragment extends Fragment {


    private FragmentHasilPencarianBinding binding;
    private ArrayList<DataPencarian> dataPencarianArrayList;
    private AdapterPengirimanFavorite adapterPengirimanFavorite;
    private String token;
    private String kotaAsal, kotaTujuan, tanggal, type_send;
    private int type_service;

    private ArrayAdapter<String> adapterPencarian;
    List<String> dataPencarian = new ArrayList<>();
    private List<ResPencarianAuto> listData = new ArrayList<>();
    private DialogSortirBinding bindingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHasilPencarianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadtable2();
        loadapipengirimanfavorite();
        data();
        onclick();

        return root;
    }

    private void onclick() {
        /* Date Picker */
        final Calendar hCalender = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                hCalender.set(Calendar.YEAR, year);
                hCalender.set(Calendar.MONTH, month);
                hCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                binding.etTglPencarianAl.setText(sdf.format(hCalender.getTime()));
            }
        };

        binding.etTglPencarianAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dp, hCalender.get(Calendar.YEAR),
                        hCalender.get(Calendar.MONTH), hCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        
        binding.etFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                bindingDialog = DialogSortirBinding.inflate(LayoutInflater.from(v.getContext()));
                mAlertDialog.setView(bindingDialog.getRoot());
                mAlertDialog.setCancelable(true);
                mAlertDialog.getWindow().setLayout(700, 600);
                mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mAlertDialog.show();
            }
        });
    }

    private void data() {
        adapterPencarian = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataPencarian);

        onLoad("");
        binding.etAsalPencarianAl.setAdapter(adapterPencarian);
        binding.etTujuanPencarianAl.setAdapter(adapterPencarian);
    }

    private void onLoad(String s) {
        ReqPencarianAuto reqPencarianAuto = new ReqPencarianAuto();

        reqPencarianAuto.setParam(s.toString());

        Call<List<ResPencarianAuto>> getListAsal = API.service().sAutoRequest(token, reqPencarianAuto);
        getListAsal.enqueue(new Callback<List<ResPencarianAuto>>() {
            @Override
            public void onResponse(Call<List<ResPencarianAuto>> call, Response<List<ResPencarianAuto>> response) {
                listData.clear();
                dataPencarian.clear();
                adapterPencarian.notifyDataSetChanged();
                listData = response.body();
                for (ResPencarianAuto tipePengiriman : listData) {
                    dataPencarian.add(tipePengiriman.getKab_kota());
                }
                adapterPencarian.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResPencarianAuto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            /* Terima data dari Home */
            kotaAsal = bundle.getString("kab_asal");
            kotaTujuan = bundle.getString("kab_tujuan");
            type_send = bundle.getString("type_send");
            type_service = bundle.getInt("type_service");
            tanggal = bundle.getString("tanggal");

            binding.etTglPencarianAl.setText(tanggal);
            binding.etAsalPencarianAl.setText(kotaAsal);
            binding.etTujuanPencarianAl.setText(kotaTujuan);
            binding.etTipePengiriman.setText("FTL");

            //loadapipengirimanfavorite();
        }
    }

    private void loadtable2() {
        dataPencarianArrayList = new ArrayList<>();
        adapterPengirimanFavorite = new AdapterPengirimanFavorite(this, dataPencarianArrayList, type_send, type_service, tanggal);
        binding.rvHasilPencarian.setAdapter(new AlphaInAnimationAdapter(adapterPengirimanFavorite));
        binding.rvHasilPencarian.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        binding.rvHasilPencarian.addItemDecoration(new GridSpacingItemDecoration(2, 2, true, 2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager) binding.rvHasilPencarian.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapterPengirimanFavorite);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void loadapipengirimanfavorite() {
        ReqPencarian reqPencarian = new ReqPencarian();
        reqPencarian.setKab_asal(kotaAsal);
        reqPencarian.setKab_tujuan(kotaTujuan);
        reqPencarian.setTanggal(tanggal);
        reqPencarian.setType_service(type_service);
        reqPencarian.setType_send(type_send);

        /* Set Res Pencarian */
        Call<ResPencarian> callPencarian = API.service().PencarianShipperPage(token, reqPencarian);
        callPencarian.enqueue(new Callback<ResPencarian>() {
            @Override
            public void onResponse(Call<ResPencarian> call, Response<ResPencarian> response) {
                Log.d("reza Pencarian", response.code() + "");

                if (response.code() == 200) {
                    ResPencarian resPencarian = response.body();
                    dataPencarianArrayList.addAll(resPencarian.getData());
                    adapterPengirimanFavorite.notifyDataSetChanged();
                } else if (response.code() == 204) {

                }
            }

            @Override
            public void onFailure(Call<ResPencarian> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}