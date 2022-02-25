package com.app.dportshipper.view.homeMenu.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dportshipper.R;
import com.app.dportshipper.adapter.AdapterAlamat;
import com.app.dportshipper.adapter.AdapterBarang;
import com.app.dportshipper.adapter.AdapterPengirimanFavorite;
import com.app.dportshipper.adapter.AdapterRekomendasiTransporter;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.DialogDataBarangBinding;
import com.app.dportshipper.databinding.FragmentBookingPengirimanBinding;
import com.app.dportshipper.model.MAlamatPenerima;
import com.app.dportshipper.model.MHead;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.MPenerima;
import com.app.dportshipper.model.MPengirim;
import com.app.dportshipper.model.request.MBarang;
import com.app.dportshipper.model.request.MReqKirimDetailPengiriman;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.request.ReqDetailInputBarang;
import com.app.dportshipper.model.response.ResDetailInputBarang;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResOrderPesanan;
import com.app.dportshipper.utils.GridSpacingItemDecoration;
import com.app.dportshipper.view.homeMenu.ui.pengiriman.PengirimanFragment;
import com.app.dportshipper.view.login.LoginActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingPengirimanFragment extends Fragment {

    private FragmentBookingPengirimanBinding binding;
    private String token;
    private int id_produk_transporter;
    private String tanggal;
    private String type_send;
    private int type_service;
    ArrayList<MListDataBarang> listDataBarang;
    ArrayList<MAlamatPenerima> listDataAlamat;
    private String kab_asal;
    private String kab_tujuan;
    private DialogDataBarangBinding bindingDialog;
    private AdapterBarang adapterBarang;
    private AdapterAlamat adapterAlamat;
    private ResDetailInputBarang resDetailPengiriman;
    private int id_transporter;
    private int nilai_input;
    private String alamat;
    private String nama_penerima;
    private String no_tlp;
    private String provisi;
    private String kabupaten;
    private String kecamatan;
    private String kelurahan;
    private int kodepos;

    private boolean loadalamat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookingPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loadapidetail();

        onactionbutton();
        return root;
    }

    private void onactionbutton() {
        binding.ivAddBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
                bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
                DetailBarangFragment fragementIntent = new DetailBarangFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.tvAlamatPengirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
                bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
                DetailGantiAlamatFragment fragementIntent = new DetailGantiAlamatFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.lyAlamatPengirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAlamatPengirim.performLongClick();
            }
        });
        binding.rvAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //binding.ivAddAlamat.performClick();
            }
        });
        binding.ivAddAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
                bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
                DetailAlamatFragment fragementIntent = new DetailAlamatFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPengirimanNewFragment inbound = new DetailPengirimanNewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                ft.commit();
            }
        });
        binding.tvDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(listDataBarang!=null){
//                    //convert array to string
//                    String jk = new Gson().toJson(listDataBarang);
//
//                    AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
//                    bindingDialog = DialogDataBarangBinding.inflate(LayoutInflater.from(v.getContext()));
//                    mAlertDialog.setView(bindingDialog.getRoot());
//                    mAlertDialog.setCancelable(false);
//                    mAlertDialog.getWindow().setLayout(700, 600);
//                    mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    mAlertDialog.show();
//
//                    //dataPencarianArrayList = new ArrayList<>();
//                    adapterBarang = new AdapterBarang(listDataBarang);
//                    bindingDialog.rvView.setAdapter(new AlphaInAnimationAdapter(adapterBarang));
//                    bindingDialog.rvView.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
//                    bindingDialog.rvView.addItemDecoration(new GridSpacingItemDecoration(2, 2,true,2));
//                    LinearLayoutManager recyclerManager = ((LinearLayoutManager)bindingDialog.rvView.getLayoutManager());
//
//                    AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (adapterBarang);
//                    alphaInAnimationAdapter.setDuration(1000);
//                    alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
//                    alphaInAnimationAdapter.setFirstOnly(false);
//
//                    bindingDialog.btnOke.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            mAlertDialog.dismiss();
//                        }
//                    });
//                }
            }
        });
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
        binding.etNilaiInput.addTextChangedListener(textWatcher);
        binding.ivAddPengirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("listDataBarang", (ArrayList<? extends Parcelable>) listDataBarang);
                bundle.putParcelableArrayList("listDataAlamat", (ArrayList<? extends Parcelable>) listDataAlamat);
                InfoPengirimFragment fragementIntent = new InfoPengirimFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String stringText = s.toString().replace(".", "");
            if (!stringText.isEmpty()) {
                nilai_input = Integer.parseInt(stringText);
                Log.d("format", nilai_input + "");
                String resultRupiah = String.format(Locale.US, "%,d", nilai_input).replace(',', '.');
                binding.etNilaiInput.removeTextChangedListener(textWatcher);
                binding.etNilaiInput.setText(resultRupiah);
                int pos = binding.etNilaiInput.getText().length();
                binding.etNilaiInput.setSelection(pos);
                binding.etNilaiInput.addTextChangedListener(textWatcher);
            }
        }
    };

    private void validasi() {
        String getNamaPenerima = nama_penerima;
        String getKontakPengirim = no_tlp;
        String getInputHarga = String.valueOf(nilai_input);
        if (getNamaPenerima.equals("") || getNamaPenerima.length() == 0) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Silahkan masukan nama pengirim terlebih dahulu")
                    .show();
        } else if (getKontakPengirim.equals("") || getKontakPengirim.length() == 0) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Silahkan masukan kontak pengirim terlebih dahulu")
                    .show();
        } else if (getInputHarga.equals("") || getInputHarga.length() == 0) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Silahkan masukan input harga terlebih dahulu")
                    .show();
        } else if (listDataBarang == null) {
            //Toast.makeText(getActivity(), "Anda Belum input barang", Toast.LENGTH_SHORT).show();
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Silahkan masukan barang terlebih dahulu")
                    .show();
        } else if (listDataAlamat == null) {
            //Toast.makeText(getActivity(), "Anda Belum input alamat", Toast.LENGTH_SHORT).show();
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Silahkan masukan alamat penerima terlebih dahulu")
                    .show();
        } else {
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();

            MHead mHead = new MHead();
            mHead.setDate_from(resDetailPengiriman.getTanggal());
            mHead.setDate_to(resDetailPengiriman.getTanggal_to());
            mHead.setEstimasi(resDetailPengiriman.getDetail().getEst());
            mHead.setId_pajak(0);
            mHead.setId_produk_transporter(id_produk_transporter);
            mHead.setId_shipper(0);
            mHead.setId_top(1);
            mHead.setId_transporter(id_transporter);
            mHead.setKeterangan("");
            mHead.setTipe_order(1);
            mHead.setTipe_schedule(type_service);
            mHead.setHarga(resDetailPengiriman.getDetail().getHarga());
            mHead.setTotal_harga(resDetailPengiriman.getTotal_harga());
            mHead.setLayanan_shipper(Integer.valueOf(resDetailPengiriman.getBiaya_layanan_shipper()));
            mHead.setLayanan_transporter(Integer.valueOf(resDetailPengiriman.getBiaya_layanan_transporter()));
            mHead.setPpn_shipper(Integer.valueOf(resDetailPengiriman.getBiaya_ppn()));
            mHead.setNilai_barang(nilai_input);

            MPengirim mPengirim = new MPengirim();
            mPengirim.setId_shipper(0);
            mPengirim.setKtp_pengirim(0);
            mPengirim.setNama_pengirim(String.valueOf(nama_penerima));
            mPengirim.setNo_hp(String.valueOf(no_tlp));
            mPengirim.setAlamat(String.valueOf(alamat));
            mPengirim.setProv(provisi);
            mPengirim.setKab(kabupaten);
            mPengirim.setKec(kecamatan);
            mPengirim.setKel(kelurahan);
            mPengirim.setKode_pos(kodepos);

            List<MPenerima> penerimaList = new ArrayList<>();

            for (int i = 0; i < listDataAlamat.size(); i++) {
                MPenerima mPenerima = new MPenerima();
                mPenerima.setAlamat(listDataAlamat.get(i).getAlamat());
                mPenerima.setNama_penerima(listDataAlamat.get(i).getNamaPenerima());
                mPenerima.setKtp_penerima("");
                mPenerima.setNo_hp(listDataAlamat.get(i).getKontakTlp());
                mPenerima.setProv(listDataAlamat.get(i).getProvinsi());
                mPenerima.setKab(listDataAlamat.get(i).getKabupaten());
                mPenerima.setKec(listDataAlamat.get(i).getKecamatan());
                mPenerima.setKel(listDataAlamat.get(i).getKelurahan());
                mPenerima.setKode_pos(listDataAlamat.get(i).getKodepos());
                penerimaList.add(mPenerima);
            }

            List<MBarang> barangList = new ArrayList<>();

            for (int i = 0; i < listDataBarang.size(); i++) {
                MBarang mBarang = new MBarang();
                mBarang.setKuantitas_barang(String.valueOf(listDataBarang.get(i).getKuantitas_barang()));
                mBarang.setJenis_barang(listDataBarang.get(i).getNama_barang());
                mBarang.setBobot_barang(String.valueOf(listDataBarang.get(i).getBobot_barang()));
                mBarang.setPanjang_barang(String.valueOf(listDataBarang.get(i).getPanjang_barang()));
                mBarang.setLebar_barang(String.valueOf(listDataBarang.get(i).getLebar_barang()));
                mBarang.setTinggi_barang(String.valueOf(listDataBarang.get(i).getTinggi_barang()));
                mBarang.setId_kategori_barang(String.valueOf(listDataBarang.get(i).getId_kategori_barang()));
                mBarang.setVolumeM2(0);
                barangList.add(mBarang);
            }

            MReqKirimDetailPengiriman mReqKirimDetailPengiriman = new MReqKirimDetailPengiriman();
            mReqKirimDetailPengiriman.setHead(mHead);
            mReqKirimDetailPengiriman.setPengirim(mPengirim);
            mReqKirimDetailPengiriman.setPenerima(penerimaList);
            mReqKirimDetailPengiriman.setDetail_barang(barangList);

            String jk = new Gson().toJson(mReqKirimDetailPengiriman);
            Log.d("sidik Kirim ORder", jk);
            Call<ResOrderPesanan> sentOrderPesanan = API.service().kirimDataOrder(token, mReqKirimDetailPengiriman);
            sentOrderPesanan.enqueue(new Callback<ResOrderPesanan>() {
                @Override
                public void onResponse(Call<ResOrderPesanan> call, Response<ResOrderPesanan> response) {
                    pDialog.dismissWithAnimation();
                    if (response.isSuccessful()) {
                        pDialog.dismissWithAnimation();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("CREATE ORDER BERHASIL")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
                                        navBar.setVisibility(View.VISIBLE);

                                        SharedPreferences preferences = getActivity().getSharedPreferences("data_transporter", 0);
                                        preferences.edit().clear().apply();

                                        PengirimanFragment paramPengiriman = new PengirimanFragment();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransactionKorlap = fragmentManager.beginTransaction();
                                        fragmentTransactionKorlap.replace(R.id.nav_host_fragment, paramPengiriman).addToBackStack(null);
                                        fragmentTransactionKorlap.commit();
                                    }
                                })
                                .show();
                    } else {
                        pDialog.dismissWithAnimation();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("ERROR")
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResOrderPesanan> call, Throwable t) {
                    pDialog.dismissWithAnimation();
                    t.printStackTrace();
                }
            });
        }
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        SharedPreferences prefs_data_transporter = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE);
        kab_asal = prefs_data_transporter.getString("kab_asal", "");
        kab_tujuan = prefs_data_transporter.getString("kab_tujuan", "");
        type_send = prefs_data_transporter.getString("type_send", "");
        type_service = prefs_data_transporter.getInt("type_service", 0);
        tanggal = prefs_data_transporter.getString("tanggal", "");
        id_produk_transporter = prefs_data_transporter.getInt("id_produk_transporter", 0);
        id_transporter = prefs_data_transporter.getInt("id_transporter", 0);

        //baru
        loadalamat = prefs_data_transporter.getBoolean("loadalamat", false);


        alamat = prefs_data_transporter.getString("alamat", "");
        nama_penerima = prefs_data_transporter.getString("nama_penerima", "");
        no_tlp = prefs_data_transporter.getString("no_tlp", "");
        provisi = prefs_data_transporter.getString("provisi", "");
        kabupaten = prefs_data_transporter.getString("kabupaten", "");
        kecamatan = prefs_data_transporter.getString("kecamatan", "");
        kelurahan = prefs_data_transporter.getString("kelurahan", "");
        kodepos = prefs_data_transporter.getInt("kodepos", 0);


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            listDataBarang = bundle.getParcelableArrayList("listDataBarang");
            if (listDataBarang != null) {
                binding.tvDataBarang.setText("Data Barang");
            }
            listDataAlamat = bundle.getParcelableArrayList("listDataAlamat");
            if (listDataAlamat != null) {
                binding.tvDataAlamat.setText("Data Alamat");

                adapterAlamat = new AdapterAlamat(listDataAlamat);
                binding.rvAlamat.setAdapter(new AlphaInAnimationAdapter(adapterAlamat));
                binding.rvAlamat.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
                binding.rvAlamat.addItemDecoration(new GridSpacingItemDecoration(2, 2, true, 2));
                LinearLayoutManager recyclerManager = ((LinearLayoutManager) binding.rvAlamat.getLayoutManager());

                AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapterAlamat);
                alphaInAnimationAdapter.setDuration(1000);
                alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
                alphaInAnimationAdapter.setFirstOnly(false);
            }

            if (nama_penerima != null || no_tlp != null) {
                binding.etInfoPengirim.setText(nama_penerima + "\n" + no_tlp);
            }
