package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.dportshipper.R;
import com.app.dportshipper.databinding.ActivityChangePasswordBinding;
import com.app.dportshipper.databinding.FragmentDetailBarangBinding;
import com.app.dportshipper.databinding.FragmentDetailPengirimanBinding;

public class DetailBarangFragment extends Fragment {

    private FragmentDetailBarangBinding binding;
    private int id_order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBarangBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_order = bundle.getInt("id_order");
            String jenis = bundle.getString("jenis");
            int berat = bundle.getInt("berat");
            int jumlah = bundle.getInt("jumlah");
            String dimensi = bundle.getString("dimensi");
            String muatan = bundle.getString("muatan");
            String nilai_barang = bundle.getString("nilai_barang");
            String catatan = bundle.getString("catatan");

            binding.tvJenisBarang.setText(jenis);
            binding.tvBeratBarang.setText(berat+"");
            binding.tvJumlahBarang.setText(jumlah+"");
            binding.tvDimensi.setText(dimensi);
            binding.tvMuatan.setText(muatan);
            binding.tvNilaiBarang.setText(nilai_barang);
            binding.tvCatatan.setText(catatan);
        }

        binding.lyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order", id_order);
                DetailPengirimanFragment fragementIntent = new DetailPengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}