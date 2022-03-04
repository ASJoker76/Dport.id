package com.app.dportshipper.view.homeMenu.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterPengirimanFavorite;
import com.app.dportshipper.adapter.AdapterRekomendasiTransporter;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentBerandaBinding;
import com.app.dportshipper.model.DataPencarian;
import com.app.dportshipper.model.MLimit;
import com.app.dportshipper.model.request.ReqPencarian;
import com.app.dportshipper.model.request.ReqPencarianAuto;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.model.response.ResPencarian;
import com.app.dportshipper.model.response.ResPencarianAuto;
import com.app.dportshipper.utils.GridSpacingItemDecoration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaFragment extends Fragment {


    private FragmentBerandaBinding binding;
    private AdapterRekomendasiTransporter adapterRekomendasiTransporter;
    private AdapterPengirimanFavorite adapterPengirimanFavorite;
    private ArrayList<MResListPengiriman> allResBursaPengirimanArrayList;
    private ArrayList<DataPencarian> dataPencarianArrayList;
    private String token;

//    private BerandaViewModel model;
    private ArrayAdapter<String> adapterPencarian;
    List<String> dataPencarian = new ArrayList<>();
    private List<ResPencarianAuto> listData = new ArrayList<>();
    private String tanggal;
    private int type_service;
    private String type_send;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBerandaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        inisiasi();
        loadtable();
        loadtable2();
        asynTask task = new asynTask();
        task.execute();
        setManual();
        onclick();
//        // Get the ViewModel.
//        model = new ViewModelProvider(this).get(BerandaViewModel.class);
//
//        // Create the observer which updates the UI.
//        final Observer<List<MResListPengiriman>> nameObserver = new Observer<List<MResListPengiriman>>() {
//            @Override
//            public void onChanged(@Nullable final List<MResListPengiriman> newName) {
//                // Update the UI, in this case, a TextView.
//                allResBursaPengirimanArrayList.addAll(newName);
//                adapterRekomendasiTransporter.notifyDataSetChanged();
//            }
//        };
//
//        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
//        model.getCurrentName().observe(getActivity(), nameObserver);

        return root;
    }

    private class asynTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // doInBackground() adalah tempat kita melakukan proses di thread lain
//            loadapirekomendasitransporter();
//            loadapiautocomplite();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            // onPostExecute akan dieksekusi setelah doInBackground selesai dieksekusi
            loadapiautocomplite();
//            loadapipengirimanfavorite("Bandung","Kepulauan Seribu");
        }
    }

    private void onclick() {
        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                CariPengirimanFragment inbound = new CariPengirimanFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                ft.commit();
            }
        });

        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etSearch.performClick();
            }
        });

        binding.ivSearchRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadapipengirimanfavorite(binding.etAsalPencarianAl.getText().toString(),binding.etTujuanPencarianAl.getText().toString());
            }
        });
    }

    private void loadapipengirimanfavorite(String asal, String tujuan) {
        ReqPencarian reqPencarian = new ReqPencarian();
        reqPencarian.setKab_asal(asal);
        reqPencarian.setKab_tujuan(tujuan);
        reqPencarian.setTanggal(tanggal);
        reqPencarian.setType_service(type_service);
        reqPencarian.setType_send(type_send);

        /* Set Res Pencarian */
        Call<ResPencarian> callPencarian = API.service().PencarianShipperPage(token, reqPencarian);
        callPencarian.enqueue(new Callback<ResPencarian>() {
            @Override
            public void onResponse(Call<ResPencarian> call, Response<ResPencarian> response) {
                Log.d("reza Pencarian", response.code() + "");

                if (response.code() == 200) {
                    ResPencarian resPencarian = response.body();
                    dataPencarianArrayList.clear();
                    dataPencarianArrayList.addAll(resPencarian.getData());
                    adapterPengirimanFavorite.notifyDataSetChanged();
                } else if (response.code() == 204) {
                    dataPencarianArrayList.clear();
                    adapterPengirimanFavorite.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ResPencarian> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setManual() {
        binding.etAsalPencarianAl.setText("Bandung");
        binding.etTujuanPencarianAl.setText("Kepulauan Seribu");
//        String kota_asal = "Kota Jakarta Timur";
//        if (kota_asal != null) {
//            int spinnerPosition = adapterPencarian.getPosition(kota_asal);
//            binding.etAsalPencarianAl.setSelection(spinnerPosition);
//        }
//        String kota_tujuan = "Kota Pekanbaru";
//        if (kota_tujuan != null) {
//            int spinnerPosition = adapterPencarian.getPosition(kota_tujuan);
//            binding.etTujuanPencarianAl.setSelection(spinnerPosition);
//        }
    }

    private void loadapiautocomplite() {
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


    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token   = prefs.getString("token","");

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDateTime now = LocalDateTime.now();

        //tanggal = dtf.format(now);
        //tanggal = "23/02/2022";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        tanggal = dateFormat.format(calendar.getTime());

//        or
        //SimpleDateFormat curFormater = new SimpleDateFormat("yyyy/MM/dd");
//        Date c = Calendar.getInstance().getTime();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//        String formattedDate = df.format(c);
//        tanggal = formattedDate;

        type_service = 1;
        type_send = "Reguler";
    }

    private void loadapirekomendasitransporter() {
        MLimit mLimit = new MLimit();
        mLimit.setStatus(18);
        mLimit.setLimit(10);
        mLimit.setOffset(0);
        Call<List<MResListPengiriman>> callListDataPengiriman = API.service().getDataListPengiriman(token,mLimit);
        callListDataPengiriman.enqueue(new Callback<List<MResListPengiriman>>() {
            @Override
            public void onResponse(Call<List<MResListPengiriman>> call, Response<List<MResListPengiriman>> response) {
                Log.d("sidik callPengiriman",response.code()+"");
                if (response.code()==200){
                    List<MResListPengiriman> resListPengirimen = response.body();
                    allResBursaPengirimanArrayList.addAll(resListPengirimen);
                    adapterRekomendasiTransporter.notifyDataSetChanged();
                }
                else if(response.code()==204){

                }

            }


            @Override
            public void onFailure(Call<List<MResListPengiriman>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadtable() {
        allResBursaPengirimanArrayList = new ArrayList<>();
        adapterRekomendasiTransporter = new AdapterRekomendasiTransporter(this, allResBursaPengirimanArrayList,type_send,type_service,tanggal);
        binding.rvRekomendasiTransporter.setAdapter(new AlphaInAnimationAdapter(adapterRekomendasiTransporter));
        binding.rvRekomendasiTransporter.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvRekomendasiTransporter.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvRekomendasiTransporter.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterRekomendasiTransporter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void loadtable2() {
        dataPencarianArrayList = new ArrayList<>();
        adapterPengirimanFavorite = new AdapterPengirimanFavorite(this, dataPencarianArrayList,type_send,type_service,tanggal);
        binding.rvPengirimanFavorite.setAdapter(new AlphaInAnimationAdapter(adapterPengirimanFavorite));
        binding.rvPengirimanFavorite.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        binding.rvPengirimanFavorite.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvPengirimanFavorite.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterPengirimanFavorite);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    private void inisiasi() {
        //kecilin ukuran drawble search
        Drawable dr = getResources().getDrawable(R.drawable.search);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 15, 15, true));

        //setCompoundDrawablesWithIntrinsicBounds (image to left, top, right, bottom)
        binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
    }
}
