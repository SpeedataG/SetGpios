package com.speedatagpios;

import static android.serialport.DeviceControlSpd.POWER_ZHANRUI;
import static com.speedatagpios.ScanConstant.SETGPIO_PATH;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.SystemProperties;
import android.serialport.DeviceControlSpd;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.speedatagpios.ui.SetWindows;
import com.speedatagpios.utils.Logcat;
import com.speedatagpios.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xuyan  新的GPIO调试页面
 * 添加展锐平台  20231205 添加RK平台
 */
public class NewMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private TextView mName;

    /**
     * 设置GPIO1
     */
    private Button mBtnGpio1;
    private ToggleButton mTbt1;
    /**
     * 设置GPIO2
     */
    private Button mBtnGpio2;
    private ToggleButton mTbt2;
    /**
     * 设置GPIO3
     */
    private Button mBtnGpio3;
    private ToggleButton mTbt3;
    /**
     * 设置GPIO4
     */
    private Button mBtnGpio4;
    private ToggleButton mTbt4;
    /**
     * 设置GPIO5
     */
    private Button mBtnGpio5;
    private ToggleButton mTbt5;

    /**
     * 设置GPIO6
     */
    private Button mBtnGpio6;
    private ToggleButton mTbt6;

    /**
     * 设置GPIO7
     */
    private Button mBtnGpio7;
    private ToggleButton mTbt7;

    /**
     * 设置GPIO8
     */
    private Button mBtnGpio8;
    private ToggleButton mTbt8;

    private DeviceControlSpd deviceControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        initView();
    }

    private void initView() {

        mName = findViewById(R.id.title_name);
        mImageView = findViewById(R.id.title_settings);

        SetWindows setWindows = new SetWindows(NewMainActivity.this);

        mImageView.setOnClickListener(v -> setWindows.showAtLocation(mImageView, Gravity.START, 0, 0));

        //注册EventBus
        EventBus.getDefault().register(this);

        initGpioOne();
        initGpioTwo();
        initGpioThree();
        initGpioFour();
        initGpioFive();
        initGpioSix();
        initGpioSeven();
        initGpioEight();

        try {
            deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN_FG);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initGpioOne() {
        mBtnGpio1 = findViewById(R.id.btn_gpio1);
        mBtnGpio1.setOnClickListener(this);
        mTbt1 = findViewById(R.id.tbt1);
        mTbt1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio1.getText().toString().contains(getString(R.string.set_1))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.zhanruiSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio1.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio1.getText().toString());
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio1.getText().toString());
                        mTbt1.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioTwo() {
        mBtnGpio2 = findViewById(R.id.btn_gpio2);
        mBtnGpio2.setOnClickListener(this);
        mTbt2 = findViewById(R.id.tbt2);
        mTbt2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio2.getText().toString().contains(getString(R.string.set_2))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.zhanruiSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio2.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio2.getText().toString());
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio2.getText().toString());
                        mTbt2.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioThree() {
        mBtnGpio3 = findViewById(R.id.btn_gpio3);
        mBtnGpio3.setOnClickListener(this);
        mTbt3 = findViewById(R.id.tbt3);
        mTbt3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio3.getText().toString().contains(getString(R.string.set_3))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.zhanruiSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio3.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio3.getText().toString());
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio3.getText().toString());
                        mTbt3.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioFour() {
        mBtnGpio4 = findViewById(R.id.btn_gpio4);
        mBtnGpio4.setOnClickListener(this);
        mTbt4 = findViewById(R.id.tbt4);
        mTbt4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio4.getText().toString().contains(getString(R.string.set_4))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio4.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio4.getText().toString());
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio4.getText().toString());
                        mTbt4.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioFive() {
        mBtnGpio5 = findViewById(R.id.btn_gpio5);
        mBtnGpio5.setOnClickListener(this);
        mTbt5 = findViewById(R.id.tbt5);
        mTbt5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio5.getText().toString().contains(getString(R.string.set_5))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio5.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio5.getText().toString());
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio5.getText().toString());
                        mTbt5.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioSix() {
        mBtnGpio6 = findViewById(R.id.btn_gpio6);
        mBtnGpio6.setOnClickListener(this);
        mTbt6 = findViewById(R.id.tbt6);
        mTbt6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio6.getText().toString().contains(getString(R.string.set_6))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio6.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio6.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio6.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio6.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio6.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio6.getText().toString()));
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio6.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio6.getText().toString());
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio6.getText().toString());
                        mTbt6.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioSeven() {
        mBtnGpio7 = findViewById(R.id.btn_gpio7);
        mBtnGpio7.setOnClickListener(this);
        mTbt7 = findViewById(R.id.tbt7);
        mTbt7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio7.getText().toString().contains(getString(R.string.set_7))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio7.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio7.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio7.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio7.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio7.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio7.getText().toString()));
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio7.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio7.getText().toString());
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio7.getText().toString());
                        mTbt7.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initGpioEight() {
        mBtnGpio8 = findViewById(R.id.btn_gpio8);
        mBtnGpio8.setOnClickListener(this);
        mTbt8 = findViewById(R.id.tbt8);
        mTbt8.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (mBtnGpio8.getText().toString().contains(getString(R.string.set_8))) {
                    Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio8.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio8.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio8.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio8.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("NEW_MAIN_SC".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.newScSetGpioOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_ZR".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.zhanruiSetDirection(Integer.parseInt(mBtnGpio8.getText().toString()));
                        deviceControl.zhanruiSetGpioOn(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.newScSetGpioOff(Integer.parseInt(mBtnGpio8.getText().toString()));
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                } else if ("MAIN_RK".equals(getType())) {
                    Logcat.d(isChecked);
                    if (isChecked) {
                        deviceControl.rkSetDirection(mBtnGpio8.getText().toString());
                        deviceControl.rkSetGpioOn(mBtnGpio8.getText().toString());
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_high);

                    } else {
                        deviceControl.rkSetGpioOff(mBtnGpio8.getText().toString());
                        mTbt8.setBackgroundResource(R.drawable.ic_switch_off);

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String getType() {
        return SystemProperties.get(SETGPIO_PATH, "NEW_MAIN_FG");
    }


    /**
     * 接收事件
     *
     * @param event event
     */
    @SuppressLint("WrongConstant")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(WeightEvent event) {
        if ("".equals(event.getMessage())) {//更新title显示内容
            mName.setText(SystemProperties.get(SETGPIO_PATH));

            try {

                if ("MAIN".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN);
                } else if ("NEW_MAIN".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN);
                } else if ("NEW_MAIN_FG".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN_FG);
                } else if ("EXPAND".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.EXPAND);
                } else if ("EXPAND2".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.EXPAND2);
                } else if ("NEW_MAIN_SC".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN_SC);
                } else if ("MAIN_ZR".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_ZR);
                } else if ("MAIN_RK".equals(getType())) {
                    deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_RK);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_gpio1:

                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio1, mTbt1);
                } else {
                    setdatas(mBtnGpio1, mTbt1);
                }
                break;
            case R.id.btn_gpio2:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio2, mTbt2);
                } else {
                    setdatas(mBtnGpio2, mTbt2);
                }
                break;
            case R.id.btn_gpio3:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio3, mTbt3);
                } else {
                    setdatas(mBtnGpio3, mTbt3);
                }
                break;
            case R.id.btn_gpio4:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio4, mTbt4);
                } else {
                    setdatas(mBtnGpio4, mTbt4);
                }
                break;
            case R.id.btn_gpio5:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio5, mTbt5);
                } else {
                    setdatas(mBtnGpio5, mTbt5);
                }
                break;
            case R.id.btn_gpio6:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio6, mTbt6);
                } else {
                    setdatas(mBtnGpio6, mTbt6);
                }
                break;
            case R.id.btn_gpio7:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio7, mTbt7);
                } else {
                    setdatas(mBtnGpio7, mTbt7);
                }
                break;
            case R.id.btn_gpio8:
                if ("EXPAND".equals(getType()) || "EXPAND2".equals(getType())) {
                    setoutdatas(mBtnGpio8, mTbt8);
                } else {
                    setdatas(mBtnGpio8, mTbt8);
                }
                break;

        }
    }


    /**
     * 判断状态，当前仅为原判断，新的FG需要参考工厂测试
     */
    public void setdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(NewMainActivity.this);

        if (!"MAIN_RK".equals(getType())) {
            edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        new AlertDialog.Builder(NewMainActivity.this).setTitle(R.string.set_value).setView(edvCount)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    String text = edvCount.getText().toString();
                    if (!"".equals(text)) {
                        btn.setText(text);
                        List list = MainGPIO();

                        if ("NEW_MAIN_FG".equals(getType()) || "NEW_MAIN_SC".equals(getType())) {
                            //从第三行正式开始
                            for (int i = 2; i < list.size(); i++) {
                                String lists = list.get(i).toString();
                                String gpio = lists.substring(0, lists.indexOf(":"));
                                String upordown;
                                if ("MAIN".equals(getType())) {
                                    upordown = lists.substring(7, 8);
                                } else {
                                    upordown = lists.substring(8, 9);
                                    Logcat.d(lists);
                                    Logcat.d(upordown);
                                }
                                if (Integer.parseInt(text) == Integer.parseInt(gpio.trim())) {//gpio去空格
                                    if ("1".equals(upordown)) {
                                        tbtn.setBackgroundResource(R.drawable.ic_switch_high);
                                        tbtn.setChecked(true);
                                        return;
                                    } else if ("0".equals(upordown)) {
                                        tbtn.setChecked(false);
                                        tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                                        return;
                                    } else {
                                        Toast.makeText(NewMainActivity.this, R.string.this_is_the, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else if ("MAIN_ZR".equals(getType())) {
                            //读高还是低,直接读value值是1还是0。
                            String path = POWER_ZHANRUI + "/gpio" + (Integer.parseInt(text) + 64) + "/" + "value";
                            InputStream inputStream;
                            try {
                                inputStream = new FileInputStream(path);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            String result = convertStreamToString(inputStream);
                            if ("1".equals(result)) {
                                tbtn.setBackgroundResource(R.drawable.ic_switch_high);
                                tbtn.setChecked(true);
                            } else if ("0".equals(result)) {
                                tbtn.setChecked(false);
                                tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                            } else {
                                Toast.makeText(NewMainActivity.this, R.string.this_is_the, Toast.LENGTH_SHORT).show();
                            }
                        } else if ("MAIN_RK".equals(getType())) {
                            //读高还是低,直接读value值是1还是0。
                            String path = POWER_ZHANRUI + "/gpio" + text + "/" + "value";
                            InputStream inputStream;
                            try {
                                inputStream = new FileInputStream(path);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            String result = convertStreamToString(inputStream);
                            if ("1".equals(result)) {
                                tbtn.setBackgroundResource(R.drawable.ic_switch_high);
                                tbtn.setChecked(true);
                            } else if ("0".equals(result)) {
                                tbtn.setChecked(false);
                                tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                            } else {
                                Toast.makeText(NewMainActivity.this, R.string.this_is_the, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            for (int i = 1; i < list.size(); i++) {
                                String lists = list.get(i).toString();
                                String gpio = lists.substring(0, lists.indexOf(":"));
                                String upordown;
                                if ("MAIN".equals(getType())) {
                                    upordown = lists.substring(7, 8);
                                } else {
                                    upordown = lists.substring(8, 9);
                                }
                                if (Integer.parseInt(text) == Integer.parseInt(gpio.trim())) {//gpio去空格
                                    if ("1".equals(upordown)) {
                                        tbtn.setBackgroundResource(R.drawable.ic_switch_high);
                                        tbtn.setChecked(true);
                                        return;
                                    } else if ("0".equals(upordown)) {
                                        tbtn.setChecked(false);
                                        tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                                        return;
                                    } else {
                                        Toast.makeText(NewMainActivity.this, R.string.this_is_the, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }


                    } else {
                        Toast.makeText(NewMainActivity.this, R.string.please_set_gpio, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }


    public void setoutdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(NewMainActivity.this);
        edvCount.setHint(R.string.please_set_a);
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(NewMainActivity.this).setTitle(R.string.set_value).setView(edvCount)
                .setPositiveButton(R.string.sure, (dialog, which) -> {
                    String text = edvCount.getText().toString();
                    if (!"".equals(text)) {
                        btn.setText(text);

                        String[] split;
                        try {
                            split = OutGPIO().split("");
                            if ("0xEA".equals(split[1])) {
                                Toast.makeText(NewMainActivity.this, R.string.please_connect_the, Toast.LENGTH_SHORT).show();
                                tbtn.setChecked(false);
                                tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                            } else {

                                if (Integer.parseInt(text) > 15) {
                                    Toast.makeText(NewMainActivity.this, R.string.please_set_the_correct, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            if ("234".equals(OutGPIO())) {
                                Toast.makeText(NewMainActivity.this, R.string.please_connect_the, Toast.LENGTH_SHORT).show();
                                tbtn.setChecked(false);
                                tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                            } else {
                                if ("".equals(OutGPIO())) {
                                    return;
                                }
                                String gpio = Integer.toBinaryString(Integer.parseInt(OutGPIO()));
                                List outlist = Collections.singletonList(gpio);
                                if (Integer.parseInt(text) > 15) {

                                    Toast.makeText(NewMainActivity.this, R.string.please_set_the_correct, Toast.LENGTH_SHORT).show();
                                    tbtn.setChecked(false);
                                    tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                                }
                            }
                        }

                    } else {
                        tbtn.setChecked(false);
                        tbtn.setBackgroundResource(R.drawable.ic_switch_off);
                        Toast.makeText(NewMainActivity.this, R.string.please_set, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }

    public String OutGPIO() {
        BufferedReader reader;
        try {
            if ("EXPAND".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9523/gpio"));
            } else {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9524/gpio"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            for (int i = 0; i < 16; i++) {
                if ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /**
     * 获取gpio状态。原来的和新fg平台不同，可参考工厂测试GPIO测试项
     */
    public List MainGPIO() {
        BufferedReader reader;
        List lists = new ArrayList();
        try {
            if ("MAIN".equals(getType())) {
                reader = new BufferedReader(new FileReader("sys/class/misc/mtgpio/pin"));
                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("NEW_MAIN".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/bus/platform/drivers/mediatek-pinctrl/10005000.pinctrl/mt_gpio"));

                String line;
                try {
                    for (int i = 1; i < 163; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("NEW_MAIN_FG".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/devices/platform/pinctrl/mt_gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("EXPAND".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9523/gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("MAIN_AND_EXPAND".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9523/gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("EXPAND2".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9524/gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("MAIN_AND_EXPAND2".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/class/misc/aw9524/gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if ("NEW_MAIN_SC".equals(getType())) {
                reader = new BufferedReader(new FileReader("/sys/devices/platform/pinctrl@1000b000/mt_gpio"));

                String line;
                try {
                    for (int i = 1; i < 203; i++) {
                        if ((line = reader.readLine()) != null) {
                            lists.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lists;
    }

    public String zhanruiPower(String path) {
        String result = "";
        try {
            Log.d("xuyanshuai", "zhanruiPower");
            BufferedReader CtrlFile = new BufferedReader(new FileReader(path));
            result = CtrlFile.readLine();
            CtrlFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    //返回键监听
    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                    mkeyTime = System.currentTimeMillis();
                    boolean cn = "CN".equals(getApplicationContext().getResources().getConfiguration().locale.getCountry());
                    if (cn) {
                        ToastUtils.showShortToastSafe("再次点击返回退出");
                    } else {
                        ToastUtils.showShortToastSafe("Press the exit again");
                    }
                } else {
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}