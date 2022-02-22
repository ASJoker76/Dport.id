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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private List<ResPencarianAuto> listData = new ArrayList<>();
    List<String> dataPencarian = new ArrayList<>();
    private ArrayAdapter<String> adapterPencarian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBerandaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        inisiasi();
        loadtable();
        loadtable2();
        loadspinner();
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
            loadapirekomendasitransporter();

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            // onPostExecute akan dieksekusi setelah doInBackground selesai dieksekusi
            loadapiautocomplite();
            loadapipengirimanfavorite();
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadapipengirimanfavorite() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        ReqPencarian reqPencarian = new ReqPencarian();
        reqPencarian.setKab_asal("Kota Jakarta Timur");
        reqPencarian.setKab_tujuan("Kota Pekanbaru");
        reqPencarian.setTanggal(dtf.format(now));
        reqPencarian.setType_service(1);
        reqPencarian.setType_send("Reguler");

        /* Set Res Pencarian */
        Call<ResPencarian> callPencarian = API.service().PencarianShipperPage(token, reqPencarian);
        callPencarian.enqueue(new Callback<ResPencarian>() {
            @Override
            public void onResponse(Call<ResPencarian> call, Response<ResPencarian> response) {
                Log.d("reza Pencarian", response.code() + "");

                if (response.code() == 200) {
                    ResPencarian resPencarian = response.body();
                    dataPencarianArrayList.addAll(resPencarian.getData());
                    adapterPengirimanFavorite.notifyDataSetChanged();
                } else if (response.code() == 204) {

                }
            }
            @Override
            public void onFailure(Call<ResPencarian> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setManual() {
        String kota_asal = "Kota Jakarta Timur";
        if (kota_asal != null) {
            int spinnerPosition = adapterPencarian.getPosition(kota_asal);
            binding.etAsalPencarianAl.setSelection(spinnerPosition);
        }
        String kota_tujuan = "Kota Pekanbaru";
        if (kota_tujuan != null) {
            int spinnerPosition = adapterPencarian.getPosition(kota_tujuan);
            binding.etTujuanPencarianAl.setSelection(spinnerPosition);
        }
    }

    private void loadspinner() {
        adapterPencarian= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dataPencarian);
        binding.etAsalPencarianAl.setAdapter(adapterPencarian);
        binding.etTujuanPencarianAl.setAdapter(adapterPencarian);
    }

    private void loadapiautocomplite() {
        ReqPencarianAuto reqPencarianAuto = new ReqPencarianAuto();

        reqPencarianAuto.setParam("");

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
        adapterRekomendasiTransporter = new AdapterRekomendasiTransporter(this, allResBursaPengirimanArrayList);
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
        adapterPengirimanFavorite = new AdapterPengirimanFavorite(this, dataPencarianArrayList);
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
