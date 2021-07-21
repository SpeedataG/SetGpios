 package com.speedatagpios;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author xuyan  imbox
 */
public class IMBoxGpioAct extends AppCompatActivity {

    private static final String GPIO1 = "/devices/platform/function_gpio/gpio2_a0";
    private static final String GPIO2 = "/devices/platform/function_gpio/gpio2_a1";
    private static final String GPIO3 = "/devices/platform/function_gpio/gpio2_a2";
    private static final String GPIO4 = "/devices/platform/function_gpio/gpio2_a5";
    private ToggleButton mTbtW1;
    private ToggleButton mTbtW2;
    private ToggleButton mTbtW3;
    private ToggleButton mTbtW4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_m_box_gpio);
        mTbtW1 = findViewById(R.id.tbt1);
        mTbtW2 = findViewById(R.id.tbt2);
        mTbtW3 = findViewById(R.id.tbt3);
        mTbtW4 = findViewById(R.id.tbt4);
        mTbtW1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPower(GPIO1, "1");
                    mTbtW1.setBackgroundResource(R.drawable.btn_press_on_true);
                } else {
                    setPower(GPIO1, "0");
                    mTbtW1.setBackgroundResource(R.drawable.btn_press_off_false);
                }
            }
        });

        mTbtW2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPower(GPIO2, "1");
                    mTbtW2.setBackgroundResource(R.drawable.btn_press_on_true);
                } else {
                    setPower(GPIO2, "0");
                    mTbtW2.setBackgroundResource(R.drawable.btn_press_off_false);
                }
            }
        });

        mTbtW3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPower(GPIO3, "1");
                    mTbtW3.setBackgroundResource(R.drawable.btn_press_on_true);
                } else {
                    setPower(GPIO3, "0");
                    mTbtW3.setBackgroundResource(R.drawable.btn_press_off_false);
                }
            }
        });

        mTbtW4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setPower(GPIO4, "1");
                    mTbtW4.setBackgroundResource(R.drawable.btn_press_on_true);
                } else {
                    setPower(GPIO4, "0");
                    mTbtW4.setBackgroundResource(R.drawable.btn_press_off_false);
                }
            }
        });

    }

    /**
     * @param power 0下电 1上电
     */
    private void setPower(String path, String power) {
        File DeviceName = new File(path);
        try {
            BufferedWriter CtrlFile = new BufferedWriter(new FileWriter(DeviceName, false));
            CtrlFile.write(power);
            CtrlFile.flush();
            CtrlFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}