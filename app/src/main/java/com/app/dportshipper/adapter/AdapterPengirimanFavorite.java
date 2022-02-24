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
import com.app.dportshipper.databinding.ListPengirimanFavoriteBinding;
import com.app.dportshipper.model.DataPencarian;
import com.app.dportshipper.view.homeMenu.ui.home.DetailPengirimanNewFragment;
import com.app.dportshipper.view.homeMenu.ui.home.HasilPencarianFragment;
import com.app.dportshipper.view.homeMenu.ui.pengiriman.DetailPengirimanFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterPengirimanFavorite extends RecyclerView.Adapter<AdapterPengirimanFavorite.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<DataPencarian> dataPengiriman;
    private int lastPosition = -1;
    private ListPengirimanFavoriteBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    private String tanggal, type_send;
    private int type_service;

    public AdapterPengirimanFavorite(Fragment myFragment, ArrayList<DataPencarian> dataPengiriman,String type_send,int type_service,String tanggal) {
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
        viewBinding = ListPengirimanFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataPencarian resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.tvNamaPerusahaan.setText(resBursaPengiriman.getNama_transporter());
        holder.viewBinding.tvTujuan.setText("Rp. "+ToRupiah(resBursaPengiriman.getHarga()));
        holder.viewBinding.tvArmada.setText(String.valueOf(resBursaPengiriman.getDimension()));
        holder.viewBinding.tvJangkauan.setText(String.valueOf(resBursaPengiriman.getWidth()));
        Glide.with(myFragment)
                .load(resBursaPengiriman.getPic_file_path())
                .centerCrop()
                .placeholder(R.drawable.image)
                .into(viewBinding.ivTransporterRekomendasi);
        holder.viewBinding.clView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle bundle = new Bundle();
//                bundle.putString("kab_asal", resBursaPengiriman.getKab_asal());
//                bundle.putString("kab_tujuan", resBursaPengiriman.getKab_tujuan());
//                bundle.putString("type_send", type_send);
//                bundle.putInt("type_service", type_service);
//                bundle.putString("tanggal", tanggal);
//                bundle.putInt("id_produk_transporter", resBursaPengiriman.getId_produk_transporter());

                SharedPreferences.Editor editor = myFragment.getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
                editor.putString("kab_asal", resBursaPengiriman.getKab_asal());
                editor.putString("kab_tujuan", resBursaPengiriman.getKab_tujuan());
                editor.putString("type_send", type_send);
                editor.putInt("type_service", type_service);
                editor.putString("tanggal", tanggal);
                editor.putInt("id_produk_transporter", resBursaPengiriman.getId_produk_transporter());
                editor.putInt("id_transporter", resBursaPengiriman.getId_transporter());
                editor.apply();

                DetailPengirimanNewFragment fragementIntent = new DetailPengirimanNewFragment();
                FragmentManager manager = myFragment.getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                //fragementIntent.setArguments(bundle);
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

        private ListPengirimanFavoriteBinding viewBinding;

        public ViewHolder(@NonNull ListPengirimanFavoriteBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }

    public String ToRupiah(long harga) {
        String format = String.format(Locale.getDefault(), "%,d", harga);
        format = format.replace(",", ".");
        //format = format;
        return format;
    }
}
