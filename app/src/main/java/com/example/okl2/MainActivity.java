package com.example.okl2;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.locks.Lock;

public class MainActivity extends AppCompatActivity {

    private Vibrator vibrator;

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdminSample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeviceAdminSample = new ComponentName(this,LockReceiver.class);
        //设备政策管理器
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        /*振动器*/
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);

        if (mDPM.isAdminActive(mDeviceAdminSample)){
            long[] pattern = new long[]{1,1,1,4};
            vibrator.vibrate(pattern,-1);
            /*vibrator.vibrate(5);*/
            mDPM.lockNow();
            finish();

        }else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"OneKeyLock");
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
