package com.speedatagpios;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.SystemProperties;
import android.serialport.DeviceControlSpd;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.speedatagpios.ui.SetWindows;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.speedatagpios.ScanConstant.SETGPIO_PATH;

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

        mImageView.setOnClickListener(v -> new SetWindows(NewMainActivity.this).showAtLocation(mImageView, Gravity.START, 0, 0));

        //注册EventBus
        EventBus.getDefault().register(this);

        initGpioOne();
        initGpioTwo();
        initGpioThree();
        initGpioFour();
        initGpioFive();

        try {
            deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN);
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
                if (mBtnGpio1.getText().toString().contains("设置GPIO")) {
                    Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_off_false);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio1.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                }

//                else if ("GAOTONG_MAIN".equals(getType())) {
//                    //dont do any thing
//                }

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
                if (mBtnGpio2.getText().toString().contains("设置GPIO")) {
                    Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_off_false);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio2.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                }

//                else if ("GAOTONG_MAIN".equals(getType())) {
//
//                }

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
                if (mBtnGpio3.getText().toString().contains("设置GPIO")) {
                    Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_off_false);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio3.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("GAOTONG_MAIN".equals(getType())) {

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
                if (mBtnGpio4.getText().toString().contains("设置GPIO")) {
                    Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_off_false);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio4.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("GAOTONG_MAIN".equals(getType())) {

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
                if (mBtnGpio5.getText().toString().contains("设置GPIO")) {
                    Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("MAIN".equals(getType())) {
                    if (isChecked) {
                        deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("NEW_MAIN".equals(getType()))) {
                    if (isChecked) {
                        deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_off_false);

                    }
                } else if (("EXPAND".equals(getType()))) {

                    if (isChecked) {
                        deviceControl.ExpandPowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.ExpandPowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_off_false);

                    }

                } else if ("MAIN_AND_EXPAND".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("NEW_MAIN_FG".equals(getType())) {

                    if (isChecked) {
                        deviceControl.newFgSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.newFgSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl.Expand2PowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl.Expand2PowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                } else if ("MAIN_AND_EXPAND2".equals(getType())) {

                    if (isChecked) {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOnDevice();
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    } else {
                        deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN_AND_EXPAND2, Integer.parseInt(mBtnGpio5.getText().toString()));
                        deviceControl.PowerOffDevice();
                        mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);

                    }

                }

//                else if ("GAOTONG_MAIN".equals(getType())) {
//
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private String getType() {
        return SystemProperties.get(SETGPIO_PATH, "NEW_MAIN");
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
//            case R.id.btn_new_gpio1:
//                break;
//            case R.id.btn_set_gpio1:
//                setoutdatas(mBtnSetGpio1, mTbtW1);
//                break;
//            case R.id.btn_set_gpio2:
//                setoutdatas(mBtnSetGpio2, mTbtW2);
//                break;
//            case R.id.btn_set_gpio3:
//                setoutdatas(mBtnSetGpio3, mTbtW3);
//                break;
//            case R.id.btn_set_gpio4:
//                setoutdatas(mBtnSetGpio4, mTbtW4);
//                break;
//            case R.id.btn_to_imbox:
//                startActivity(new Intent(this, IMBoxGpioAct.class));
//                break;
        }
    }


    /**
     * 判断状态，当前仅为原判断，新的FG需要参考工厂测试
     */
    public void setdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(NewMainActivity.this);
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(NewMainActivity.this).setTitle("设置GPIO值").setView(edvCount)
                .setPositiveButton("确定", (dialog, which) -> {
                    String text = edvCount.getText().toString();
                    if (!"".equals(text)) {
                        btn.setText(text);
                        //                            btn.setTextColor(Color.RED);
                        List list = MainGPIO();
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
                                    tbtn.setBackgroundResource(R.drawable.btn_press_on_true);
                                    tbtn.setChecked(true);
                                    mTbt1.setTextOn("高");
                                    return;
                                } else if ("0".equals(upordown)) {
                                    tbtn.setChecked(false);
                                    mTbt1.setTextOff("低");
                                    tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                                    return;
                                } else {
                                    Toast.makeText(NewMainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(NewMainActivity.this, "请设置GPIO", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", null).show();
    }


    public void setoutdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(NewMainActivity.this);
        edvCount.setHint("请设置0--7数值");
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(NewMainActivity.this).setTitle("设置GPIO值").setView(edvCount)
                .setPositiveButton("确定", (dialog, which) -> {
                    String text = edvCount.getText().toString();
                    if (!"".equals(text)) {
                        btn.setText(text);
                        //                            btn.setTextColor(Color.RED);
                        String[] split;
                        try {
                            split = OutGPIO().split("");
                            if ("0xEA".equals(split[1])) {
                                Toast.makeText(NewMainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
                                tbtn.setChecked(false);
                                mTbt1.setTextOff("低");
                                tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                            } else {

                                if (Integer.parseInt(text) > 7) {
                                    Toast.makeText(NewMainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            if ("234".equals(OutGPIO())) {
                                Toast.makeText(NewMainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
                                tbtn.setChecked(false);
                                mTbt1.setTextOff("低");
                                tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                            } else {
                                if ("".equals(OutGPIO())) {
                                    return;
                                }
                                String gpio = Integer.toBinaryString(Integer.parseInt(OutGPIO()));
                                List outlist = Collections.singletonList(gpio);
                                if (Integer.parseInt(text) > 7) {

                                    Toast.makeText(NewMainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
                                    tbtn.setChecked(false);
                                    mTbt1.setTextOff("低");
                                    tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                                }
                            }
                        }

                    } else {
                        tbtn.setChecked(false);
                        mTbt1.setTextOff("低");
                        tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                        Toast.makeText(NewMainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", null).show();
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
        String line = null;
        try {
            for (int i = 0; i < 8; i++) {
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
                String line = null;
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

                String line = null;
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

                String line = null;
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

                String line = null;
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

                String line = null;
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

                String line = null;
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

                String line = null;
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

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}