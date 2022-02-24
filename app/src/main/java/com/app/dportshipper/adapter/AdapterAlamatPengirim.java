package com.app.dportshipper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.databinding.ListAlamatPenerimaBinding;

import com.app.dportshipper.databinding.ListAlamatPengirimBinding;
import com.app.dportshipper.model.response.ResDataAlamatPenerima;

import java.util.ArrayList;

public class AdapterAlamatPengirim extends RecyclerView.Adapter<AdapterAlamatPengirim.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<ResDataAlamatPenerima> dataPengiriman;
    private int lastPosition = -1;
    private ListAlamatPengirimBinding viewBinding;
    private OnKlikListener onKlikListener;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private String tanggal, type_send;
    private int type_service;

    public AdapterAlamatPengirim(Fragment myFragment,ArrayList<ResDataAlamatPenerima> dataPengiriman, OnKlikListener onKlikListener) {
        this.myFragment = myFragment;
        this.dataPengiriman = dataPengiriman;
        this.onKlikListener = onKlikListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListAlamatPengirimBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding,onKlikListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResDataAlamatPenerima resMAlamatPenerima = dataPengiriman.get(position);
        holder.viewBinding.tvNama.setText(resMAlamatPenerima.getNama_penerima());
        holder.viewBinding.tvAlamat.setText(resMAlamatPenerima.getAlamat()+"\n\n"+resMAlamatPenerima.getNo_hp());
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ListAlamatPengirimBinding viewBinding;
        OnKlikListener onKlikListener;

        public ViewHolder(@NonNull ListAlamatPengirimBinding binding, OnKlikListener onKlikListener) {
            super(binding.getRoot());

            viewBinding = binding;
            this.onKlikListener = onKlikListener;
            binding.clView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onKlikListener.onKlikClick(getAdapterPosition());
        }
    }

    public interface OnKlikListener{
        void onKlikClick(int position);
    }
}
