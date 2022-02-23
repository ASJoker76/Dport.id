package com.app.dportshipper.view.homeMenu.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
import com.app.dportshipper.adapter.AdapterBarang;
import com.app.dportshipper.adapter.AdapterPengirimanFavorite;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.DialogDataBarangBinding;
import com.app.dportshipper.databinding.FragmentBookingPengirimanBinding;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.request.ReqDetailInputBarang;
import com.app.dportshipper.model.response.ResDetailInputBarang;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
import com.app.dportshipper.view.login.LoginActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingPengirimanFragment extends Fragment {

    private FragmentBookingPengirimanBinding binding;
    private String token;
    private int id_produk_transporter;
    private String tanggal;
    private String type_send;
    private int type_service;
    ArrayList<MListDataBarang> listDataBarang;
    private String kab_asal;
    private String kab_tujuan;
    private DialogDataBarangBinding bindingDialog;
    private AdapterBarang adapterBarang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadapidetail();

        onactionbutton();
        return root;
    }

    private void onactionbutton() {
        binding.ivAddBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
                DetailBarangFragment fragementIntent = new DetailBarangFragment();
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
            public void onClick(View v) {
                DetailPengirimanNewFragment inbound = new DetailPengirimanNewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                ft.commit();
            }
        });
        binding.tvDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listDataBarang!=null){
                    //convert array to string
                    String jk = new Gson().toJson(listDataBarang);

                    AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                    bindingDialog = DialogDataBarangBinding.inflate(LayoutInflater.from(v.getContext()));
                    mAlertDialog.setView(bindingDialog.getRoot());
                    mAlertDialog.setCancelable(false);
                    mAlertDialog.getWindow().setLayout(700, 600);
                    mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mAlertDialog.show();

                    //dataPencarianArrayList = new ArrayList<>();
                    adapterBarang = new AdapterBarang(listDataBarang);
                    bindingDialog.rvView.setAdapter(new AlphaInAnimationAdapter(adapterBarang));
                    bindingDialog.rvView.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
                    bindingDialog.rvView.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
                    LinearLayoutManager recyclerManager = ((LinearLayoutManager)bindingDialog.rvView.getLayoutManager());

                    AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterBarang);
                    alphaInAnimationAdapter.setDuration(1000);
                    alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
                    alphaInAnimationAdapter.setFirstOnly(false);

                    bindingDialog.btnOke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAlertDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

        SharedPreferences prefs_data_transporter = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE);
        kab_asal = prefs_data_transporter.getString("kab_asal", "");
        kab_tujuan = prefs_data_transporter.getString("kab_tujuan", "");
        type_send = prefs_data_transporter.getString("type_send", "");
        type_service = prefs_data_transporter.getInt("type_service", 0);
        tanggal = prefs_data_transporter.getString("tanggal", "");
        id_produk_transporter = prefs_data_transporter.getInt("id_produk_transporter", 0);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            listDataBarang =bundle.getParcelableArrayList("listDataBarang");
            if(listDataBarang!=null){
                binding.tvDataBarang.setText("Data Barang");
            }
//            kab_asal = bundle.getString("kab_asal");
//            kab_tujuan = bundle.getString("kab_tujuan");
//            type_send = bundle.getString("type_send");
//            type_service = bundle.getInt("type_service");
//            tanggal = bundle.getString("tanggal");
//            id_produk_transporter = bundle.getInt("id_produk_transporter");
        }
    }

    private void loadapidetail() {
        ReqDetailInputBarang reqDetailInputBarang = new ReqDetailInputBarang();
        reqDetailInputBarang.setType_send(type_send);
        reqDetailInputBarang.setType_service(type_service);
        reqDetailInputBarang.setTanggal(tanggal);
        reqDetailInputBarang.setId_produk_transporter(id_produk_transporter);

        String jk = new Gson().toJson(reqDetailInputBarang);
        Log.d("sidik PARAM",jk);
        Call<ResDetailInputBarang> callDetailInputBarang = API.service().detailInputBarang(token, reqDetailInputBarang);
        callDetailInputBarang.enqueue(new Callback<ResDetailInputBarang>() {
            @Override
            public void onResponse(Call<ResDetailInputBarang> call, Response<ResDetailInputBarang> response) {
                if (response.code() == 200){
                    ResDetailInputBarang resDetailPengiriman = response.body();

                    Glide.with(getActivity())
                            .load(resDetailPengiriman.getDetail().getPic_file_path())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivTruk);

                    binding.tvNamaTruk.setText(resDetailPengiriman.getDetail().getNama_transporter());
                    binding.tvPlatNo.setText(resDetailPengiriman.getDetail().getPolice_no()+"");
                    binding.tvAlamatPengirim.setText(resDetailPengiriman.getAlamat().getAlamat());
                    binding.etTglPencarianAl.setText(tanggal);

                    //detail armada
                    binding.tvTipeKendaraan.setText(resDetailPengiriman.getDetail().getJenis_kendaraan());
                    binding.tvMerek.setText(resDetailPengiriman.getDetail().getBrand());
                    binding.tvNoKir.setText("");
                    binding.tvDimensi.setText(resDetailPengiriman.getDetail().getHeigth()+" cm x "+resDetailPengiriman.getDetail().getWidth()+" cm x "+ resDetailPengiriman.getDetail().getLength()+" cm");
                    binding.tvMuatan.setText(resDetailPengiriman.getDetail().getCapacity() +" Kg");
                    
                    //tarif pengiriman
                    binding.tvBiayaPengiriman.setText("Rp. "+ToRupiah(resDetailPengiriman.getDetail().getHarga()));
                    binding.tvBiayaPpn.setText("Rp. "+ToRupiah(Integer.valueOf(resDetailPengiriman.getBiaya_ppn())));
                    binding.tvSubTotal.setText("Rp. "+ToRupiah(resDetailPengiriman.getTotal_harga()));

                }else if (response.code() == 202){
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Anda belum Login?")
                            .setContentText("Silahkan login terlebih dahulu")
                            .setConfirmText("Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    startActivity(new Intent(getActivity(), LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    getActivity().finish();
                                }
                            })
                            .show();

                }
            }

            @Override
            public void onFailure(Call<ResDetailInputBarang> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public String ToRupiah(long total_harga) {
        String format = String.format(Locale.getDefault(), "%,d", total_harga);
        format = format.replace(",", ".");
        //format = format;
        return format;
    }
}