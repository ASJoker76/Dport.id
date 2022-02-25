package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDetailPengirimanNewBinding;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.request.ReqDetailTruck;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailTruck;
import com.app.dportshipper.utils.SliderAdapter;
import com.app.dportshipper.utils.SliderItems;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailPengirimanNewFragment extends Fragment {

    private FragmentDetailPengirimanNewBinding binding;
    private Handler sliderHandler = new Handler();
    private String token;
    private int idOrder;
    private String status;
    List<SliderItems> sliderItems = new ArrayList<>();
    private int id_produk_transporter;
    private String tanggal, kab_asal, kab_tujuan, type_send;
    private int id_order;
    private int type_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPengirimanNewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        sliderimage();
        hidenavbottom();
        loadapidetail();
        onclick();

        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });

        return root;
    }

    private void onclick() {
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
                editor.putBoolean("loadalamat", false);
                editor.apply();
//                Bundle bundle = new Bundle();
//                bundle.putString("kab_asal", kab_asal);
//                bundle.putString("kab_tujuan", kab_tujuan);
//                bundle.putString("type_send", type_send);
//                bundle.putInt("type_service", type_service);
//                bundle.putString("tanggal", tanggal);
//                bundle.putInt("id_produk_transporter", id_produk_transporter);
//                bundle.putInt("id_order", id_order);
//                bundle.putInt("id_produk_transporter", id_produk_transporter);
                BookingPengirimanFragment fragementIntent = new BookingPengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                //fragementIntent.setArguments(bundle);
                transaction.commit();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("data_transporter", 0);
                preferences.edit().clear().apply();

                CariPengirimanFragment paramPengiriman = new CariPengirimanFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransactionKorlap = fragmentManager.beginTransaction();
                fragmentTransactionKorlap.replace(R.id.nav_host_fragment, paramPengiriman).addToBackStack(null);
                fragmentTransactionKorlap.commit();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        SharedPreferences prefs_data_transporter = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE);
        kab_asal = prefs_data_transporter.getString("kab_asal", "");
        kab_tujuan = prefs_data_transporter.getString("kab_tujuan", "");
        type_send = prefs_data_transporter.getString("type_send", "");
        type_service = prefs_data_transporter.getInt("type_service", 0);
        tanggal = prefs_data_transporter.getString("tanggal", "");
        id_produk_transporter = prefs_data_transporter.getInt("id_produk_transporter", 0);
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            kab_asal = bundle.getString("kab_asal");
//            kab_tujuan = bundle.getString("kab_tujuan");
//            type_send = bundle.getString("type_send");
//            type_service = bundle.getInt("type_service");
//            tanggal = bundle.getString("tanggal");
//            id_produk_transporter = bundle.getInt("id_produk_transporter");
//        }
    }

    private void loadapidetail() {
        ReqDetailTruck reqDetailTruck = new ReqDetailTruck();
        reqDetailTruck.setType_send(type_send);
        reqDetailTruck.setType_service(type_service);
        reqDetailTruck.setTanggal(tanggal);
        reqDetailTruck.setId_produk_transporter(id_produk_transporter);

        Call<ResDetailTruck> truckCall = API.service().detailTruckPesanan(token, reqDetailTruck);
        truckCall.enqueue(new Callback<ResDetailTruck>() {
            @Override
            public void onResponse(Call<ResDetailTruck> call, Response<ResDetailTruck> response) {
                if (response.code() == 200) {
                    ResDetailTruck resDetailRingkasan = response.body();
                    sliderItems.clear();
                    //image slider truk
                    if (resDetailRingkasan.getData().getPic_file_path() != null) {
                        sliderItems.add(new SliderItems(resDetailRingkasan.getData().getPic_file_path()));
                    }

                    binding.viewPagerImageSlider.setAdapter(new SliderAdapter(sliderItems, binding.viewPagerImageSlider));

                    binding.viewPagerImageSlider.setClipToPadding(false);
                    binding.viewPagerImageSlider.setClipChildren(false);
                    binding.viewPagerImageSlider.setOffscreenPageLimit(3);
                    binding.viewPagerImageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {
                            float r = 1 - Math.abs(position);
                            page.setScaleY(0.85f + r * 0.15f);
                        }
                    });

                    binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer);

                    binding.viewPagerImageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            sliderHandler.removeCallbacks(sliderRunnable);
                            sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
                        }
                    });


                    binding.tvNamaTransporter.setText(resDetailRingkasan.getData().getNama_transporter());
                    binding.tvNomorPolisi.setText(resDetailRingkasan.getData().getPolice_no());
                    binding.etAsalPencarianAl.setText(resDetailRingkasan.getData().getKab_asal());
                    binding.etTujuanPencarianAl.setText(resDetailRingkasan.getData().getKab_tujuan());
                    binding.etTglPencarianAl.setText(tanggal);
                    binding.etTarif.setText("Rp. " + ToRupiah(resDetailRingkasan.getData().getHarga()) + ",-");

                    //detail
                    binding.tvTipeKendaraan.setText(resDetailRingkasan.getData().getBrand() + "");
                    binding.tvMerek.setText("");
                    binding.tvNoKir.setText(resDetailRingkasan.getData().getPolice_no());

                    binding.tvDimensi.setText(resDetailRingkasan.getData().getDimension() + "");
                    binding.tvMuatan.setText(resDetailRingkasan.getData().getCapacity() + " Kg");

                    //set ke publik
//                    id_produk_transporter = resDetailRingkasan.getDetail().getId_produk_transporter();
//                    tanggal = resDetailRingkasan.getDetail().getTgl_pengiriman();
//                    id_order = resDetailRingkasan.getDetail().getId_order();
                } else if (response.code() == 201) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Tidak menemukan data")
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResDetailTruck> call, Throwable t) {
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

    private void sliderimage() {
//        sliderItems.add(new SliderItems(R.drawable.truk_header));
//        sliderItems.add(new SliderItems(R.drawable.truk_header));
//        sliderItems.add(new SliderItems(R.drawable.truk_header));
    }

    private void hidenavbottom() {
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewPagerImageSlider.setCurrentItem(binding.viewPagerImageSlider.getCurrentItem() + 1);
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }
}