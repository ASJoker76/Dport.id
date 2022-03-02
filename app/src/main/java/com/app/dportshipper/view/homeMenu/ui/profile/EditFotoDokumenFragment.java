package com.app.dportshipper.view.homeMenu.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.dportshipper.R;
import com.app.dportshipper.connection.API;
import com.app.dportshipper.databinding.FragmentDokumentPerjanjianBinding;
import com.app.dportshipper.databinding.FragmentEditFotoDokumentBinding;
import com.app.dportshipper.model.response.ResGetProfileFull;
import com.app.dportshipper.model.response.ResUtama;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFotoDokumenFragment extends Fragment {

    private FragmentEditFotoDokumentBinding binding;

    private String btnKlik;
    private ContentValues values;
    private Uri imageUri;
    static final int REQUEST_GALLERY_PHOTO = 2;
    static final int ID_ACT_CAMERA_UPLOAD = 200;
    private String imageurlKTP;
    private String imageKtp = "";
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private Bitmap thumbnailKTP;
    Uri selectedImage;
    private int id_shipper;
    private String status;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditFotoDokumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadsession();
        onloadgambar();
        onclick();

        return root;
    }

    private void onclick() {
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DokumentPerjanjianFragment fragementIntent = new DokumentPerjanjianFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragementIntent);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.ivDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKlik = status;
                selectImage();
            }
        });

        binding.txtFotoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ivDokumen.performClick();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    requestStoragePermission(true);
                } else if (items[item].equals("Choose from Library")) {
                    requestStoragePermission(false);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void requestStoragePermission(boolean b) {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (b) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void dispatchGalleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_GALLERY_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        issueCameraIntentUpload();
    }

    private void issueCameraIntentUpload() {
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, ID_ACT_CAMERA_UPLOAD);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == ID_ACT_CAMERA_UPLOAD) {
            if (resultCode == RESULT_OK ) {
                try {
                    thumbnailKTP = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(), imageUri);
                    int nh = (int) (thumbnailKTP.getHeight() * (512.0 / thumbnailKTP.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(thumbnailKTP, 512, nh, true);
                    imageurlKTP = getRealPathFromURI(imageUri);

                    float division;
                    if (thumbnailKTP.getHeight() < thumbnailKTP.getWidth()) {
                        division = (float) thumbnailKTP.getWidth() / 1024F;
                    } else {
                        division = (float) thumbnailKTP.getHeight() / 1024F;
                    }
                    int newHeight = (int) (thumbnailKTP.getHeight() / division);
                    int newWidth = (int) (thumbnailKTP.getWidth() / division);

                    Bitmap sendImage = Bitmap.createScaledBitmap(thumbnailKTP, newWidth, newHeight, false);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    sendImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    File destination = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), getNameRandom(15) + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(out.toByteArray());
                        fo.close();
                        imageKtp = destination.getPath();
                        binding.ivDokumen.setImageBitmap(scaled);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == REQUEST_GALLERY_PHOTO) {
            try {
                if (result != null) { // user selects some Image
                    selectedImage = result.getData();
                    if (selectedImage != null) {
                        selectedImage = result.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

                        float division;
                        if (bitmap.getHeight() < bitmap.getWidth()) {
                            division = (float) bitmap.getWidth() / 1024F;
                        } else {
                            division = (float) bitmap.getHeight() / 1024F;
                        }
                        int newHeight = (int) (bitmap.getHeight() / division);
                        int newWidth = (int) (bitmap.getWidth() / division);

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        Log.d("sidik size", out.toByteArray().length + "");
                        File destination = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), getNameRandom(15) + ".jpg");
                        FileOutputStream fo;

                        try {
                            fo = new FileOutputStream(destination);
                            fo.write(out.toByteArray());
                            fo.close();
                            imageKtp = destination.getPath();
                            binding.ivDokumen.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        // jika di klik back atau cancel
                    }
                }
                else{
                    // jika di klik back atau cancel
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(btnKlik.equals("ktp")){
            inputktp();
        }
        else if(btnKlik.equals("npwp")){
            inputnpwp();
        }
        else if(btnKlik.equals("siup")){
            inputsiup();
        }
        else if(btnKlik.equals("siujpt")){
            inputsiujpt();
        }
        else if(btnKlik.equals("skt")){
            inputskt();
        }
        else if(btnKlik.equals("tdp")){
            inputtdp();
        }
        else if(btnKlik.equals("aktapendirian")){
            inputaktapendirian();
        }
        else if(btnKlik.equals("aktaakhir")){
            inputaktaakhir();
        }
        else if(btnKlik.equals("sppkp")){
            inputsppkp();
        }
        else if(btnKlik.equals("domisili")){
            inputdomisili();
        }
    }

    /* input Surat Domisili */
    private void inputdomisili() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_domisili = MultipartBody.Part.createFormData("file_path_domisili", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadSrtDomisili(token,
                    idShipper,
                    file_path_domisili
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadSrtDomisili(token,
                    idShipper,
                    file_path_domisili
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input Sppkp */
    private void inputsppkp() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_sppkp = MultipartBody.Part.createFormData("file_path_sppkp", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadSppkp(token,
                    idShipper,
                    file_path_sppkp
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadSppkp(token,
                    idShipper,
                    file_path_sppkp
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input Akta Akhir */
    private void inputaktaakhir() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_akta_akhir = MultipartBody.Part.createFormData("file_path_akta_akhir", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadAktaAkhir(token,
                    idShipper,
                    file_path_akta_akhir
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadAktaAkhir(token,
                    idShipper,
                    file_path_akta_akhir
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input Akta Pendirian */
    private void inputaktapendirian() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_akta = MultipartBody.Part.createFormData("file_path_akta", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadAktaPendirian(token,
                    idShipper,
                    file_path_akta
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadAktaPendirian(token,
                    idShipper,
                    file_path_akta
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input TDP NIB */
    private void inputtdp() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_nib_tdp = MultipartBody.Part.createFormData("file_path_nib_tdp", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadNibTdp(token,
                    idShipper,
                    file_path_nib_tdp
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadNibTdp(token,
                    idShipper,
                    file_path_nib_tdp
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input SKT */
    private void inputskt() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_skt = MultipartBody.Part.createFormData("file_path_skt", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadSkt(token,
                    file_path_skt,
                    idShipper
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadSkt(token,
                    file_path_skt,
                    idShipper
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input SIUJPT */
    private void inputsiujpt() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_siujpt = MultipartBody.Part.createFormData("file_path_siujpt", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadSiujpt(token,
                    idShipper,
                    file_path_siujpt
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadSiujpt(token,
                    idShipper,
                    file_path_siujpt
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input SIUP */
    private void inputsiup() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part file_path_siup = MultipartBody.Part.createFormData("file_path_siup", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadSiup(token,
                    idShipper,
                    file_path_siup
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadSiup(token,
                    idShipper,
                    file_path_siup
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input NPWP */
    private void inputnpwp() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part npwp_file_path = MultipartBody.Part.createFormData("npwp_file_path", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadNpwp(token,
                    idShipper,
                    npwp_file_path
            );
        } else {
            sentDataPendaftaranLangsung = API.service().uploadNpwp(token,
                    idShipper,
                    npwp_file_path
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    /* input KTP */
    private void inputktp() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        File fileFotoKtp = new File(imageKtp);
        RequestBody requestFileKtp = RequestBody.create(MediaType.parse("multipart/form-data"), fileFotoKtp);
        MultipartBody.Part ktp_shipper_path = MultipartBody.Part.createFormData("ktp_shipper_path", fileFotoKtp.getName(), requestFileKtp);

        RequestBody idShipper = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_shipper));

        Call<ResUtama> sentDataPendaftaranLangsung;
        if (imageKtp.isEmpty()) {
            sentDataPendaftaranLangsung = API.service().uploadKtp(token,
                    idShipper,
                    ktp_shipper_path

            );
        } else {

            sentDataPendaftaranLangsung = API.service().uploadKtp(token,
                    idShipper,
                    ktp_shipper_path
            );
        }

        sentDataPendaftaranLangsung.enqueue(new Callback<ResUtama>() {
            @Override
            public void onResponse(Call<ResUtama> call, Response<ResUtama> response) {
                Log.d("respon", response.code()+"");
                if (response.code()==200){
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil Upload KTP")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkeprofile();
                                }
                            })
                            .show();
                }
                else {
                    pDialog.dismissWithAnimation();
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Upload KTP Gagal")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResUtama> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                pDialog.dismissWithAnimation();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Ada Kesalahan Sistem")
                        .show();
            }
        });
    }

    private void pindahkeprofile() {
        DokumentPerjanjianFragment inbound = new DokumentPerjanjianFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, inbound);
        ft.commit();
    }

    private String getNameRandom(int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private String getRealPathFromURI(Uri imageUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(imageUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    private void onloadgambar() {

        if(status.equals("ktp")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();

                        Log.d("file_ktp", resGetProfile.getId_shipper() + "");

                        id_shipper = resGetProfile.getId_shipper();

                        //Log.d("Load_KTP", ktp_Path + "");
                        Glide.with(getActivity())
                                .load(resGetProfile.getKtp_shipper_path())
                                //.apply(new RequestOptions().override(750, 400))
                                .centerCrop()
                                .into(binding.ivDokumen);


                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("npwp")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getNpwp_file_path())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("siup")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_siup())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("siujpt")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_siujpt())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("skt")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_skt())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("tdp")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getTdp_file_path())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("aktapendirian")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_akta())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("aktaakhir")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_akta_akhir())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("sppkp")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_sppkp())
                                .centerCrop()
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }
        else if(status.equals("domisili")){
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
            Call<ResGetProfileFull> getListCall = API.service().getProfilFull(token);
            getListCall.enqueue(new Callback<ResGetProfileFull>() {
                @Override
                public void onResponse(Call<ResGetProfileFull> call, Response<ResGetProfileFull> response) {
                    if (response.code() == 200) {
                        binding.txtFotoDoc.setVisibility(View.GONE);
                        ResGetProfileFull resGetProfile = response.body();
                        id_shipper = resGetProfile.getId_shipper();
                        Glide.with(getActivity())
                                .load(resGetProfile.getFile_path_domisili())
                                .centerCrop()
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.error404))
                                .into(binding.ivDokumen);
                        pDialog.dismissWithAnimation();
                    }
                    pDialog.dismissWithAnimation();
                }

                @Override
                public void onFailure(Call<ResGetProfileFull> call, Throwable t) {
                    t.printStackTrace();
                    pDialog.dismissWithAnimation();
                }
            });
        }

    }

    private void loadsession() {
        SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            status = bundle.getString("status");
        }
    }
}