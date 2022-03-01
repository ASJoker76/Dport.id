package com.app.dportshipper.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.app.dportshipper.databinding.ListBarangBinding;
import com.app.dportshipper.databinding.ListPengirimanFavoriteBinding;
import com.app.dportshipper.model.DataPencarian;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.view.homeMenu.ui.home.DetailPengirimanNewFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<MListDataBarang> dataPengiriman;
    private int lastPosition = -1;
    private ListBarangBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private String tanggal, type_send;
    private int type_service;

    public AdapterBarang(ArrayList<MListDataBarang> dataPengiriman) {
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @Override
    public AdapterBarang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListBarangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterBarang.ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarang.ViewHolder holder, int position) {
        MListDataBarang resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.tvBobotBarangRingkasan.setText(resBursaPengiriman.getBobot_barang()+"");
        holder.viewBinding.tvJumlahBarangRingkasan.setText(resBursaPengiriman.getKuantitas_barang()+"");
        holder.viewBinding.tvKategoriBarangRingkasan.setText(resBursaPengiriman.getKuantitas_barang()+"");
        holder.viewBinding.tvPanjangBarangRingkasan.setText(String.valueOf(resBursaPengiriman.getPanjang_barang()));
        holder.viewBinding.tvLebarcmPcsRingkasan.setText(String.valueOf(resBursaPengiriman.getLebar_barang()));
        holder.viewBinding.tvTinggiCmBarangRingkasan.setText(String.valueOf(resBursaPengiriman.getTinggi_barang()));
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

        private ListBarangBinding viewBinding;

        public ViewHolder(@NonNull ListBarangBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
