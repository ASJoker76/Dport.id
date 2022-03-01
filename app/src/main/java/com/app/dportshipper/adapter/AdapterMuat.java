package com.app.dportshipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.R;
import com.app.dportshipper.databinding.ListBongkarMuatBinding;
import com.app.dportshipper.model.response.ResDataFotoBongkarBarang;
import com.app.dportshipper.model.response.ResDataFotoMuatBarang;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterMuat extends RecyclerView.Adapter<AdapterMuat.ViewHolder> {

    private Context context;
    private ArrayList<ResDataFotoMuatBarang> dataPengiriman;
    private int lastPosition = -1;
    private ListBongkarMuatBinding viewBinding;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public AdapterMuat(Context context, ArrayList<ResDataFotoMuatBarang> dataPengiriman) {
        this.context = context;
        this.dataPengiriman = dataPengiriman;
    }

    @NonNull
    @Override
    public AdapterMuat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListBongkarMuatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdapterMuat.ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMuat.ViewHolder holder, int position) {
        ResDataFotoMuatBarang resBursaPengiriman = dataPengiriman.get(position);
        Glide.with(context)
                .load(resBursaPengiriman.getImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.truk_header)
                        .transform(new RoundedCorners(16)))
                .into(holder.viewBinding.imgPhoto);
        holder.viewBinding.tvDesciption.setText("Tanggal : "+resBursaPengiriman.getTanggal()+"\n"+
                "Jam : "+resBursaPengiriman.getJam()+"\n" +
                "Description : "+resBursaPengiriman.getDescription());
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

        private ListBongkarMuatBinding viewBinding;

        public ViewHolder(@NonNull ListBongkarMuatBinding binding) {
            super(binding.getRoot());

            viewBinding = binding;

        }
    }
}
