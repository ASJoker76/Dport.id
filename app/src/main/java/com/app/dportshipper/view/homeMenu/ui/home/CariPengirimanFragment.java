package com.app.dportshipper.view.homeMenu.ui.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentCariPengirimanBinding;
import com.app.dportshipper.model.request.ReqPencarianAuto;
import com.app.dportshipper.model.response.ResPencarianAuto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CariPengirimanFragment extends Fragment {


    private FragmentCariPengirimanBinding binding;
    private int ftl;
    private int service;
    private ArrayAdapter<String> adapterPencarian;
    List<String> dataPencarian = new ArrayList<>();
    private List<ResPencarianAuto> listData = new ArrayList<>();
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCariPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        onloadsession();
        data();
        datepiker();
        onClick();

        return root;
    }

    private void onClick() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                navBar.setVisibility(View.VISIBLE);

                BerandaFragment inbound = new BerandaFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                ft.commit();
            }
        });
        binding.btnCariHomePengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekValidasiPencarian();
            }
        });

        binding.ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etTglPencarianAl.performClick();
            }
        });
    }

    private void cekValidasiPencarian() {

        String getAsal = binding.etAsalPencarianAl.getText().toString().toLowerCase();
        String getTujuan = binding.etTujuanPencarianAl.getText().toString().toLowerCase();
        String getTanggal = binding.etTglPencarianAl.getText().toString();


        // Check if all strings are null or not
        if (getAsal.equals("") || getAsal.length() == 0){
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Asal daerah pengiriman")
                    .show();
        }else if (getTujuan.equals("") || getTujuan.length() == 0){
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Tujuan daerah pengiriman")
                    .show();
        }else if (getTanggal.equals("") || getTanggal.length() == 0){
            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Mohon isi Tanggal pengiriman")
                    .show();
        }else {
            /*if (getAsal.equals("Kampar") && getTujuan.equals("Jambi") && getTanggal.equals("2021-01-21")) {
                Toast.makeText(getContext(), Host.getHost(), Toast.LENGTH_LONG).show();
                return;
            }*/
            pencarianAsalTujuan("reza");
        }
    }
    private void pencarianAsalTujuan(String reza) {
        if(binding.etFtlPencarian.getSelectedItem().toString().equals("FTL (Full Truck Load)")){
            ftl = 1;
        }
        if(binding.etTypePencarian.getSelectedItem().toString().equals("Reguler")){
            service = 1;
        }
        else if(binding.etTypePencarian.getSelectedItem().toString().equals("Terjadwal")){
            service = 2;
        }
        HasilPencarianFragment paramPengiriman = new HasilPencarianFragment();
        Bundle args = new Bundle();
        args.putString("kab_asal", String.valueOf(binding.etAsalPencarianAl.getText().toString()));
        args.putString("kab_tujuan", String.valueOf(binding.etTujuanPencarianAl.getText().toString()));
        args.putString("type_send", String.valueOf(ftl));
        args.putInt("type_service", service);
        args.putString("tanggal", String.valueOf(binding.etTglPencarianAl.getText().toString()));
        paramPengiriman.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransactionKorlap = fragmentManager.beginTransaction();
        fragmentTransactionKorlap.replace(R.id.nav_host_fragment, paramPengiriman).addToBackStack(null);
        fragmentTransactionKorlap.commit();
    }

    private void onloadsession() {

        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

    }

    private void datepiker() {
        /* Date Picker */
        final Calendar hCalender = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                hCalender.set(Calendar.YEAR, year);
                hCalender.set(Calendar.MONTH, month);
                hCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                binding.etTglPencarianAl.setText(sdf.format(hCalender.getTime()));
            }
        };

        binding.etTglPencarianAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dp, hCalender.get(Calendar.YEAR),
                        hCalender.get(Calendar.MONTH), hCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void data() {
        adapterPencarian= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataPencarian);

        onLoad("");
        binding.etAsalPencarianAl.setAdapter(adapterPencarian);
        binding.etTujuanPencarianAl.setAdapter(adapterPencarian);
    }

    private void onLoad(String s) {
        ReqPencarianAuto reqPencarianAuto = new ReqPencarianAuto();

        reqPencarianAuto.setParam(s.toString());

        Call<List<ResPencarianAuto>> getListAsal = API.service().sAutoRequest(token, reqPencarianAuto);
        getListAsal.enqueue(new Callback<List<ResPencarianAuto>>() {
            @Override
            public void onResponse(Call<List<ResPencarianAuto>> call, Response<List<ResPencarianAuto>> response) {
                listData.clear();
                dataPencarian.clear();
                adapterPencarian.notifyDataSetChanged();
                listData = response.body();
                for (ResPencarianAuto tipePengiriman : listData) {
                    dataPencarian.add(tipePengiriman.getKab_kota());
                }
                adapterPencarian.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ResPencarianAuto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}