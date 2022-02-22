package com.app.dportshipper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dportshipper.databinding.ListStatusBinding;
import com.app.dportshipper.model.response.ResStatusTab;

import java.util.ArrayList;

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.ViewHolder> {

    private Fragment myFragment;
    private ArrayList<ResStatusTab> dataPengiriman;
    private int lastPosition = -1;
    private ListStatusBinding viewBinding;
    private OnKlikListener onKlikListener;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public AdapterStatus(Fragment myFragment, ArrayList<ResStatusTab> dataPengiriman, OnKlikListener onKlikListener) {
        this.myFragment = myFragment;
        this.dataPengiriman = dataPengiriman;
        this.onKlikListener = onKlikListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengiriman_status, parent, false);
        viewBinding = ListStatusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding,onKlikListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResStatusTab resBursaPengiriman = dataPengiriman.get(position);
        holder.viewBinding.btnStatus.setText(resBursaPengiriman.getNama_status());
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

        private ListStatusBinding viewBinding;
        OnKlikListener onKlikListener;

        public ViewHolder(@NonNull ListStatusBinding binding, OnKlikListener onKlikListener) {
            super(binding.getRoot());

            viewBinding = binding;
            this.onKlikListener = onKlikListener;
            binding.btnStatus.setOnClickListener(this);
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
