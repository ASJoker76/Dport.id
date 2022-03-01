package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentTambahAlamatBinding;
import com.app.dportshipper.databinding.FragmentUpdateAlamatBinding;
import com.app.dportshipper.model.request.MReqProv;
import com.app.dportshipper.model.request.ReqUpdateAlamatProfil;
import com.app.dportshipper.model.response.MResKab;
import com.app.dportshipper.model.response.MResKec;
import com.app.dportshipper.model.response.MResKel;
import com.app.dportshipper.model.response.MResProv;
import com.app.dportshipper.model.response.ResUtama;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateAlamatFragment extends Fragment {

    private FragmentUpdateAlamatBinding binding;

    String  alamat, prov, kota, kec, kel, kd_alamat;
    int id_alamat_shipper, kodePos, flag;

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
        binding = FragmentUpdateAlamatBinding.inflate(inflater, container, false);
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
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaftarAlamatSayaFragment fragementIntent = new DaftarAlamatSayaFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void validasi() {
        String getAlamat = binding.etAlamat.getText().toString();

        // Check if all strings are null or not
        if (getAlamat.equals("") || getAlamat.length() == 0){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi alamat lengkap penerima")
                    .show();
        }

        else {
            onloadProcess(getAlamat);
        }
    }

    private void onloadProcess(String getAlamat) {
        ReqUpdateAlamatProfil reqUpdateAlamatProfil = new ReqUpdateAlamatProfil();
        reqUpdateAlamatProfil.setAlamat(getAlamat);
        reqUpdateAlamatProfil.setProv(valuePro);
        reqUpdateAlamatProfil.setKec(valueKec);
        reqUpdateAlamatProfil.setKab(valueKab);
        reqUpdateAlamatProfil.setKel(valueKel);
        reqUpdateAlamatProfil.setKode_pos(valueKodepos);
        reqUpdateAlamatProfil.setFlag(1);


        Call<ResUtama> callUpdateAlamat = API.service().updateAlamatProfil(token, reqUpdateAlamatProfil, id_alamat_shipper);
        callUpdateAlamat.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Update Alamat Berhasil")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                pindahKeAlamatSaya();
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void pindahKeAlamatSaya() {
        DaftarAlamatSayaFragment fragementIntent = new DaftarAlamatSayaFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragementIntent);
        transaction.addToBackStack(null);
        transaction.commit();
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
                    if (prov != null) {
                        int spinnerPosition = adapterProv.getPosition(prov);
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
                    if (kota != null) {
                        int spinnerPosition = adapterKab.getPosition(kota);
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
                    if (kec != null) {
                        int spinnerPosition = adapterKec.getPosition(kec);
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
                    if (kel != null) {
                        int spinnerPosition = adapterKel.getPosition(kel);
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
            id_alamat_shipper = bundle.getInt("id_alamat_shipper");
            alamat = bundle.getString("alamat");
            prov = bundle.getString("prov");
            kota = bundle.getString("kota");
            kec = bundle.getString("kec");
            kel = bundle.getString("kel");
            kodePos = bundle.getInt("kodepos");
            flag = bundle.getInt("flag");

            binding.etAlamat.setText(alamat+"");
        }
    }
}