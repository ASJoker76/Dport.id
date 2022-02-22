package com.app.dportshipper.view.homeMenu.ui.riwayat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.R;
//import com.app.dportshipper.adapter.AlamatPenerimaAdapter;
//import com.app.dportshipper.adapter.DetailBarangAdapter;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.response.ResDetailPenerima;
import com.app.dportshipper.model.response.ResDetailPengirim;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailPengirimanBarang;
import com.app.dportshipper.model.response.ResDetailPengirimanIsi;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
//import com.app.dportshipper.view.homeMenu.ui.pengiriman.TabPengiriman;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPesananFragment extends Fragment {


//    private TextView txtHashtag, txtTanggal, txtTop, txtMuatan, txtTypeTruck, txtTypeLayanan,
//            txtStatus, txtAsal, txtTujuan, lbTypeTruck, txtAlamatPengirim, txtNamaPengirim,
//            txtKontakPengirim, txtHarga, txtNamaPengemudi, txtEmailPengemudi, txtKontakPengemudi;
//    private EditText etCatatan;
//    private ImageView ivTrukCard, ivBack;
//    private ArrayList<ResDetailPengirimanBarang> allListBarang;
//    private ArrayList<ResDetailPenerima> allListPenerima;
//    private DetailBarangAdapter detailBarangAdapter;
//    private AlamatPenerimaAdapter alamatPenerimaAdapter;
//    private RecyclerView rvDetailbarang, rvDetailPenerima;
//    private CircleImageView civPengemudi;
//    int id_order;
//    String username, token;
//    private String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_pesanan, container, false);
//
//        txtHashtag = root.findViewById(R.id.txt_hashtag);
//        txtTanggal = root.findViewById(R.id.txt_tanggal);
//        txtTop = root.findViewById(R.id.txt_top);
//        txtMuatan = root.findViewById(R.id.txt_muatan);
//        txtTypeTruck = root.findViewById(R.id.txt_type_truck);
//        txtTypeLayanan = root.findViewById(R.id.txt_type_layanan);
//        txtStatus = root.findViewById(R.id.txt_status);
//        txtAsal = root.findViewById(R.id.txt_kota_asal_pengiriman);
//        txtTujuan = root.findViewById(R.id.txt_kota_tujuan);
//        ivTrukCard = root.findViewById(R.id.iv_truk_card);
//        ivBack = root.findViewById(R.id.iv_back_detail_pesanan);
//        lbTypeTruck = root.findViewById(R.id.lb_type_truk);
//        txtAlamatPengirim = root.findViewById(R.id.txt_alamat);
//        txtNamaPengirim = root.findViewById(R.id.txt_nama);
//        txtKontakPengirim = root.findViewById(R.id.txt_kontak);
//        txtHarga = root.findViewById(R.id.txt_total_harga_pesanan);
//        civPengemudi = root.findViewById(R.id.civ_profile_driver);
//        txtNamaPengemudi = root.findViewById(R.id.txt_nama_pengemudi);
//        txtEmailPengemudi = root.findViewById(R.id.txt_email_pengemudi);
//        txtKontakPengemudi = root.findViewById(R.id.txt_no_telp_pengemudi);
//        etCatatan = root.findViewById(R.id.txt_catatan);
//
//        rvDetailbarang = root.findViewById(R.id.rv_detail_barang);
//        rvDetailPenerima = root.findViewById(R.id.rv_alamat_penerima_detail);
//
//        // sesion
//        onloadsession();
//        // load recycler detail barang
//        loaddetailbarang();
//        // load recycler alamat penerima
//        loadalamatpenerima();
//        // load data detail
//        loaddata();
//        // ilangin nav button
//        hidebuttonnav();
//        // semua fungsi button
//        actionbutton();
//        // matiin onbackpress
//        root.setFocusableInTouchMode(true);
//        root.requestFocus();
//        root.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        return root;
    }

