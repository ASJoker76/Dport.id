package com.app.dportshipper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.databinding.ListAlamatPenerimaBinding;
import com.app.dportshipper.model.MAlamatPenerima;

import java.util.ArrayList;

public class AdapterAlamat extends RecyclerView.Adapter<AdapterAlamat.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<MAlamatPenerima> dataPengiriman;
    private int lastPosition = -1;
    private ListAlamatPenerimaBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private String tanggal, type_send;
    private int type_service;

    public AdapterAlamat(ArrayList<MAlamatPenerima> dataPengiriman) {
        this.dataPengiriman = dataPengiriman;
        this.type_send = type_send;
        this.type_service = type_service;
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListAlamatPenerimaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MAlamatPenerima resMAlamatPenerima = dataPengiriman.get(position);
        holder.viewBinding.etAlamatPengirim.setText(resMAlamatPenerima.getNamaPenerima()+"\n"+resMAlamatPenerima.getAlamat());
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

        private ListAlamatPenerimaBinding viewBinding;

        public ViewHolder(@NonNull ListAlamatPenerimaBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
