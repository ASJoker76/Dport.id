package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentBookingPengirimanBinding;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.view.login.LoginActivity;
import com.bumptech.glide.Glide;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private int id_order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadapidetail();

        return root;
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
//            type_send = bundle.getString("type_send");
//            type_service = bundle.getInt("type_service");
//            tanggal = bundle.getString("tanggal");
            id_order = bundle.getInt("id_order");
            id_produk_transporter = bundle.getInt("id_produk_transporter");
        }
    }

    private void loadapidetail() {
        ReqBursaPengiriman reqBursaPengiriman = new ReqBursaPengiriman();
        reqBursaPengiriman.setId_order(id_order);

        Call<ResDetailPengiriman> getListDetailRingkasan = API.service().detailBursaPengiriman2(token, reqBursaPengiriman);
        getListDetailRingkasan.enqueue(new Callback<ResDetailPengiriman>() {
            @Override
            public void onResponse(Call<ResDetailPengiriman> call, Response<ResDetailPengiriman> response) {
                if (response.code() == 200){
//                    String js = new Gson().toJson(response.body());
//                    Log.d("sidik Detail Barang : ",js);
                    ResDetailPengiriman resDetailPengiriman = response.body();

                    Glide.with(getActivity())
                            .load(resDetailPengiriman.getDetail().getImage_truck())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivTruk);

                    binding.tvNamaTruk.setText(resDetailPengiriman.getDetail().getNama_transporter());
                    binding.tvPlatNo.setText(resDetailPengiriman.getDetail().getPolice_no()+"");
                    binding.tvAlamatPengirim.setText(resDetailPengiriman.getPengirim().getAlamat());
                    binding.etTglPencarianAl.setText(resDetailPengiriman.getDetail().getTgl_pengiriman());

                    //detail armada
                    binding.tvTipeKendaraan.setText(resDetailPengiriman.getDetail().getJenis_kendaraan());
                    binding.tvMerek.setText(resDetailPengiriman.getDetail().getBrand());
                    binding.tvNoKir.setText(resDetailPengiriman.getDetail().getNo_surat_jalan());
                    binding.tvDimensi.setText(resDetailPengiriman.getDetail().getHeigth()+" cm x "+resDetailPengiriman.getDetail().getWidth()+" cm x "+ resDetailPengiriman.getDetail().getLength()+" cm");
                    binding.tvMuatan.setText(resDetailPengiriman.getDetail().getCapacity() +" Kg");
                    
                    //tarif pengiriman
                    binding.tvBiayaPengiriman.setText("Rp. "+ToRupiah(resDetailPengiriman.getDetail().getHarga()));
                    binding.tvBiayaPpn.setText("Rp. "+ToRupiah(resDetailPengiriman.getDetail().getPpn_shipper()));
                    binding.tvSubTotal.setText("Rp. "+ToRupiah(resDetailPengiriman.getDetail().getTotal_harga()));

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