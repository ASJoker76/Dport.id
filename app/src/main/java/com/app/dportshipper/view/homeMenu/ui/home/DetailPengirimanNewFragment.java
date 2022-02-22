package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.utils.SliderAdapter;
import com.app.dportshipper.utils.SliderItems;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private String tanggal;
    private int id_order;

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

        return root;
    }

    private void onclick() {
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
//                bundle.putString("kab_asal", binding.etAsalPencarianAl.getText().toString());
//                bundle.putString("kab_tujuan", binding.etTujuanPencarianAl.getText().toString());
//                bundle.putString("type_send", "1");
//                bundle.putInt("type_service", 1);
//                bundle.putString("tanggal", tanggal);
                //bundle.putLong("harga", harga);
                bundle.putInt("id_order", id_order);
                bundle.putInt("id_produk_transporter", id_produk_transporter);
                BookingPengirimanFragment fragementIntent = new BookingPengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.commit();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            idOrder = bundle.getInt("id_order");
            status = bundle.getString("status");
        }
    }

    private void loadapidetail() {
        ReqBursaPengiriman reqBursaPengiriman = new ReqBursaPengiriman();
        reqBursaPengiriman.setId_order(idOrder);

        Call<ResDetailPengiriman> getListDetailRingkasan = API.service().detailBursaPengiriman2(token, reqBursaPengiriman);
        getListDetailRingkasan.enqueue(new Callback<ResDetailPengiriman>() {
            @Override
            public void onResponse(Call<ResDetailPengiriman> call, Response<ResDetailPengiriman> response) {
                if (response.code() == 200) {
                    ResDetailPengiriman resDetailRingkasan = response.body();
                    String kl = new Gson().toJson(resDetailRingkasan);
                    Log.d("Data", kl);
                    //image slider truk
                    if (resDetailRingkasan.getDetail().getImage_truck()!=null){
                        sliderItems.add(new SliderItems(resDetailRingkasan.getDetail().getImage_truck()));
                    }


                    binding.tvNamaTransporter.setText(resDetailRingkasan.getDetail().getNama_transporter());
                    binding.tvNomorPolisi.setText(resDetailRingkasan.getDetail().getPolice_no());
                    binding.etAsalPencarianAl.setText(resDetailRingkasan.getDetail().getKab_asal());
                    binding.etTujuanPencarianAl.setText(resDetailRingkasan.getDetail().getKab_tujuan());
                    binding.etTglPencarianAl.setText(resDetailRingkasan.getDetail().getTgl_pengiriman());
                    binding.etTarif.setText("Rp. "+ToRupiah(resDetailRingkasan.getDetail().getTotal_harga())+",-");

                    //detail
                    binding.tvTipeKendaraan.setText(resDetailRingkasan.getDetail().getJenis_kendaraan());
                    binding.tvMerek.setText(resDetailRingkasan.getDetail().getBrand());
                    binding.tvNoKir.setText(resDetailRingkasan.getDetail().getNo_surat_jalan());

                    binding.tvDimensi.setText(resDetailRingkasan.getDetail().getHeigth()+" cm x "+resDetailRingkasan.getDetail().getWidth()+" cm x "+ resDetailRingkasan.getDetail().getLength()+" cm");
                    binding.tvMuatan.setText(resDetailRingkasan.getDetail().getCapacity() +" Kg");

                    //set ke publik
                    id_produk_transporter = resDetailRingkasan.getDetail().getId_produk_transporter();
                    tanggal = resDetailRingkasan.getDetail().getTgl_pengiriman();
                    id_order = resDetailRingkasan.getDetail().getId_order();
                }
            }

            @Override
            public void onFailure(Call<ResDetailPengiriman> call, Throwable t) {
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

        binding.viewPagerImageSlider.setAdapter(new SliderAdapter(sliderItems,binding.viewPagerImageSlider));

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