package com.app.dportshipper.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.R;
import com.app.dportshipper.databinding.ListRekomendasiTransporterNewBinding;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.view.homeMenu.ui.home.DetailPengirimanNewFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterRekomendasiTransporter extends RecyclerView.Adapter<AdapterRekomendasiTransporter.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<MResListPengiriman> dataPengiriman;
    private int lastPosition = -1;
    private ListRekomendasiTransporterNewBinding viewBinding;

    private String tanggal, type_send;
    private int type_service;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public AdapterRekomendasiTransporter(Fragment myFragment, ArrayList<MResListPengiriman> dataPengiriman,String type_send,int type_service,String tanggal) {
        this.myFragment = myFragment;
        this.dataPengiriman = dataPengiriman;
        this.type_send = type_send;
        this.type_service = type_service;
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListRekomendasiTransporterNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MResListPengiriman resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.tvNamaPerusahaan.setText(resBursaPengiriman.getNama_pengirim());
        holder.viewBinding.tvTujuan.setText(String.valueOf(resBursaPengiriman.getKab_asal()));
        holder.viewBinding.tvArmada.setText(String.valueOf(resBursaPengiriman.getQty()));
        holder.viewBinding.tvJangkauan.setText(resBursaPengiriman.getKab_tujuan());
        Glide.with(myFragment)
                .load(resBursaPengiriman.getPic_file_path())
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(viewBinding.ivTransporterRekomendasi);
        holder.viewBinding.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = myFragment.getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
//                editor.putString("kab_asal", resBursaPengiriman.getKab_asal());
//                editor.putString("kab_tujuan", resBursaPengiriman.getKab_tujuan());
//                editor.putString("type_send", type_send);
//                editor.putInt("type_service", type_service);
//                editor.putString("tanggal", tanggal);
//                editor.putInt("id_produk_transporter", resBursaPengiriman.getId_transporter());
//                editor.apply();
//
//                DetailPengirimanNewFragment fragementIntent = new DetailPengirimanNewFragment();
//                FragmentManager manager = myFragment.getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.nav_host_fragment, fragementIntent);
//                transaction.commit();
            }
        });
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition)
        {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);
        }
    }

    @Override
    public int getItemCount() {
        return dataPengiriman.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ListRekomendasiTransporterNewBinding viewBinding;

        public ViewHolder(@NonNull ListRekomendasiTransporterNewBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
