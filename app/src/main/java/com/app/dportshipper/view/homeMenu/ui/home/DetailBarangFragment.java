package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDetailBarang2Binding;
import com.app.dportshipper.databinding.FragmentDetailBarangBinding;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.NewAlamat;
import com.app.dportshipper.model.response.MResKategoriBarang;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailBarangFragment extends Fragment {

    private FragmentDetailBarang2Binding binding;
    ArrayList<MListDataBarang> listDataBarang;
    private ArrayAdapter adapterKatbarang;
    List<String> dataKatBarang = new ArrayList<>();
    private int idKatBarang;
    private String valueNmKatBarang;
    private List<MResKategoriBarang> listDataKatbarang;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBarang2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadadapterspinner();
        loadapi();
        onclick();

        return root;
    }

    private void loadapi() {
        Call<List<MResKategoriBarang>> callKategoribarang = API.service().getKategoribarang(token);
        callKategoribarang.enqueue(new Callback<List<MResKategoriBarang>>() {
            @Override
            public void onResponse(Call<List<MResKategoriBarang>> call, Response<List<MResKategoriBarang>> response) {
                if (response.isSuccessful()){
                    listDataKatbarang=response.body();

                    for (MResKategoriBarang mResKategoriBarang:listDataKatbarang) {

                        Log.d("sidik DTA",mResKategoriBarang.getJenis_barang());
                        dataKatBarang.add(mResKategoriBarang.getJenis_barang());
                    }
                    adapterKatbarang.notifyDataSetChanged();

                }else {

                }
            }

            @Override
            public void onFailure(Call<List<MResKategoriBarang>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void loadadapterspinner() {
        adapterKatbarang= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataKatBarang);
        binding.spKategoriBarang.setAdapter(adapterKatbarang);

        binding.spKategoriBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueNmKatBarang = parent.getItemAtPosition(position).toString();
                MResKategoriBarang mResKategoriBarang = listDataKatbarang.get(position);
                idKatBarang = mResKategoriBarang.getId_ktg_barang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
        }
    }

    private void onclick() {
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    private void validasi() {

        //String getSpn = sp_kategori.getSelectedItem().toString();
        String getBobot = binding.etBeratBarang.getText().toString();
        String getJmlBarang = binding.etJumlahBarang.getText().toString();
        String getPanjang = binding.etPanjang.getText().toString();
        String getLebar = binding.etLebar.getText().toString();
        String getTinggi = binding.etTinggi.getText().toString();

        // Check if all strings are null or not
        if (getBobot.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Bobot barang")
                    .show();
        }else if (getJmlBarang.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Jumlah barang")
                    .show();
        }else if (getPanjang.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Panjang barang")
                    .show();
        }else if (getLebar.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Lebar barang")
                    .show();
        }else if (getTinggi.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Tinggi barang")
                    .show();
        }

        else {
            onloadProcess(getBobot,getJmlBarang,getPanjang,getLebar,getTinggi);
        }
    }

    private void onloadProcess(String getBobot, String getJmlBarang, String getPanjang, String getLebar, String getTinggi) {
        ArrayList<MListDataBarang> listDataBarangNew = new ArrayList<>();
        MListDataBarang mListDataBarang = new MListDataBarang();
        mListDataBarang.setId_kategori_barang(idKatBarang);
        mListDataBarang.setNama_barang(valueNmKatBarang);
        mListDataBarang.setBobot_barang(Integer.parseInt(getBobot));
        mListDataBarang.setKuantitas_barang(Integer.parseInt(getJmlBarang));
        mListDataBarang.setPanjang_barang(Integer.parseInt(getPanjang));
        mListDataBarang.setLebar_barang(Integer.parseInt(getLebar));
        mListDataBarang.setTinggi_barang(Integer.parseInt(getTinggi));
        listDataBarangNew.add(mListDataBarang);

        BookingPengirimanFragment ringkasanPesananFragment = new BookingPengirimanFragment();
        Bundle bundle = new Bundle();

        if (listDataBarang!=null) {
            for (MListDataBarang barangList : listDataBarangNew) {
                listDataBarang.add(barangList);
            }
            bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
        }
        else{
            bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarangNew);
        }
        ringkasanPesananFragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, ringkasanPesananFragment);
        ft.commit();
    }
}