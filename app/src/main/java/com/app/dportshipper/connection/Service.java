package com.app.dportshipper.connection;

import com.app.dportshipper.model.MLimit;
import com.app.dportshipper.model.request.MReqFinalOrder;
import com.app.dportshipper.model.request.MReqKirimDetailPengiriman;
import com.app.dportshipper.model.request.MReqProv;
import com.app.dportshipper.model.request.ReqBursaPengiriman;
import com.app.dportshipper.model.request.ReqDetailInputBarang;
import com.app.dportshipper.model.request.ReqDetailTruck;
import com.app.dportshipper.model.request.ReqDocumentProfile;
import com.app.dportshipper.model.request.ReqEmail;
import com.app.dportshipper.model.request.ReqForgotPassword;
import com.app.dportshipper.model.request.ReqGantiPasswordProfil;
import com.app.dportshipper.model.request.ReqInputData;
import com.app.dportshipper.model.request.ReqInvoice;
import com.app.dportshipper.model.request.ReqKirimUlang;
import com.app.dportshipper.model.request.ReqKonfirmasiSelesai;
import com.app.dportshipper.model.request.ReqLastPengiriman;
import com.app.dportshipper.model.request.ReqLoginShipper;
import com.app.dportshipper.model.request.ReqPencarian;
import com.app.dportshipper.model.request.ReqPencarianAuto;
import com.app.dportshipper.model.request.ReqPic;
import com.app.dportshipper.model.request.ReqRegister;
import com.app.dportshipper.model.request.ReqTambahAlamatProfil;
import com.app.dportshipper.model.request.ReqTotal;
import com.app.dportshipper.model.request.ReqTrackingArmada;
import com.app.dportshipper.model.request.ReqUpdateAlamatProfil;
import com.app.dportshipper.model.request.ReqValidateCode;
import com.app.dportshipper.model.request.Reqid_produk_transporter;
import com.app.dportshipper.model.response.MResKab;
import com.app.dportshipper.model.response.MResKategoriBarang;
import com.app.dportshipper.model.response.MResKec;
import com.app.dportshipper.model.response.MResKel;
import com.app.dportshipper.model.response.MResListPengiriman;
import com.app.dportshipper.model.response.MResProv;
import com.app.dportshipper.model.response.MResTOP;
import com.app.dportshipper.model.response.MSukses;
import com.app.dportshipper.model.response.ResAlamatProfil;
import com.app.dportshipper.model.response.ResBursaPengiriman;
import com.app.dportshipper.model.response.ResCost;
import com.app.dportshipper.model.response.ResDataAlamatPenerima;
import com.app.dportshipper.model.response.ResDetailInputBarang;
import com.app.dportshipper.model.response.ResDetailPengiriman;
import com.app.dportshipper.model.response.ResDetailTruck;
import com.app.dportshipper.model.response.ResGetPic;
import com.app.dportshipper.model.response.ResGetProfil;
import com.app.dportshipper.model.response.ResGetProfileFull;
import com.app.dportshipper.model.response.ResGrandTotal;
import com.app.dportshipper.model.response.ResInvoice;
import com.app.dportshipper.model.response.ResKirimUlang;
import com.app.dportshipper.model.response.ResLacakArmada;
import com.app.dportshipper.model.response.ResLastPengiriman;
import com.app.dportshipper.model.response.ResLoginShipper;
import com.app.dportshipper.model.response.ResNotification;
import com.app.dportshipper.model.response.ResOrderPesanan;
import com.app.dportshipper.model.response.ResPencarian;
import com.app.dportshipper.model.response.ResPencarianAuto;
import com.app.dportshipper.model.response.ResProfilUpdate;
import com.app.dportshipper.model.response.ResStatusTab;
import com.app.dportshipper.model.response.ResTrackingArmada;
import com.app.dportshipper.model.response.ResUtama;
import com.app.dportshipper.model.response.ResValidateCode;
import com.app.dportshipper.model.response.ResValidationCode;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Service {
    @Headers({
            "Content-Type:application/json"
    })

    /* Login */
    @POST("api_v1/login/loginAll")
    Call<ResLoginShipper> loginRequest(@Body ReqLoginShipper reqLoginShipper);

    /* AutoComplete */
    @POST("api_v1/ShipperApi/autoCompleteKab")
    Call<List<ResPencarianAuto>> sAutoRequest(@Header ("Authorization") String autorization, @Body ReqPencarianAuto reqPencarianAuto);

    /* Pencarian */
    @POST("api_v1/ShipperApi/searchShipperPage")
    Call<ResPencarian> PencarianShipperPage(@Header ("Authorization") String autorization, @Body ReqPencarian reqPencarian);

    /* Detail Truck */
    @POST("api_v1/ShipperApi/detailPesanan")
    Call<ResDetailTruck> detailTruckPesanan(@Header ("Authorization") String autorization, @Body ReqDetailTruck reqTruck);

    /* Register */
    @POST("api_v1/Login/registerShipper")
    Call<ResUtama> register(@Body ReqRegister reqRegister);

    /* Kirim Ulang */
    @POST("api_v1/Login/kirimUlangEmail")
    Call<ResKirimUlang> kirimUlang(@Body ReqKirimUlang reqKirimUlang);

    /* Validasi Code */
    @POST("api_v1/login/validationCode")
    Call<ResValidateCode> verifikasiCode(@Body ReqValidateCode reqValidateCode);

    /* Input Data Shipper */
    @POST("api_v1/Api/updateDataShipper")
    Call<ResUtama> inputData(@Header("Authorization") String authorization,@Body ReqInputData reqInputData);

    /* Detail Input Barang Shipper */
    @POST("api_v1/ShipperApi/detailInputBarang")
    Call<ResDetailInputBarang> detailInputBarang(@Header("Authorization") String authorization, @Body ReqDetailInputBarang reqDetailInputBarang);

    /* Data Alamat Penerima */
    @POST("api_v1/ShipperApi/getAlamatShipperPenerima")
    Call<List<ResDataAlamatPenerima>> getAlamatPenerima(@Header("Authorization") String authorization);

    /* Riwayat */
    @POST("api_v1/Api_v2/riwayatShipper")
    Call<List<ResBursaPengiriman>> listBursaPengiriman(@Header("Authorization") String authorization);

    /* Detail Riwayat (BursaPengiriman) */
    @POST("api_v1/ShipperApi/detailOrderBursaPengiriman")
    Call<ResDetailPengiriman> detailBursaPengiriman(@Header("Authorization") String authorization, @Body ReqBursaPengiriman reqBursaPengiriman);

    @POST("api_v1/ShipperApi/detailOrderBursaPengirimanPhase2")
    Call<ResDetailPengiriman> detailBursaPengiriman2(@Header("Authorization") String authorization, @Body ReqBursaPengiriman reqBursaPengiriman);

    /* Get Profile */
    @GET("api_v1/ShipperApi/Profile")
    Call<ResGetProfil> getProfile(@Header("Authorization") String authorization);

    /* Update Profile */
    @FormUrlEncoded
    @POST("api_v1/ShipperApi/Profile")
    Call<ResProfilUpdate> updateProfile(@Header("Authorization") String authorization,
                                        @Field("nama_perusahaan") String nama_perusahaan,
                                        @Field("email_perusahaan") String email_perusahaan,
                                        @Field("notelp_perusahaan") String notelp_perusahaan);

    /* Upload foto Profil */
    @Multipart
    @POST("api_v1/ShipperApi/Profile")
    Call<ResUtama> uploadProfile(@Header("Authorization") String authorization,
                                 @Part MultipartBody.Part profile_pic_path,
                                 @Part("id_shipper") RequestBody id_shipper);

    /* Update PIC */
    @POST("api_v1/shipperApi/pic")
    Call<ResGetPic> updatePIC(@Header("Authorization") String authorization, @Body ReqPic reqPic);

    /* Get PIC */
    @GET("api_v1/shipperApi/pic")
    Call<ResGetPic> getPIC(@Header("Authorization") String authorization);

    /* Get Alamat Profil */
    @GET("api_v1/shipperApi/address")
    Call<List<ResAlamatProfil>> getAlamatProfil(@Header("Authorization") String authorization);

    /* Tambah Alamat Profil */
    @POST("api_v1/shipperApi/address")
    Call<ResUtama> tambahAlamatProfil(@Header("Authorization") String authorization, @Body ReqTambahAlamatProfil reqTambahAlamatProfil);

    /* Update Alamat Profil */
    @POST("api_v1/shipperApi/address/{id}")
    Call<ResUtama> updateAlamatProfil(@Header("Authorization") String authorization, @Body ReqUpdateAlamatProfil reqUpdateAlamatProfil, @Path("id") int id);

    /* Forgot Password */
    @POST("api_v1/login/forgotPassword")
    Call<ResUtama> forgotPassword(@Body ReqEmail reqEmail);

    /* Valdiasi Forgot Password */
    @POST("api_v1/login/forgotPasswordValidation")
    Call<ResValidationCode> verifikasiCodeResetPassword(@Body ReqValidateCode reqValidateCode);

    /* Change Password ( New Password ) Login */
    @POST("api_v1/login/changePasswordUser")
    Call<ResValidationCode> resetPassword(@Body ReqForgotPassword reqForgotPassword);

    /* Ganti Password Profile */
    @POST("api_v1/ShipperApi/changePassword")
    Call<ResUtama> gantiPassword(@Header("Authorization") String authorization, @Body ReqGantiPasswordProfil reqGantiPasswordProfil);

    /* Get Data Documment */
    @POST("api_v1/Api/updateDataShipper")
    Call<ReqDocumentProfile> getDataDokumen(@Header("Authorization") String authorization);

    /* Get Profile Full */
    @POST("api_v1/Api/getProfileShipper")
    Call<ResGetProfileFull> getProfilFull(@Header("Authorization") String authorization);

    /* Get Dalam Pengiriman Profil */
    @POST("api_v1/ShipperApi/listBursaPengiriman")
    Call<List<ResLastPengiriman>> getDalamPengirimanProfil(@Header("Authorization") String authorization, @Body ReqLastPengiriman reqLastPengiriman);

    /* Update Dokumen Shipper */
    @POST("api_v1/Api/updateDataShipper")
    Call<ResUtama> updateDataDokumen(@Header("Authorization") String authorization, @Body ReqDocumentProfile reqDokument);

    /* Upload File Dokumen Shipper */
    //KTP
    @Multipart
    @POST("api_v1/Api_v2/uploadKTP")
    Call<ResUtama> uploadKtp(@Header("Authorization") String authorization,
                             @Part("id_shipper") RequestBody id_shipper,
                             @Part MultipartBody.Part ktp_shipper_path);

    //NPWP
    @Multipart
    @POST("api_v1/Api_v2/uploadNPWP")
    Call<ResUtama> uploadNpwp(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part npwp_file_path);

    //SIUP
    @Multipart
    @POST("api_v1/Api_v2/uploadSIUP")
    Call<ResUtama> uploadSiup(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_siup);

    //SIUJPT
    @Multipart
    @POST("api_v1/Api_v2/uploadSIUJPT")
    Call<ResUtama> uploadSiujpt(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_siujpt);

    //SKT
    @Multipart
    @POST("api_v1/Api_v2/uploadSKT")
    Call<ResUtama> uploadSkt(@Header("Authorization") String authorization,
                              @Part MultipartBody.Part file_path_skt,
                              @Part("id_shipper") RequestBody id_shipper);

    //NIB TDP
    @Multipart
    @POST("api_v1/Api_v2/uploadNIB")
    Call<ResUtama> uploadNibTdp(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_nib_tdp);

    //Akta Pendirian
    @Multipart
    @POST("api_v1/Api_v2/uploadAkta")
    Call<ResUtama> uploadAktaPendirian(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_akta);

    //Akta Akhir
    @Multipart
    @POST("api_v1/Api_v2/uploadAktaAkhir")
    Call<ResUtama> uploadAktaAkhir(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_akta_akhir);

    //SPPKP
    @Multipart
    @POST("api_v1/Api_v2/uploadSPPKP")
    Call<ResUtama> uploadSppkp(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_sppkp);

    //Surat Domisili
    @Multipart
    @POST("api_v1/Api_v2/uploadFileDomisili")
    Call<ResUtama> uploadSrtDomisili(@Header("Authorization") String authorization,
                              @Part("id_shipper") RequestBody id_shipper,
                              @Part MultipartBody.Part file_path_domisili);

    @POST("api_v1/Api/provinsi")
    Call<List<MResProv>> getProv(@Header("Authorization") String authorization);

    @POST("api_v1/Api/kabupaten")
    Call<List<MResKab>> getKab(@Header("Authorization") String authorization, @Body MReqProv mReqProv);

    @POST("api_v1/Api/kecamatan")
    Call<List<MResKec>> getKec(@Header("Authorization") String authorization, @Body MReqProv mReqProv);

    @POST("api_v1/Api/kelurahan")
    Call<List<MResKel>> getKel(@Header("Authorization") String authorization, @Body MReqProv mReqProv);

    @POST("api_v1/ShipperApi/getSelectCategorybarang")
    Call<List<MResKategoriBarang>> getKategoribarang(@Header("Authorization") String authorization);

    @POST("api_v1/ShipperApi/getSelectTopShipper")
    Call<List<MResTOP>> getTOP(@Header("Authorization") String authorization);
    //agus api
    @GET("api_v1/Transporter/list_status")
    Call<List<ResStatusTab>> callStatusTab(@Header("Authorization") String authHeader);

    @POST("api_v1/ShipperApi/listBursaPengiriman")
    Call<List<MResListPengiriman>> getDataListPengiriman(@Header("Authorization") String authHeader, @Body MLimit mLimit);

    @POST("api_v1/ShipperApi/OrderPengiriman")
    Call<ResOrderPesanan> kirimDataOrder(@Header("Authorization") String authHeader, @Body MReqKirimDetailPengiriman mReqKirimDetailPengiriman);

    @POST("api_v1/ShipperApi/OrderPengirimanPhase2")
    Call<ResOrderPesanan> kirimDataOrder2(@Header("Authorization") String authHeader, @Body MReqKirimDetailPengiriman mReqKirimDetailPengiriman);

    @POST("api_v1/ShipperApi/OrderBayar")
    Call<MSukses> kirimFinalOrder(@Header("Authorization") String authHeader, @Body MReqFinalOrder mReqFinalOrder);

    @POST("api_v1/ShipperApi/OrderBayarB2b")
    Call<MSukses> orderBayarB2c(@Header("Authorization") String authHeader, @Body MReqFinalOrder mReqFinalOrder);

    @POST("api_v1/ShipperApi/countNotification")
    Call<ResNotification> getNotif(@Header("Authorization") String authorization);

    @GET("api_v1/ShipperApi/econtract/{id}")
    Call<ResponseBody> eContract(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("api_v1/ShipperApi/invoice/{id}")
    Call<ResponseBody> invoice(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("api_v1/ShipperApi/suratJalan/{id}")
    Call<ResponseBody> suratJalan(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("api_v1/ShipperApi/lacak_armada")
    Call<List<ResLacakArmada>> lacakArmada(@Header("Authorization") String authorization);

    @POST("api_v1/ShipperApi/tracking_armada")
    Call<ResTrackingArmada> trackingArmada(@Header("Authorization") String authorization, @Body ReqTrackingArmada reqTrackingArmada);

    @POST("api_v1/ShipperApi/konfirmasiOrderSelesai")
    Call<ResUtama> konfirmasiSelesai(@Header("Authorization") String authorization, @Body ReqKonfirmasiSelesai reqKonfirmasiSelesai);

    @POST("api_v1/ShipperApi/postInvoice")
    Call<ResInvoice> postInvoice(@Header("Authorization") String authorization, @Body ReqInvoice reqInvoice);

    @POST("api_v1/ShipperApi/postInvoiceB2c")
    Call<ResInvoice> postInvoiceB2c(@Header("Authorization") String authorization, @Body ReqInvoice reqInvoice);

    @POST("api_v1/ShipperApi/handleArea")
    Call<List<MResProv>> handleArea(@Header("Authorization") String authorization, @Body Reqid_produk_transporter reqid_produk_transporter);

    @POST("api_v1/ShipperApi/addCost")
    Call<ResCost> addCost(@Header("Authorization") String authorization, @Body Reqid_produk_transporter reqid_produk_transporter);

    @POST("api_v1/ShipperApi/checkGrantotal")
    Call<ResGrandTotal> checkGrantotal(@Header("Authorization") String authorization, @Body ReqTotal reqTotal);

}