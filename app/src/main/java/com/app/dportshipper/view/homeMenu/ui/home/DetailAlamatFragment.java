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
import com.app.dportshipper.databinding.FragmentDetailAlamatBinding;
import com.app.dportshipper.databinding.FragmentDetailBarang2Binding;
import com.app.dportshipper.model.MAlamatPenerima;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.request.MReqProv;
import com.app.dportshipper.model.response.MResKab;
import com.app.dportshipper.model.response.MResKategoriBarang;
import com.app.dportshipper.model.response.MResKec;
import com.app.dportshipper.model.response.MResKel;
import com.app.dportshipper.model.response.MResProv;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailAlamatFragment extends Fragment {


    private FragmentDetailAlamatBinding binding;

    private ArrayList<MListDataBarang> listDataBarang;
    private ArrayList<MAlamatPenerima> listDataAlamat;

    private List<MResProv> listDataProv = new ArrayList<>();

    List<String> dataTypeProv = new ArrayList<>();
    List<String> dataTypeKab = new ArrayList<>();
    List<String> dataTypeKec = new ArrayList<>();
    List<String> dataTypeKel= new ArrayList<>();
    List<String> dataTypeKodePos= new ArrayList<>();

    private ArrayAdapter<String> adapterProv;
    private ArrayAdapter<String> adapterKec;
    private ArrayAdapter<String> adapterKab;
    private ArrayAdapter<String> adapterKel;

    private String valuePro;
    private String valueKel;
    private String valueKab;
    private String valueKec;
    private int valueKodepos;

    private List<MResKel> listDataKel;
    private List<MResKab> listDataKab;
    private List<MResKec> listDataKec;

    private String penerimaProv;
    private String penerimaKota;
    private String penerimaKecamatan;
    private String penerimaKelurahan;


    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailAlamatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadspinner();
        actionbutton();

        return root;
    }

    private void actionbutton() {
        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
    }

    private void validasi() {
        String getNama = binding.etNama.getText().toString();
        String getNotel = binding.etNoTlp.getText().toString();
        String getAlamat = binding.etAlamat.getText().toString();

        // Check if all strings are null or not
        if (getNama.equals("") || getNama.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi nama penerima")
                    .show();
        }else if (getNotel.equals("") || getNotel.length()==0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi nomor telepon penerima")
                    .show();
        }else if (getAlamat.equals("") || getAlamat.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi alamat lengkap penerima")
                    .show();
        }

        else {
            onloadProcess(getNama,getNotel,getAlamat);
        }
    }

    private void onloadProcess(String getNama, String getNotel, String getAlamat) {
        ArrayList<MAlamatPenerima> listDataAlamatNew = new ArrayList<>();
        MAlamatPenerima mAlamatPenerima = new MAlamatPenerima();
        mAlamatPenerima.setNamaPenerima(getNama);
        mAlamatPenerima.setAlamat(getAlamat);
        mAlamatPenerima.setKontakTlp(getNotel);
        mAlamatPenerima.setProvinsi(valuePro);
        mAlamatPenerima.setKabupaten(valueKab);
        mAlamatPenerima.setKecamatan(valueKec);
        mAlamatPenerima.setKelurahan(valueKel);
        mAlamatPenerima.setKodepos(valueKodepos);
        listDataAlamatNew.add(mAlamatPenerima);

        BookingPengirimanFragment ringkasanPesananFragment = new BookingPengirimanFragment();
        Bundle bundle = new Bundle();

        if (listDataAlamat!=null) {
            for (MAlamatPenerima alamatList : listDataAlamatNew) {
                listDataAlamat.add(alamatList);
            }
            bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
        }
        else{
            bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamatNew);
        }
        bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
        ringkasanPesananFragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, ringkasanPesananFragment);
        ft.commit();
    }

    private void loadspinner() {
        callProv();

        adapterProv= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataTypeProv);
        binding.spProvinsi.setAdapter(adapterProv);

        adapterKab= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataTypeKab);
        binding.spKotaKab.setAdapter(adapterKab);

        adapterKec= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataTypeKec);
        binding.spKecamatan.setAdapter(adapterKec);

        adapterKel= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataTypeKel);
        binding.spKelurahan.setAdapter(adapterKel);


        binding.spKelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueKel = parent.getItemAtPosition(position).toString();
                MResKel mResKel = listDataKel.get(position);
                valueKodepos = mResKel.getKode_pos();
                binding.spKodePos.setText(String.valueOf(mResKel.getKode_pos()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valuePro = parent.getItemAtPosition(position).toString();
                if (listDataKab!=null){
                    listDataKab.clear();
                    dataTypeKab.clear();
                    adapterKab.notifyDataSetChanged();
                    listDataKec.clear();
                    dataTypeKec.clear();
                    adapterKec.notifyDataSetChanged();
                    listDataKel.clear();
                    dataTypeKel.clear();
                    adapterKel.notifyDataSetChanged();
                }
                callDataKab(valuePro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spKotaKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueKab = parent.getItemAtPosition(position).toString();
                if (listDataKec!=null){
                    listDataKec.clear();
                    dataTypeKec.clear();
                    adapterKec.notifyDataSetChanged();
                    listDataKel.clear();
                    dataTypeKel.clear();
                    adapterKel.notifyDataSetChanged();
                }
                callDataKec(valueKab,valuePro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueKec = parent.getItemAtPosition(position).toString();
                if (listDataKel!=null){
                    listDataKel.clear();
                    dataTypeKel.clear();
                    adapterKel.notifyDataSetChanged();
                }
                callDataKel(valuePro,valueKab,valueKec);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callProv() {
        Call<List<MResProv>> getDataProv = API.service().getProv(token);
        getDataProv.enqueue(new Callback<List<MResProv>>() {
            @Override
            public void onResponse(Call<List<MResProv>> call, Response<List<MResProv>> response) {
                if (response.isSuccessful()){
                    listDataProv=response.body();

                    for (MResProv tipePengiriman:listDataProv) {
                        dataTypeProv.add(tipePengiriman.getProv());
                    }
                    if (penerimaProv != null) {
                        int spinnerPosition = adapterProv.getPosition(penerimaProv);
                        binding.spProvinsi.setSelection(spinnerPosition);
                    }
                    adapterProv.notifyDataSetChanged();


                }else {

                }
            }

            @Override
            public void onFailure(Call<List<MResProv>> call, Throwable t) {

            }
        });
    }

    private void callDataKab(String valuePro) {
        MReqProv mReqProv = new MReqProv();
        mReqProv.setProvinsi(valuePro);
        Call<List<MResKab>> getDataKab = API.service().getKab(token,mReqProv);
        getDataKab.enqueue(new Callback<List<MResKab>>() {
            @Override
            public void onResponse(Call<List<MResKab>> call, Response<List<MResKab>> response) {
                if (response.isSuccessful()){
                    listDataKab=response.body();

                    for (MResKab tipeKab:listDataKab) {
                        dataTypeKab.add(tipeKab.getKab_kota());
                    }
                    adapterKab.notifyDataSetChanged();
                    if (penerimaKota != null) {
                        int spinnerPosition = adapterKab.getPosition(penerimaKota);
                        binding.spKotaKab.setSelection(spinnerPosition);
                    }


                }else {

                }
            }

            @Override
            public void onFailure(Call<List<MResKab>> call, Throwable t) {

            }
        });
    }

    private void callDataKec(String valueKab, String valuePro) {
        MReqProv mReqkec = new MReqProv();
        mReqkec.setKabupaten(valueKab);
        mReqkec.setProvinsi(valuePro);

        Call<List<MResKec>>getDataKec = API.service().getKec(token,mReqkec);
        getDataKec.enqueue(new Callback<List<MResKec>>() {
            @Override
            public void onResponse(Call<List<MResKec>> call, Response<List<MResKec>> response) {
                if (response.isSuccessful()){
                    listDataKec=response.body();

                    for (MResKec tipeKec:listDataKec) {
                        dataTypeKec.add(tipeKec.getKec());
                    }

                    adapterKec.notifyDataSetChanged();
                    if (penerimaKecamatan != null) {
                        int spinnerPosition = adapterKec.getPosition(penerimaKecamatan);
                        binding.spKecamatan.setSelection(spinnerPosition);
                    }


                }else {

                }
            }

            @Override
            public void onFailure(Call<List<MResKec>> call, Throwable t) {

            }
        });

    }

    private void callDataKel(String valuePro, String valueKab, String valueKec) {
        MReqProv mReqkec = new MReqProv();
        mReqkec.setProvinsi(valuePro);
        mReqkec.setKabupaten(valueKab);
        mReqkec.setKecamatan(valueKec);

        Call<List<MResKel>>getDataKel = API.service().getKel(token,mReqkec);
        getDataKel.enqueue(new Callback<List<MResKel>>() {
            @Override
            public void onResponse(Call<List<MResKel>> call, Response<List<MResKel>> response) {
                if (response.isSuccessful()){
                    listDataKel=response.body();

                    for (MResKel tipeKel:listDataKel) {
                        dataTypeKel.add(tipeKel.getKel());
                    }
                    adapterKel.notifyDataSetChanged();
                    if (penerimaKelurahan != null) {
                        int spinnerPosition = adapterKel.getPosition(penerimaKelurahan);
                        binding.spKelurahan.setSelection(spinnerPosition);
                    }


                }else {

                }
            }

            @Override
            public void onFailure(Call<List<MResKel>> call, Throwable t) {

            }
        });

    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
            listDataAlamat = bundle.getParcelableArrayList("listDataAlamat");
        }
    }
}