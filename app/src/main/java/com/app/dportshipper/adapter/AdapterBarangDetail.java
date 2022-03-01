package com.app.dportshipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.databinding.ListBarangBinding;
import com.app.dportshipper.databinding.ListItemBarangBinding;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.response.ResDetailPengirimanBarang;

import java.util.ArrayList;

public class AdapterBarangDetail extends RecyclerView.Adapter<AdapterBarangDetail.ViewHolder> {

    private Fragment myFragment;
    private Context context;
    private ArrayList<ResDetailPengirimanBarang> dataPengiriman;
    private int lastPosition = -1;
    private ListItemBarangBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private String tanggal, type_send;
    private int type_service;

    public AdapterBarangDetail(Context context, ArrayList<ResDetailPengirimanBarang> dataPengiriman) {
        this.context = context;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @Override
    public AdapterBarangDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewBinding = ListItemBarangBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterBarangDetail.ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarangDetail.ViewHolder holder, int position) {
        ResDetailPengirimanBarang resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.tvMuatan.setText(resBursaPengiriman.getBobot_barang()+"");
        holder.viewBinding.tvJumlahBarang.setText(resBursaPengiriman.getKuantitas_barang()+"");
        holder.viewBinding.tvJenisBarang.setText(resBursaPengiriman.getJenis_barang()+"");
        holder.viewBinding.tvDimensi.setText(String.valueOf(resBursaPengiriman.getPanjang_barang() + "cm x " + resBursaPengiriman.getLebar_barang() + "cm x " + resBursaPengiriman.getTinggi_barang() + "cm"));
        holder.viewBinding.tvBeratBarang.setText(String.valueOf(resBursaPengiriman.getBobot_barang()));
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

        private ListItemBarangBinding viewBinding;

        public ViewHolder(@NonNull ListItemBarangBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
