package com.app.dportshipper.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.dportshipper.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.isConnectedToInternet(context)){
            new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setCustomImage(R.drawable.ic_baseline_signal_wifi_off_24)
                    .setTitleText("INTERNET NOT CONNECTED")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            onReceive(context,intent);
                        }
                    })
                    .show();
        }
    }
}
