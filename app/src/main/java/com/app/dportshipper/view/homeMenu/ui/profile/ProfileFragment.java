package com.app.dportshipper.view.homeMenu.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentProfilBinding;
import com.app.dportshipper.model.request.ReqLastPengiriman;
import com.app.dportshipper.model.response.ResGetProfil;
import com.app.dportshipper.model.response.ResLastPengiriman;
import com.app.dportshipper.view.login.LoginActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private List<ResLastPengiriman> listPengiriman;

//    private TextView txtNamaShipper, lblDalamPengiriman;
//    private RecyclerView rvDalamPengiriman;
    //private DalamPengirimanAdapter adapter;
    String username, token, email, nama, notlp, alamat, namapic, noktppic, emailpic, kontakpic,
            alamatpic;
    private LinearLayout llAkun, llKataSandi, llDokumen, llPic, llLogout;
//    private ImageView ivEditProfil;
//    private CircleImageView civProfilShipper;
    String foto;
    private int id_shipper;
    private FragmentProfilBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        txtNamaShipper = root.findViewById(R.id.txt_name_shipper_profile);
//        civProfilShipper = root.findViewById(R.id.civ_profile);
//        rvDalamPengiriman = root.findViewById(R.id.rv_dalam_pengiriman);
        //lblDalamPengiriman = root.findViewById(R.id.lbl_dalam_pengiriman);
        inisiasi();
        onloadsession();
        getdataprofile();
        loadData();

//        ivEditProfil = root.findViewById(R.id.iv_edit_profile);
//        ivEditProfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("nama", nama);
//                bundle.putString("email", email);
//                bundle.putString("notlp", notlp);
//                bundle.putString("alamat", alamat);
//                bundle.putString("foto", foto);
//                bundle.putInt("id_shipper", id_shipper);
//                bundle.putString("namapic", namapic);
//                bundle.putString("noktppic", noktppic);
//                bundle.putString("emailpic", emailpic);
//                bundle.putString("kontakpic", kontakpic);
//                bundle.putString("alamatpic", alamatpic);
//
//                EditProfileFragment inbound = new EditProfileFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                inbound.setArguments(bundle);
//                ft.commit();
//            }
//        });
        llAkun = root.findViewById(R.id.ll_akun);
        llAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle = new Bundle();
//                bundle.putString("nama", nama);
//                bundle.putString("email", email);
//                bundle.putString("notlp", notlp);
//                bundle.putString("alamat", alamat);
//                bundle.putString("foto", foto);
//                bundle.putInt("id_shipper", id_shipper);
//                bundle.putString("namapic", namapic);
//                bundle.putString("noktppic", noktppic);
//                bundle.putString("emailpic", emailpic);
//                bundle.putString("kontakpic", kontakpic);
//                bundle.putString("alamatpic", alamatpic);
//
//                EditProfileFragment inbound = new EditProfileFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                inbound.setArguments(bundle);
//                ft.commit();

            }
        });

        llKataSandi = root.findViewById(R.id.ll_kata_sandi);
        llKataSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = null;
//                EditKataSandiFragment inbound = new EditKataSandiFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                ft.commit();
            }
        });

        llDokumen = root.findViewById(R.id.ll_dokumen);
        llDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("nama", nama);
//                bundle.putString("email", email);
//                bundle.putString("notlp", notlp);
//                bundle.putString("alamat", alamat);
//                bundle.putString("foto", foto);
//                bundle.putInt("id_shipper", id_shipper);
//                bundle.putString("namapic", namapic);
//                bundle.putString("noktppic", noktppic);
//                bundle.putString("emailpic", emailpic);
//                bundle.putString("kontakpic", kontakpic);
//                bundle.putString("alamatpic", alamatpic);
//
//                EditInputDokumenProfileFragment inbound = new EditInputDokumenProfileFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                inbound.setArguments(bundle);
//                ft.commit();
            }
        });

        llPic = root.findViewById(R.id.ll_pic);
        llPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = null;
//                EditPicFragment inbound = new EditPicFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.nav_host_fragment, inbound);
//                ft.commit();
            }
        });

        /* Logout */
        llLogout = root.findViewById(R.id.ll_logout);
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah Anda Yakin?")
                        .setContentText("ingin keluar dari Aplikasi ini")
                        .setCancelText("Tidak")
                        .setConfirmText("Lanjut")
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
                                SharedPreferences preferences = getActivity().getSharedPreferences("login", 0);
                                preferences.edit().clear().apply();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                getActivity().finish();
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        // matiin onbackpress
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });

        return root;
    }

    private void inisiasi() {
        //kecilin ukuran drawble search
        Drawable dr = getResources().getDrawable(R.drawable.search);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 15, 15, true));

        //setCompoundDrawablesWithIntrinsicBounds (image to left, top, right, bottom)
        binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(d,null,null,null);
    }

    private void loadData() {
        listPengiriman = new ArrayList<>();
        ReqLastPengiriman reqLastPengiriman = new ReqLastPengiriman();
        reqLastPengiriman.setLimit(5);
        reqLastPengiriman.setOffset(10);
        reqLastPengiriman.setStatus(12);

        /*

        Log.d("resPengiriman", response.code() + "");
                if (response.code() == 200){
                    List<ResLastPengiriman> resLastPengiriman = response.body();
                    Log.d("dataaaaa", resLastPengiriman + "");
                }

        }*/

        Call<List<ResLastPengiriman>> callData = API.service().getDalamPengirimanProfil(token, reqLastPengiriman);
        callData.enqueue(new Callback<List<ResLastPengiriman>>() {
            @Override
            public void onResponse(Call<List<ResLastPengiriman>> call, Response<List<ResLastPengiriman>> response) {
                Log.d("resPengiriman", response.code() + "");
                if (response.code() == 200){

//                    List<ResLastPengiriman> resLastPengiriman = response.body();
//                    callll();
//                    listPengiriman.addAll(resLastPengiriman);
//                    adapter.notifyDataSetChanged();

                }else if (response.code() == 204){
                    //lblDalamPengiriman.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ResLastPengiriman>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void callll() {
//        adapter = new DalamPengirimanAdapter(this, listPengiriman);
//        rvDalamPengiriman.setHasFixedSize(true);
//        rvDalamPengiriman.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        rvDalamPengiriman.setAdapter(adapter);
    }

    private void getdataprofile() {
//        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading ...");
//        pDialog.setCancelable(false);
//        pDialog.show();

        Call<ResGetProfil> callGetProfil = API.service().getProfile(token);
        callGetProfil.enqueue(new Callback<ResGetProfil>() {
            @Override
            public void onResponse(Call<ResGetProfil> call, Response<ResGetProfil> response) {
                Log.d("reza", response.code()+"");
                if (response.code() == 200){
                    ResGetProfil resGetProfil = response.body();

                    id_shipper = resGetProfil.getId_shipper();
                    nama = resGetProfil.getNama_perusahaan();
                    email = resGetProfil.getEmail_perusahaan();
                    notlp = String.valueOf(resGetProfil.getNotelp_perusahaan());
                    foto = resGetProfil.getProfile_pic_path();

                    binding.txtNameShipper.setText(nama);
                    Glide.with(getActivity())
                            .load(foto)
                            .centerCrop()
                            .placeholder(R.drawable.image)
                            .into(binding.civProfile);

                    //pDialog.dismissWithAnimation();

                }
            }

            @Override
            public void onFailure(Call<ResGetProfil> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void onloadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        username = prefs.getString("username", "");
        token = prefs.getString("token", "");
        email = prefs.getString("email", "");
    }
}