//            alamat = bundle.getString("alamat");
//            String nama_penerima = bundle.getString("nama_penerima");
//            String no_tlp = bundle.getString("no_tlp");


//            kab_asal = bundle.getString("kab_asal");
//            kab_tujuan = bundle.getString("kab_tujuan");
//            type_send = bundle.getString("type_send");
//            type_service = bundle.getInt("type_service");
//            tanggal = bundle.getString("tanggal");
//            id_produk_transporter = bundle.getInt("id_produk_transporter");
        }
    }

    private void loadapidetail() {
        ReqDetailInputBarang reqDetailInputBarang = new ReqDetailInputBarang();
        reqDetailInputBarang.setType_send(type_send);
        reqDetailInputBarang.setType_service(type_service);
        reqDetailInputBarang.setTanggal(tanggal);
        reqDetailInputBarang.setId_produk_transporter(id_produk_transporter);

        String jk = new Gson().toJson(reqDetailInputBarang);
        Log.d("Agus PARAM", jk);
        Call<ResDetailInputBarang> callDetailInputBarang = API.service().detailInputBarang(token, reqDetailInputBarang);
        callDetailInputBarang.enqueue(new Callback<ResDetailInputBarang>() {
            @Override
            public void onResponse(Call<ResDetailInputBarang> call, Response<ResDetailInputBarang> response) {
                if (response.code() == 200) {
                    resDetailPengiriman = response.body();

                    String jk = new Gson().toJson(resDetailPengiriman);
                    Log.d("Agus ISI", jk);

                    Glide.with(getActivity())
                            .load(resDetailPengiriman.getDetail().getPic_file_path())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivTruk);

                    binding.tvNamaTruk.setText(resDetailPengiriman.getDetail().getNama_transporter());
                    binding.tvPlatNo.setText(resDetailPengiriman.getDetail().getPolice_no() + "");

                    if (loadalamat == false) {
                        binding.tvAlamatPengirim.setText(resDetailPengiriman.getAlamat().getAlamat());

                        SharedPreferences.Editor editor = getActivity().getBaseContext().getSharedPreferences("data_transporter", Context.MODE_PRIVATE).edit();
                        editor.putString("alamat", resDetailPengiriman.getAlamat().getAlamat());
                        editor.putString("provisi", resDetailPengiriman.getAlamat().getProv());
                        editor.putString("kabupaten", resDetailPengiriman.getAlamat().getKab());
                        editor.putString("kecamatan", resDetailPengiriman.getAlamat().getKec());
                        editor.putString("kelurahan", resDetailPengiriman.getAlamat().getKel());
                        editor.putInt("kodepos", resDetailPengiriman.getAlamat().getKode_pos());
                        editor.apply();
                    } else if (loadalamat == true) {
                        binding.tvAlamatPengirim.setText(alamat);
                    }

//                    if(alamat!=null)
//                    {
//
//                    }
//                    else{
//
//                    }

                    binding.etTglPencarianAl.setText(tanggal);

                    //detail armada
                    binding.tvTipeKendaraan.setText(resDetailPengiriman.getDetail().getJenis_kendaraan());
                    binding.tvMerek.setText(resDetailPengiriman.getDetail().getBrand());
                    binding.tvNoKir.setText("");
                    binding.tvDimensi.setText(resDetailPengiriman.getDetail().getHeigth() + " cm x " + resDetailPengiriman.getDetail().getWidth() + " cm x " + resDetailPengiriman.getDetail().getLength() + " cm");
                    binding.tvMuatan.setText(resDetailPengiriman.getDetail().getCapacity() + " Kg");

                    //tarif pengiriman
                    binding.tvBiayaPengiriman.setText("Rp. " + ToRupiah(resDetailPengiriman.getDetail().getHarga()));
                    binding.tvBiayaPpn.setText("Rp. " + ToRupiah(Integer.valueOf(resDetailPengiriman.getBiaya_ppn())));
                    binding.tvSubTotal.setText("Rp. " + ToRupiah(resDetailPengiriman.getTotal_harga()));

                } else if (response.code() == 202) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Anda belum Login?")
                            .setContentText("Silahkan login terlebih dahulu")
                            .setConfirmText("Login")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    startActivity(new Intent(getActivity(), LoginActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    getActivity().finish();
                                }
                            })
                            .show();

                }
            }

            @Override
            public void onFailure(Call<ResDetailInputBarang> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public String ToRupiah(long total_harga) {
        String format = String.format(Locale.getDefault(), "%,d", total_harga);
        format = format.replace(",", ".");
        //format = format;
        return format;
    }
}