//    private void actionbutton() {
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(status.equals("pemesanan")){
//                    BottomNavigationView navigationView = getActivity().findViewById(R.id.nav_view);
//                    navigationView.setVisibility(View.VISIBLE);
//
//                    TabPengiriman inbound = new TabPengiriman();
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, inbound);
//                    ft.commit();
//                }
//                else if(status.equals("riwayat")){
//                    BottomNavigationView navigationView = getActivity().findViewById(R.id.nav_view);
//                    navigationView.setVisibility(View.VISIBLE);
//
//                    RiwayatFragment inbound = new RiwayatFragment();
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, inbound);
//                    ft.commit();
//                }
//            }
//        });
//        /*binding.btnPilihPengemudi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("id_order", id_order);
//                PilihPengemudiFragment inbound = new PilihPengemudiFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                inbound.setArguments(bundle);
//                ft.commit();
//            }
//        });*/
//
//    }
//
//    private void hidebuttonnav() {
//        BottomNavigationView navigationView = getActivity().findViewById(R.id.nav_view);
//        navigationView.setVisibility(View.GONE);
//    }
//
//    private void loaddata() {
//
//        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading ...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        ReqBursaPengiriman reqBursaPengiriman = new ReqBursaPengiriman();
//        reqBursaPengiriman.setId_order(id_order);//134
//
//        Call<ResDetailPengiriman> getListData = API.service().detailBursaPengiriman2(token, reqBursaPengiriman);
//        getListData.enqueue(new Callback<ResDetailPengiriman>() {
//            @Override
//            public void onResponse(Call<ResDetailPengiriman> call, Response<ResDetailPengiriman> response) {
//                pDialog.dismissWithAnimation();
//                if (response.code() == 200) {
//                    ResDetailPengiriman resDetailPengiriman = response.body();
//                    ResDetailPengirimanIsi resDetailPengirimanIsis = resDetailPengiriman.getDetail();
//                    ResDetailPengirim resDetailPengirim = resDetailPengiriman.getPengirim();
//                    List<ResDetailPenerima> resDetailPenerima = resDetailPengiriman.getPenerima();
//                    //List<ResDetailPengirimanBarang> resDetailPengirimanBarangs = resDetailPengiriman.getDetail_barang();
//
//                    /* Isi Pengiriman */
//                    txtHashtag.setText(String.valueOf(resDetailPengirimanIsis.getNo_order()));
//                    txtAsal.setText(resDetailPengirimanIsis.getKab_asal());
//                    txtTujuan.setText(resDetailPengirimanIsis.getKab_tujuan());
//                    txtTanggal.setText(resDetailPengirimanIsis.getTgl_pengiriman());
//                    txtTop.setText(resDetailPengirimanIsis.getNama_perjanjian());
//                    lbTypeTruck.setText(resDetailPengirimanIsis.getKab_asal());
//                    txtMuatan.setText(String.valueOf(resDetailPengirimanIsis.getCapacity()));
//                    txtTypeTruck.setText(resDetailPengirimanIsis.getJenis_kendaraan());
//                    //txtTypeLayanan.setText(resDetailPengirimanIsis.getT);
//                    txtStatus.setText(resDetailPengirimanIsis.getNama_perjanjian());
//                    int i = (int) resDetailPengirimanIsis.getHarga();
//                    txtHarga.setText("Rp "+ ToRupiah(i));
//
//                    /* Pengirim */
//                    txtAlamatPengirim.setText(resDetailPengirim.getAlamat());
//                    txtNamaPengirim.setText("Nama : "+resDetailPengirim.getNama_pengirim());
//                    txtKontakPengirim.setText("Kontak : "+resDetailPengirim.getNo_hp());
//
//                    /* Penerima */
//                    allListPenerima.addAll(resDetailPenerima);
//                    alamatPenerimaAdapter.notifyDataSetChanged();
//
//                    /* Detail Barang */
//                    //allListBarang.addAll(resDetailPengirimanBarangs);
//                    //detailBarangAdapter.notifyDataSetChanged();
//
//                    /* Pengemudi */
//                    if(resDetailPengirimanIsis.getImage_truck()!=null){
//                        Glide.with(getActivity())
//                                .load(resDetailPengirimanIsis.getImage_truck())
//                                .centerCrop()
//                                .placeholder(R.drawable.truk_card)
//                                .into(civPengemudi);
//                    }
//                    else{
//                        civPengemudi.setVisibility(View.GONE);
//                    }
//
//                    txtNamaPengemudi.setText(resDetailPengirimanIsis.getNama_driver());
//                    txtEmailPengemudi.setText(resDetailPengirimanIsis.getEmail_driver());
//                    txtKontakPengemudi.setText(resDetailPengirimanIsis.getNohp_driver());
//
//                    /* Catatan */
//                    etCatatan.setText(resDetailPengirimanIsis.getCatatan());
//
//                    pDialog.dismissWithAnimation();
//                }
//                pDialog.dismissWithAnimation();
//            }
//
//            @Override
//            public void onFailure(Call<ResDetailPengiriman> call, Throwable t) {
//                t.printStackTrace();
//                pDialog.dismissWithAnimation();
//
//                //pDialog.dismissWithAnimation();
//            }
//        });
//
//    }
//
//    private void loadalamatpenerima() {
//
//        allListPenerima = new ArrayList<>();
//        alamatPenerimaAdapter = new AlamatPenerimaAdapter(getActivity(), allListPenerima);
//        rvDetailPenerima.setAdapter(alamatPenerimaAdapter);
//        rvDetailPenerima.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
//        rvDetailPenerima.addItemDecoration(new GridSpacingItemDecoration(2, 2, true, 2));
//
//    }
//
//    private void loaddetailbarang() {
//
//        allListBarang = new ArrayList<>();
//        detailBarangAdapter = new DetailBarangAdapter(getContext(), allListBarang);
//        rvDetailbarang.setAdapter(detailBarangAdapter);
//        rvDetailbarang.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
//        rvDetailbarang.addItemDecoration(new GridSpacingItemDecoration(2, 2, true, 2));
//
//    }
//
//    private void onloadsession() {
//        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
//        username = prefs.getString("username", "");
//        token = prefs.getString("token", "");
//
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            id_order = bundle.getInt("id_order");
//            status = bundle.getString("status");
//        }
//
//    }
//
//    public String ToRupiah(int harga) {
//        String format = String.format(Locale.getDefault(), "%,d", harga);
//        format = format.replace(",", ".");
//        //format = format;
//        return format;
//    }
}