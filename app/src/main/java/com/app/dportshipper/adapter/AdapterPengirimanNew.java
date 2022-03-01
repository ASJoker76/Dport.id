package com.app.dportshipper.adapter;

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
import com.app.dportshipper.databinding.ListPengirimanNewBinding;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.view.homeMenu.ui.home.DetailPengirimanNewFragment;
import com.app.dportshipper.view.homeMenu.ui.pengiriman.DetailPengirimanFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterPengirimanNew extends RecyclerView.Adapter<AdapterPengirimanNew.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<MResListPengiriman> dataPengiriman;
    private int lastPosition = -1;
    private ListPengirimanNewBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public AdapterPengirimanNew(Fragment myFragment, ArrayList<MResListPengiriman> dataPengiriman) {
        this.myFragment = myFragment;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListPengirimanNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MResListPengiriman resBursaPengiriman = dataPengiriman.get(position);
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
        holder.viewBinding.clView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order",resBursaPengiriman.getId_order());
                DetailPengirimanFragment fragementIntent = new DetailPengirimanFragment();
                FragmentManager manager = myFragment.getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.commit();
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

        private ListPengirimanNewBinding viewBinding;

        public ViewHolder(@NonNull ListPengirimanNewBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
