package com.app.dportshipper.view.homeMenu.ui.pengiriman;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.DialogDataBarangBinding;
import com.app.dportshipper.databinding.DialogSuratWebviewBinding;
import com.app.dportshipper.databinding.FragmentDetailPengirimanBinding;
import com.app.dportshipper.databinding.FragmentDetailPesananBinding;
import com.app.dportshipper.databinding.FragmentPengirimanBinding;
import com.app.dportshipper.model.request.MReqFinalOrder;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.request.ReqFinalPembayaran;
import com.app.dportshipper.model.request.ReqKonfirmasiSelesai;
import com.app.dportshipper.model.response.MSukses;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailPengirimanBarang;
import com.app.dportshipper.model.response.ResDetailPengirimanIsi;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.utils.ToRupiah;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPengirimanFragment extends Fragment {


    private FragmentDetailPengirimanBinding binding;
    private String token;
    private int id_order;

    private List<ResDetailPengirimanBarang> resDetailBarangs;
    private ResDetailPengirimanIsi resDetails;
    private String status_pengiriman;
    private DialogSuratWebviewBinding bindingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPengirimanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        loaddataapi();

        actiononclick();

        return root;
    }

    private void actiononclick() {
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengirimanFragment fragementIntent = new PengirimanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.lyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_order", id_order);
                bundle.putString("jenis", resDetailBarangs.get(0).getJenis_barang() + "d");
                bundle.putInt("berat", resDetailBarangs.get(0).getBobot_barang());
                bundle.putInt("jumlah", resDetailBarangs.get(0).getKuantitas_barang());
                bundle.putString("dimensi", resDetailBarangs.get(0).getPanjang_barang() + "cm x " + resDetailBarangs.get(0).getLebar_barang() + "cm x " + resDetailBarangs.get(0).getTinggi_barang() + "cm");
                bundle.putString("muatan", resDetails.getCapacity() + "");
                bundle.putString("nilai_barang", resDetails.getHarga() + "");
                bundle.putString("catatan", resDetails.getCatatan());
                DetailBarangFragment fragementIntent = new DetailBarangFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                fragementIntent.setArguments(bundle);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.ivDetailBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.lyDetail.performClick();
            }
        });

        binding.tvSuratJalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                bindingDialog = DialogSuratWebviewBinding.inflate(LayoutInflater.from(v.getContext()));
                mAlertDialog.setView(bindingDialog.getRoot());
                mAlertDialog.setCancelable(true);
                mAlertDialog.getWindow().setLayout(700, 600);
                mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mAlertDialog.show();

                bindingDialog.webview.setInitialScale(1);
                bindingDialog.webview.getSettings().setLoadWithOverviewMode(true);
                bindingDialog.webview.getSettings().setUseWideViewPort(true);

                Call<ResponseBody> getCallInvoice = API.service().suratJalan(token, id_order);
                getCallInvoice.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("invoice", response.code() + "");
                        if (response.code() == 200) {
                            ResponseBody resEcontract = response.body();
                            String temp_awal = "<html><body>";
                            String temp_akhir = "</body></html>";
                            String temp = "";
                            try {
                                temp = resEcontract.string();
                                String RESULTDATA = "<html><body>" + temp + "</body></html>";
                                if (!RESULTDATA.equals(null)) {
                                    Log.e("info", RESULTDATA);
                                    bindingDialog.webview.loadData(RESULTDATA, "text/html", "UTF-8");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        binding.tvInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                bindingDialog = DialogSuratWebviewBinding.inflate(LayoutInflater.from(v.getContext()));
                mAlertDialog.setView(bindingDialog.getRoot());
                mAlertDialog.setCancelable(true);
                mAlertDialog.getWindow().setLayout(700, 600);
                mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mAlertDialog.show();

                bindingDialog.webview.setInitialScale(1);
                bindingDialog.webview.getSettings().setLoadWithOverviewMode(true);
                bindingDialog.webview.getSettings().setUseWideViewPort(true);
                bindingDialog.webview.getSettings().setLoadsImagesAutomatically(true);
                bindingDialog.webview.getSettings().setJavaScriptEnabled(true);
                bindingDialog.webview.getSettings().setDomStorageEnabled(true);
                bindingDialog.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

                Call<ResponseBody> getCallInvoice = API.service().invoice(token, id_order);
                getCallInvoice.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("invoice", response.code() + "");
                        if (response.code() == 200) {
                            ResponseBody resEcontract = response.body();
                            String temp_awal = "<html><body>";
                            String temp_akhir = "</body>";
                            String temp_akhir2 = "</html>";
                            String tagDoc = "<!DOCTYPE html>";
                            String tagHtml = "<html lang=\"en\">";
                            String temp = "";
                            try {
                                temp = resEcontract.string();
                                //webview.loadData(temp,"","");
                                temp = temp.replace(tagDoc, "");
                                temp = temp.replace(tagHtml, "");
                                temp = temp.replace(temp_akhir, "");
                                temp = temp.replace(temp_akhir2, "");
                                String RESULTDATA = "<html><body>" + temp + "</body></html>";
                                if (!RESULTDATA.equals(null)) {
                                    Log.e("info", RESULTDATA);

                                    //webview.loadData(RESULTDATA, "text/html; charset=utf-8", null);
                                    //webview.loadData(temp, "text/html", "UTF-8");
                                    bindingDialog.webview.loadData(URLEncoder.encode(RESULTDATA, "UTF-8").replaceAll("\\+", " "), "text/html; charset=utf-8", "utf-8");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        binding.tvEKontrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                bindingDialog = DialogSuratWebviewBinding.inflate(LayoutInflater.from(v.getContext()));
                mAlertDialog.setView(bindingDialog.getRoot());
                mAlertDialog.setCancelable(true);
                mAlertDialog.getWindow().setLayout(700, 600);
                mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mAlertDialog.show();

                bindingDialog.webview.setInitialScale(1);
                bindingDialog.webview.getSettings().setLoadWithOverviewMode(true);
                bindingDialog.webview.getSettings().setUseWideViewPort(true);

                Call<ResponseBody> getCallEcontract = API.service().eContract(token, id_order);
                getCallEcontract.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("e contact", response.code() + "");
                        if (response.code() == 200) {
                            ResponseBody resEcontract = response.body();
                            String temp_awal = "<html><body>";
                            String temp_akhir = "</body></html>";
                            String temp = "";
                            try {
                                temp = resEcontract.string();
                                String RESULTDATA = "<html><body>" + temp + "</body></html>";
                                if (!RESULTDATA.equals(null)) {
                                    Log.e("info", RESULTDATA);
                                    bindingDialog.webview.loadData(RESULTDATA, "text/html", null);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        //username = prefs.getString("username","");
        token = prefs.getString("token", "");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id_order = bundle.getInt("id_order");
            status_pengiriman = bundle.getString("status_pengiriman");
        }
    }

    private void loaddataapi() {
        ReqBursaPengiriman reqBursaPengiriman = new ReqBursaPengiriman();
        reqBursaPengiriman.setId_order(id_order);

        Call<ResDetailPengiriman> getListDetailRingkasan = API.service().detailBursaPengiriman(token, reqBursaPengiriman);
        getListDetailRingkasan.enqueue(new Callback<ResDetailPengiriman>() {
            @Override
            public void onResponse(Call<ResDetailPengiriman> call, Response<ResDetailPengiriman> response) {
                if (response.code() == 200) {

                    ResDetailPengiriman resDetailRingkasan = response.body();
                    String kl = new Gson().toJson(resDetailRingkasan);
                    Log.d("isi data", kl);

                    Glide.with(getActivity())
                            .load(resDetailRingkasan.getDetail().getImage_truck())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivTruk);

                    binding.tvNamaTruk.setText(resDetailRingkasan.getDetail().getNama_transporter());
                    binding.tvPlatNo.setText(resDetailRingkasan.getDetail().getPolice_no());

                    binding.tvAlamatPengirim.setText(resDetailRingkasan.getPengirim().getAlamat());
                    binding.tvAlamatPenerima.setText(resDetailRingkasan.getPenerima().get(0).getAlamat());

                    binding.tvTglPencarianAl.setText(resDetailRingkasan.getDetail().getTgl_pengiriman());

                    binding.tvTarifPengiriman.setText(ToRupiah(resDetailRingkasan.getDetail().getHarga()));
                    binding.tvBiayaPpn.setText(ToRupiah(resDetailRingkasan.getDetail().getPpn_shipper()));
                    binding.tvBiayaInap.setText(ToRupiah(resDetailRingkasan.getDetail().getBiaya_inap()));

                    binding.tvSubTotal.setText(ToRupiah(resDetailRingkasan.getDetail().getTotal_harga()));

                    binding.tvNamaPengirim.setText(resDetailRingkasan.getPengirim().getNama_pengirim());
                    binding.tvNoTlpPengirim.setText(resDetailRingkasan.getPengirim().getNo_hp() + "");

                    binding.tvNamaPenerima.setText(resDetailRingkasan.getPenerima().get(0).getNama_penerima());
                    binding.tvNoTlpPenerima.setText(resDetailRingkasan.getPenerima().get(0).getNo_hp() + "");

                    Glide.with(getActivity())
                            .load(resDetailRingkasan.getDetail().getImage_transporter())
                            .centerCrop()
                            .placeholder(R.drawable.truk_header)
                            .into(binding.ivPhotoDriver);

                    binding.tvNamaDriver.setText(resDetailRingkasan.getDetail().getNama_driver());
                    binding.tvNoTlpDriver.setText(resDetailRingkasan.getDetail().getNohp_driver());

                    //binding.btnStatusPengiriman.setText(status_pengiriman);
                    //cek status
                    cekstatus(resDetailRingkasan.getDetail());

                    resDetailBarangs = resDetailRingkasan.getDetail_barang();
                    resDetails = resDetailRingkasan.getDetail();
                }
            }

            @Override
            public void onFailure(Call<ResDetailPengiriman> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void cekstatus(ResDetailPengirimanIsi detail) {
        if (detail.getStatus() == 10) {
            binding.btnStatusPengiriman.setText("Bayar");
        }
        else if (detail.getStatus() == 11) {
            binding.btnStatusPengiriman.setText("Menunggu PickUp");
            binding.btnStatusPengiriman.setEnabled(false);
            binding.lyFotoBongkarDanBuat.setVisibility(View.VISIBLE);
            binding.tvPayment.setVisibility(View.GONE);
            binding.lyPayment.setVisibility(View.GONE);
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            if (detail.getJml_bayar() == detail.getTotal_harga()) {
                //buttton false
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setEnabled(false);
                //binding.btnPembayaran.setBackgroundColor(getResources().getColor(R.color.background_text_color));
                binding.btnStatusPengiriman.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.background_text_color));
            }
            else if (detail.getJml_bayar() != detail.getTotal_harga()) {
                //button aktif
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.btnStatusPengiriman.getText() == "Bayar") {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Apakah anda yakin\ningin melakukan pembayaran?")
                                    //.setContentText("Barang sudah sampai ?")
                                    .setCancelText("Tidak")
                                    .setConfirmText("Ya")
                                    .showCancelButton(true)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.cancel();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            orderB2c();
                                        }
                                    })
                                    .show();
                        }
                    }
                });

            }
        }
        else if (detail.getStatus() == 12) {
            if (detail.getJml_bayar() == detail.getTotal_harga()) {
                //buttton false
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setEnabled(false);
                //binding.btnPembayaran.setBackgroundColor(getResources().getColor(R.color.background_text_color));
                binding.btnStatusPengiriman.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.background_text_color));
            }
            else if (detail.getJml_bayar() != detail.getTotal_harga()) {
                //button aktif
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Apakah anda yakin\ningin melakukan pembayaran?")
                                //.setContentText("Barang sudah sampai ?")
                                .setCancelText("Tidak")
                                .setConfirmText("Ya")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        orderB2c();
                                    }
                                })
                                .show();
                    }
                });

            }
            binding.btnStatusPengiriman.setText("Dalam Pengiriman");
            binding.btnStatusPengiriman.setEnabled(false);
            binding.lyPayment.setVisibility(View.GONE);
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            binding.cvSuratJalan.setVisibility(View.VISIBLE);
        }
        else if (detail.getStatus() == 13) {
            if (detail.getJml_bayar() == detail.getTotal_harga()) {
                //buttton false
                binding.btnStatusPengiriman.setText("Bayar");
                //binding.btnStatusPengiriman.setEnabled(false);
                //binding.btnPembayaran.setBackgroundColor(getResources().getColor(R.color.background_text_color));
            }
            else if (detail.getJml_bayar() != detail.getTotal_harga()) {
                //button aktif
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Apakah anda yakin\ningin melakukan pembayaran?")
                                //.setContentText("Barang sudah sampai ?")
                                .setCancelText("Tidak")
                                .setConfirmText("Ya")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        orderB2c();
                                    }
                                })
                                .show();
                    }
                });

            }
            binding.lyPayment.setVisibility(View.GONE);
            binding.btnStatusPengiriman.setText("Barang Sampai");
            binding.btnStatusPengiriman.setFocusable(false);
            binding.btnBayarSekarang.setVisibility(View.VISIBLE);
            binding.btnBayarSekarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Apakah anda yakin\nBarang anda sudah sampai?")
                            //.setContentText("Barang sudah sampai ?")
                            .setCancelText("Tidak")
                            .setConfirmText("Ya")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    finalkonfirmasiSelesai();
                                }
                            })
                            .show();

                }
            });
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            binding.cvSuratJalan.setVisibility(View.VISIBLE);
            binding.cvInvoice.setVisibility(View.VISIBLE);
        } else if (detail.getStatus() == 14) {
            if (detail.getJml_bayar() == detail.getTotal_harga()) {
                //buttton false
                binding.btnStatusPengiriman.setText("Bayar");
                //binding.btnStatusPengiriman.setEnabled(false);
                //binding.btnStatusPengiriman.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.background_text_color));
            }
            else if (detail.getJml_bayar() != detail.getTotal_harga()) {
                //button aktif
                binding.btnStatusPengiriman.setText("Bayar");
                binding.btnStatusPengiriman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Apakah anda yakin\ningin melakukan pembayaran?")
                                //.setContentText("Barang sudah sampai ?")
                                .setCancelText("Tidak")
                                .setConfirmText("Ya")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        orderB2c();
                                    }
                                })
                                .show();
                    }
                });

            }
            binding.lyPayment.setVisibility(View.GONE);
            binding.btnStatusPengiriman.setText("Selesai");
            binding.btnBayarSekarang.setVisibility(View.VISIBLE);
            binding.btnBayarSekarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Apakah anda yakin\nBarang anda sudah sampai?")
                            //.setContentText("Barang sudah sampai ?")
                            .setCancelText("Tidak")
                            .setConfirmText("Ya")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    finalkonfirmasiSelesai();
                                }
                            })
                            .show();

                }
            });
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            binding.cvSuratJalan.setVisibility(View.VISIBLE);
            binding.cvInvoice.setVisibility(View.VISIBLE);
        }
        //terkonfirmasi
        else if (detail.getStatus() == 20) {
            binding.btnStatusPengiriman.setText("Terkonfirmasi");
            binding.btnStatusPengiriman.setEnabled(false);
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            binding.lyPayment.setVisibility(View.GONE);
        }
        else if (detail.getStatus() == 22){
            binding.btnStatusPengiriman.setText("Pembayaran Selesai");
            binding.cvEKontrak.setVisibility(View.VISIBLE);
            binding.cvSuratJalan.setVisibility(View.VISIBLE);
            binding.cvInvoice.setVisibility(View.VISIBLE);
        }
    }

    private void orderB2c() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        MReqFinalOrder mReqFinalOrder = new MReqFinalOrder();
        mReqFinalOrder.setId_order(id_order);
        Call<MSukses> sentDataOrder = API.service().orderBayarB2c(token, mReqFinalOrder);
        sentDataOrder.enqueue(new Callback<MSukses>() {
            @Override
            public void onResponse(Call<MSukses> call, Response<MSukses> response) {
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    suksesBayarB2c();
                }
            }

            @Override
            public void onFailure(Call<MSukses> call, Throwable t) {
                pDialog.dismissWithAnimation();
                t.printStackTrace();
            }
        });
    }

    private void suksesBayarB2c() {
        SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        alertDialog.setTitleText("Pembayaran Berhasil, Terimakasih");
        alertDialog.setConfirmText("Ok");
        alertDialog.showCancelButton(false);
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                PengirimanFragment homeFragment = new PengirimanFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, homeFragment);
                ft.commit();

            }
        })
                .show();
    }

    private void konfirmasiSelesai() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqKonfirmasiSelesai reqKonfirmasiSelesai = new ReqKonfirmasiSelesai();
        reqKonfirmasiSelesai.setId_order(id_order);

        Call<ResUtama> getCallKonfirmasi = API.service().konfirmasiSelesai(token, reqKonfirmasiSelesai);
        getCallKonfirmasi.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    suksesKonfirmasi();
                    pDialog.dismissWithAnimation();
                } else if (response.code() == 204) {
                    pDialog.dismissWithAnimation();

                } else {
                    pDialog.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                pDialog.dismissWithAnimation();
                t.printStackTrace();
            }
        });

    }
    private void finalkonfirmasiSelesai() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqFinalPembayaran reqKonfirmasiSelesai = new ReqFinalPembayaran();
        reqKonfirmasiSelesai.setId_order(id_order);
        reqKonfirmasiSelesai.setJumlah_pembayaran(String.valueOf(resDetails.getJml_bayar()));

        Call<ResUtama> getCallKonfirmasi = API.service().finalPembayaran(token, reqKonfirmasiSelesai);
        getCallKonfirmasi.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("BERHASIL")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id_order", id_order);
                                    DetailPengirimanFragment homeFragment = new DetailPengirimanFragment();
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.nav_host_fragment, homeFragment);
                                    homeFragment.setArguments(bundle);
                                    ft.commit();
                                }
                            })
                            .show();
                } else {
                    pDialog.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                pDialog.dismissWithAnimation();
                t.printStackTrace();
            }
        });

    }

    private void suksesKonfirmasi() {
        SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        alertDialog.setTitleText("Konfirmasi barang sampai selesai, Terimakasih!");
        alertDialog.setConfirmText("Ok");
        alertDialog.showCancelButton(false);
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                PengirimanFragment homeFragment = new PengirimanFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, homeFragment);
                ft.commit();
                sweetAlertDialog.dismiss();
            }
        })
                .show();
    }

    public String ToRupiah(long total_harga) {
        String format = String.format(Locale.getDefault(), "%,d", total_harga);
        format = format.replace(",", ".");
        //format = format;
        return format;
    }
}