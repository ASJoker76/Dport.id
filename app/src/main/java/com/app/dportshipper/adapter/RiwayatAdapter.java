package com.app.dportshipper.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.R;
import com.app.dportshipper.databinding.ListPengirimanNewBinding;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.model.response.ResBursaPengiriman;
//import com.app.dportshipper.view.homeMenu.ui.home.DetailRingkasanOrder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<ResBursaPengiriman> dataPengiriman;
    private int lastPosition = -1;
    private ListPengirimanNewBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public RiwayatAdapter(Fragment myFragment, ArrayList<ResBursaPengiriman> dataPengiriman) {
        this.myFragment = myFragment;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @Override
    public RiwayatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewBinding = ListPengirimanNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RiwayatAdapter.ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.ViewHolder holder, int position) {
        ResBursaPengiriman resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.tvNamaPerusahaan.setText(resBursaPengiriman.getNama_pengirim());
        holder.viewBinding.tvTujuan.setText(String.valueOf(resBursaPengiriman.getKab_tujuan()));
        holder.viewBinding.tvAsal.setText(String.valueOf(resBursaPengiriman.getKab_asal()));
        holder.viewBinding.tvNamaBarang.setText(resBursaPengiriman.getKategori_barang());
        holder.viewBinding.tvTanggal.setText(resBursaPengiriman.getTgl_muat());
        holder.viewBinding.tvStatusPembayaran.setText(resBursaPengiriman.getStatus_pengiriman());
        Glide.with(myFragment)
                .load(resBursaPengiriman.getPic_file_path())
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(viewBinding.ivTransporterRekomendasi);
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

        private ListPengirimanNewBinding viewBinding;

        public ViewHolder(@NonNull ListPengirimanNewBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
