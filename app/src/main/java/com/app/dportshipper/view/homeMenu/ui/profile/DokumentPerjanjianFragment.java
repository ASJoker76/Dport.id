package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDetailGantiAlamatBinding;
import com.app.dportshipper.databinding.FragmentDokumentPerjanjianBinding;
import com.app.dportshipper.model.request.ReqDocumentProfile;
import com.app.dportshipper.model.response.ResGetProfileFull;
import com.app.dportshipper.model.response.ResUtama;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DokumentPerjanjianFragment extends Fragment {


    private FragmentDokumentPerjanjianBinding binding;
    int param = 0;
    int id_shipper;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDokumentPerjanjianBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        cekdata();
        onclick();

        return root;
    }

    private void onclick() {
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment inbound = new ProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                ft.commit();
            }
        });

        binding.ivDocKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "ktp");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocNpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "npwp");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocSiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "siup");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocSiujpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "siujpt");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocSkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "skt");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocTdpNib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "tdp");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocAktaPendirian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "aktapendirian");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocAktaAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "aktaakhir");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocSppkp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "sppkp");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.ivDocSuratDomisili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("status", "domisili");
                EditFotoDokumenFragment inbound = new EditFotoDokumenFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, inbound);
                inbound.setArguments(bundle);
                ft.commit();
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (param == 2) {
                    /* personal */
                    checkvalidation();
                } else if (param == 1) {
                    /* perusahaan */
                    checkvalidation2();
                }
            }
        });
    }

    private void checkvalidation2() {
        String getNoKtp = binding.etDocNoKtp.toString();
        String getoNpwp = binding.etNpwp.getText().toString();

        if (getNoKtp.equals("") || getNoKtp.length() == 0
                || getoNpwp.equals("") || getoNpwp.length() == 0
        ) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Data Belum Dilengkapi")
                    .show();
        } else {
            process2(String.valueOf(param));
        }
    }

    private void process2(String param) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqDocumentProfile reqDocumentProfile = new ReqDocumentProfile();
        reqDocumentProfile.setParam(String.valueOf(param));
        reqDocumentProfile.setId_shipper(String.valueOf(id_shipper));
        reqDocumentProfile.setShipper_ktp(binding.etDocNoKtp.getText().toString());
        reqDocumentProfile.setShipper_npwp(binding.etNpwp.getText().toString());

        Call<ResUtama> getListPersonalDokumen = API.service().updateDataDokumen(token, reqDocumentProfile);
        getListPersonalDokumen.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Update Dokumen Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });

    }

    private void checkvalidation() {
        String getNoKtp = binding.etDocNoKtp.getText().toString();
        String getNoNpwp = binding.etNpwp.getText().toString();
        String getNoSiup = binding.etSiup.getText().toString();
        String getNoSiujpt = binding.etSiujpt.getText().toString();
        String getNoSkt = binding.etSkt.getText().toString();
        String getTdpNib = binding.etTdpNib.getText().toString();
        String getAktaPendirian = binding.etAktaPendirian.getText().toString();
        String getAktaAkhir = binding.etAktaAkhir.getText().toString();
        String getNoSppkp = binding.etSppkp.getText().toString();
        String getSrtDomisili = binding.etSuratDomisili.getText().toString();

        if (getNoKtp.equals("") || getNoKtp.length() == 0
                || getNoNpwp.equals("") || getNoNpwp.length() == 0
                || getNoSiup.equals("") || getNoSiup.length() == 0
                || getSrtDomisili.equals("") || getSrtDomisili.length() == 0
                || getNoSiujpt.equals("") || getNoSiujpt.length() == 0
                || getNoSkt.equals("") || getNoSkt.length() == 0
                || getTdpNib.equals("") || getTdpNib.length() == 0
                || getAktaPendirian.equals("") || getAktaPendirian.length() == 0
                || getAktaAkhir.equals("") || getAktaAkhir.length() == 0
                || getNoSppkp.equals("") || getNoSppkp.length() == 0
        ) {

            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Data Belum Dilengkapi")
                    .show();
        } else {
            process(param);
        }
    }

    private void process(int param) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        ReqDocumentProfile reqDocumentProfile = new ReqDocumentProfile();
        reqDocumentProfile.setParam(String.valueOf(param));
        reqDocumentProfile.setId_shipper(String.valueOf(id_shipper));
        reqDocumentProfile.setShipper_ktp(binding.etDocNoKtp.getText().toString());
        reqDocumentProfile.setShipper_npwp(binding.etNpwp.getText().toString());
        reqDocumentProfile.setNo_siup(binding.etSiup.getText().toString());
        reqDocumentProfile.setNo_siujpt(binding.etSiujpt.getText().toString());
        reqDocumentProfile.setNo_skt(binding.etSkt.getText().toString());
        reqDocumentProfile.setNo_nib_tdp(binding.etTdpNib.getText().toString());
        reqDocumentProfile.setAkta_pendirian(binding.etAktaPendirian.getText().toString());
        reqDocumentProfile.setAkta_akhir(binding.etAktaAkhir.getText().toString());
        reqDocumentProfile.setNo_sppkp(binding.etSppkp.getText().toString());
        reqDocumentProfile.setSurat_domisili(binding.etSuratDomisili.getText().toString());

        Call<ResUtama> getListDokumen = API.service().updateDataDokumen(token, reqDocumentProfile);
        getListDokumen.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                if (response.code() == 200) {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Update Dokumen Berhasil")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                pDialog.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                t.printStackTrace();
                pDialog.dismissWithAnimation();
            }
        });

    }

    private void cekdata() {
        Call<ResGetProfileFull> getListDataDoc = API.service().getProfilFull(token);
        getListDataDoc.enqueue(new Callback<ResGetProfileFull>() {
            @Override
            public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                Log.d("List getProfil", response.code() + "");
                if (response.code() == 200) {
                    ResGetProfileFull resGetProfileFull = response.body();

                    List<String> dataTypeProv = new ArrayList<>();
                    dataTypeProv.add("Personal");
                    dataTypeProv.add("Perusahaan");

                    ArrayAdapter<String> adapterProv;
                    adapterProv= new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, dataTypeProv);

                    param = resGetProfileFull.getType_shipper_id();

                    if(param==1){

                        String prov = "Personal";
                        if (prov != null) {
                            int spinnerPosition = adapterProv.getPosition(prov);
                            binding.spTipeUsaha.setSelection(spinnerPosition);
                            binding.spTipeUsaha.setEnabled(false);
                            binding.spTipeUsaha.setClickable(false);
                        }

                    }
                    else if(param==2){
                        String prov = "Perusahaan";
                        if (prov != null) {
                            int spinnerPosition = adapterProv.getPosition(prov);
                            binding.spTipeUsaha.setSelection(spinnerPosition);
                            binding.spTipeUsaha.setEnabled(false);
                            binding.spTipeUsaha.setClickable(false);
                        }
                    }
                    //Log.d("ID_SHIPPER", param + "");

                    if (resGetProfileFull.getType_shipper_id() == 1) {

                        /* Personal Akun */
                        binding.lyCompany.setVisibility(View.GONE);
                        binding.etDocNoKtp.setText(String.valueOf(resGetProfileFull.getKtp_shipper()));
                        binding.etNpwp.setText(String.valueOf(resGetProfileFull.getNonpwp()));

                        /* Image Doc sudah di isi apa belum */
                        //KTP
                        String getFileKtp = resGetProfileFull.getKtp_shipper_path();
                        if (getFileKtp == null) {
                            binding.ivDocKtp.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocKtp.setImageResource(R.drawable.ic_picture);
                        }
                        //NPWP
                        String getFileNpwp = resGetProfileFull.getNpwp_file_path();
                        if (getFileNpwp == null) {
                            binding.ivDocNpwp.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocNpwp.setImageResource(R.drawable.ic_picture);
                        }


                    } else if (resGetProfileFull.getType_shipper_id() == 2) {

                        /* Perusahaan Akun */
                        binding.lyCompany.setVisibility(View.VISIBLE);
                        binding.etDocNoKtp.setText(String.valueOf(resGetProfileFull.getKtp_shipper()));
                        binding.etNpwp.setText(String.valueOf(resGetProfileFull.getNonpwp()));
                        binding.etSiup.setText(String.valueOf(resGetProfileFull.getNo_siup()));
                        binding.etSiujpt.setText(String.valueOf(resGetProfileFull.getNo_siujpt()));
                        binding.etSkt.setText(String.valueOf(resGetProfileFull.getNo_skt()));
                        binding.etTdpNib.setText(String.valueOf(resGetProfileFull.getNo_nib_tdp()));
                        binding.etAktaPendirian.setText(String.valueOf(resGetProfileFull.getAkta_pendirian()));
                        binding.etAktaAkhir.setText(String.valueOf(resGetProfileFull.getAkta_akhir()));
                        binding.etSppkp.setText(String.valueOf(resGetProfileFull.getNo_sppkp()));
                        binding.etSuratDomisili.setText(String.valueOf(resGetProfileFull.getSurat_domisili()));

                        /* Image Doc sudah di isi apa belum */
                        //KTP
                        String getFileKtp = resGetProfileFull.getKtp_shipper_path();
                        if (getFileKtp == null) {
                            binding.ivDocKtp.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocKtp.setImageResource(R.drawable.ic_picture);
                        }
                        //NPWP
                        String getFileNpwp = resGetProfileFull.getNpwp_file_path();
                        if (getFileNpwp == null) {
                            binding.ivDocNpwp.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocNpwp.setImageResource(R.drawable.ic_picture);
                        }
                        //SIUP
                        String getFileSiup = resGetProfileFull.getFile_path_siup();
                        if (getFileSiup == null) {
                            binding.ivDocSiup.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocSiup.setImageResource(R.drawable.ic_picture);
                        }
                        //SIUJPT
                        String getFileSiujpt = resGetProfileFull.getFile_path_siujpt();
                        if (getFileSiujpt == null) {
                            binding.ivDocSiujpt.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocSiujpt.setImageResource(R.drawable.ic_picture);
                        }
                        //SKT
                        String getFileSkt = resGetProfileFull.getFile_path_skt();
                        if (getFileSkt == null) {
                            binding.ivDocSkt.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocSkt.setImageResource(R.drawable.ic_picture);
                        }
                        //TDP NIB
                        String getFileTdpNib = resGetProfileFull.getFile_path_nib_tdp();
                        if (getFileTdpNib == null) {
                            binding.ivDocTdpNib.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocTdpNib.setImageResource(R.drawable.ic_picture);
                        }
                        //Akta Pendirian
                        String getFileAktaPendirian = resGetProfileFull.getFile_path_akta();
                        if (getFileAktaPendirian == null) {
                            binding.ivDocAktaPendirian.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocAktaPendirian.setImageResource(R.drawable.ic_picture);
                        }
                        //Akta Akhir
                        String getFileAktaAkhir = resGetProfileFull.getFile_path_akta_akhir();
                        if (getFileAktaAkhir == null) {
                            binding.ivDocAktaAkhir.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocAktaAkhir.setImageResource(R.drawable.ic_picture);
                        }
                        //SPPKP
                        String getFileSppkp = resGetProfileFull.getFile_path_sppkp();
                        if (getFileSppkp == null) {
                            binding.ivDocSppkp.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocSppkp.setImageResource(R.drawable.ic_picture);
                        }
                        //Surat Domisili
                        String getFileSrtDomisili = resGetProfileFull.getFile_path_domisili();
                        if (getFileSrtDomisili == null) {
                            binding.ivDocSuratDomisili.setImageResource(R.drawable.ic_picture_gray);
                        } else {
                            binding.ivDocSuratDomisili.setImageResource(R.drawable.ic_picture);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");
    }
}