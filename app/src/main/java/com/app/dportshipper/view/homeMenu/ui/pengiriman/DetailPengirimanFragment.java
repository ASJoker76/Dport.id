package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDetailPengirimanBinding;
import com.app.dportshipper.databinding.FragmentDetailPesananBinding;
import com.app.dportshipper.databinding.FragmentPengirimanBinding;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailPengirimanBarang;
import com.app.dportshipper.model.response.ResDetailPengirimanIsi;
import com.app.dportshipper.utils.ToRupiah;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengirimanFragment extends Fragment {


    private FragmentDetailPengirimanBinding binding;
    private String token;
    private int id_order;

    private List<ResDetailPengirimanBarang> resDetailBarangs;
    private ResDetailPengirimanIsi resDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loaddataapi();

        actiononclick();

        return root;
    }

    private void actiononclick() {
        binding.lyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order", id_order);
                bundle.putString("jenis", resDetailBarangs.get(0).getJenis_barang()+"d");
                bundle.putInt("berat", resDetailBarangs.get(0).getBobot_barang());
                bundle.putInt("jumlah", resDetailBarangs.get(0).getKuantitas_barang());
                bundle.putString("dimensi", resDetailBarangs.get(0).getPanjang_barang()+"cm x "+resDetailBarangs.get(0).getLebar_barang()+"cm x "+resDetailBarangs.get(0).getTinggi_barang()+"cm");
                bundle.putString("muatan", resDetails.getCapacity()+"");
                bundle.putString("nilai_barang", resDetails.getHarga()+"");
                bundle.putString("catatan", resDetails.getCatatan());
                DetailBarangFragment fragementIntent = new DetailBarangFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.ivDetailBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lyDetail.performClick();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        //username = prefs.getString("username","");
        token = prefs.getString("token", "");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_order = bundle.getInt("id_order");
        }
    }

    private void loaddataapi() {
        ReqBursaPengiriman reqBursaPengiriman = new ReqBursaPengiriman();
        reqBursaPengiriman.setId_order(id_order);

        Call<ResDetailPengiriman> getListDetailRingkasan = API.service().detailBursaPengiriman(token, reqBursaPengiriman);
        getListDetailRingkasan.enqueue(new Callback<ResDetailPengiriman>() {
            @Override
            public void onResponse(Call<ResDetailPengiriman> call, Response<ResDetailPengiriman> response) {
                if (response.code() == 200) {

                    ResDetailPengiriman resDetailRingkasan = response.body();
                    String kl = new Gson().toJson(resDetailRingkasan);
                    Log.d("isi data", kl);

                    Glide.with(getActivity())
                            .load(resDetailRingkasan.getDetail().getImage_truck())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivTruk);

                    binding.tvNamaTruk.setText(resDetailRingkasan.getDetail().getNama_transporter());
                    binding.tvPlatNo.setText(resDetailRingkasan.getDetail().getPolice_no());

                    binding.tvAlamatPengirim.setText(resDetailRingkasan.getPengirim().getAlamat());
                    binding.tvAlamatPenerima.setText(resDetailRingkasan.getPenerima().get(0).getAlamat());

                    binding.tvTglPencarianAl.setText(resDetailRingkasan.getDetail().getTgl_pengiriman());

                    binding.tvTarifPengiriman.setText(ToRupiah(resDetailRingkasan.getDetail().getHarga()));
                    binding.tvBiayaPpn.setText(ToRupiah(resDetailRingkasan.getDetail().getPpn_shipper()));
                    binding.tvBiayaInap.setText(ToRupiah(resDetailRingkasan.getDetail().getBiaya_inap()));

                    binding.tvSubTotal.setText(ToRupiah(resDetailRingkasan.getDetail().getTotal_harga()));

                    binding.tvNamaPengirim.setText(resDetailRingkasan.getPengirim().getNama_pengirim());
                    binding.tvNoTlpPengirim.setText(resDetailRingkasan.getPengirim().getNo_hp()+"");

                    binding.tvNamaPenerima.setText(resDetailRingkasan.getPenerima().get(0).getNama_penerima());
                    binding.tvNoTlpPenerima.setText(resDetailRingkasan.getPenerima().get(0).getNo_hp()+"");

                    Glide.with(getActivity())
                            .load(resDetailRingkasan.getDetail().getImage_transporter())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivPhotoDriver);

                    binding.tvNamaDriver.setText(resDetailRingkasan.getDetail().getNama_driver());
                    binding.tvNoTlpDriver.setText(resDetailRingkasan.getDetail().getNohp_driver());

                    resDetailBarangs = resDetailRingkasan.getDetail_barang();
                    resDetails = resDetailRingkasan.getDetail();
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
}