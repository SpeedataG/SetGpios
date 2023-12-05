package com.speedatagpios;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.serialport.DeviceControlSpd;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xuyan  旧界面  当前弃用
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private static final String MAIN_GPIO = "sys/class/misc/mtgpio/pin";
    private static final String OUT_GPIO = "/sys/class/misc/aw9523/gpio";
    private int isFlag = 0;
    private String outgpioinfo;
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
     * 设置GPIO5
     */
    private Button mBtnNewGpio1;
    private ToggleButton mNewBtn1;
    /**
     * 拓展GPIO1
     */
    private Button mBtnSetGpio1;
    private ToggleButton mTbtW1;
    /**
     * 拓展GPIO2
     */
    private Button mBtnSetGpio2;
    private ToggleButton mTbtW2;
    /**
     * 拓展GPIO3
     */
    private Button mBtnSetGpio3;
    private ToggleButton mTbtW3;
    /**
     * 拓展GPIO4
     */
    private Button mBtnSetGpio4;
    private ToggleButton mTbtW4;

    private DeviceControlSpd deviceControl;
    private DeviceControlSpd deviceControl2;
    private Switch mSSwitch;
    private Button btnIMBox;

    /*
    16进制转2二进制
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    public String OutGPIO() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/sys/class/misc/aw9523/gpio"));
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

    public List MainGPIO() {
        BufferedReader reader = null;
        List lists = new ArrayList();
        try {
            if (isFlag == 0) {
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
            } else {
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

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lists;
    }

    private void init() {
        try {
            if (isFlag == 0) {
                deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.MAIN);
            } else {
                deviceControl = new DeviceControlSpd(DeviceControlSpd.PowerType.NEW_MAIN);
            }
            deviceControl2 = new DeviceControlSpd(DeviceControlSpd.PowerType.EXPAND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(MainActivity.this);
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(MainActivity.this).setTitle("设置GPIO值").setView(edvCount)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edvCount.getText().toString();
                        if (!"".equals(text)) {
                            btn.setText(text);
                            //                            btn.setTextColor(Color.RED);
                            List list = MainGPIO();
                            for (int i = 1; i < list.size(); i++) {
                                String lists = list.get(i).toString();
                                String gpio = lists.substring(0, lists.indexOf(":"));
                                String upordown;
                                if (isFlag == 0) {
                                    upordown = lists.substring(7, 8);
                                } else {
                                    upordown = lists.substring(8, 9);
                                }
                                if (text.equals(gpio.trim())) {//gpio去空格
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
                                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "请设置GPIO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    public void setoutdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(MainActivity.this);
        edvCount.setHint("请设置0--7数值");
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(MainActivity.this).setTitle("设置GPIO值").setView(edvCount)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edvCount.getText().toString();
                        if (!"".equals(text)) {
                            btn.setText(text);
                            //                            btn.setTextColor(Color.RED);
                            String[] split = new String[0];
                            try {
                                split = OutGPIO().split("");
                                if ("0xEA".equals(split[1])) {
                                    Toast.makeText(MainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
                                    tbtn.setChecked(false);
                                    mTbt1.setTextOff("低");
                                    tbtn.setBackgroundResource(R.drawable.btn_press_off_false);
                                } else {
                                    String bb = hexString2binaryString(split[1]);
                                    List outlist = new ArrayList();
                                    if (Integer.parseInt(text) > 7) {
                                        Toast.makeText(MainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                if ("234".equals(OutGPIO())) {
                                    Toast.makeText(MainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
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

                                        Toast.makeText(MainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void initView() {
        mBtnGpio1 = findViewById(R.id.btn_gpio1);
        mBtnGpio1.setOnClickListener(this);
        mTbt1 = findViewById(R.id.tbt1);
        btnIMBox = findViewById(R.id.btn_to_imbox);
        btnIMBox.setOnClickListener(this);
        mTbt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (mBtnGpio1.getText().toString().contains("设置GPIO")) {
                        Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFlag == 0) {
                        if (isChecked) {
                            deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                            mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                        } else {
                            deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                            mTbt1.setBackgroundResource(R.drawable.btn_press_off_false);

                        }
                    } else {
                        if (isChecked) {
                            deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio1.getText().toString()));
                            mTbt1.setBackgroundResource(R.drawable.btn_press_on_true);

                        } else {
                            deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio1.getText().toString()));
                            mTbt1.setBackgroundResource(R.drawable.btn_press_off_false);

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGpio2 = findViewById(R.id.btn_gpio2);
        mBtnGpio2.setOnClickListener(this);
        mTbt2 = findViewById(R.id.tbt2);
        mTbt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (mBtnGpio2.getText().toString().contains("设置GPIO")) {
                        Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFlag == 0) {
                        if (isChecked) {
                            deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                            mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);
                        } else {
                            deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                            mTbt2.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    } else {
                        if (isChecked) {
                            deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio2.getText().toString()));
                            mTbt2.setBackgroundResource(R.drawable.btn_press_on_true);
                        } else {
                            deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio2.getText().toString()));
                            mTbt2.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGpio3 = findViewById(R.id.btn_gpio3);
        mBtnGpio3.setOnClickListener(this);
        mTbt3 = findViewById(R.id.tbt3);
        mTbt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (mBtnGpio3.getText().toString().contains("设置GPIO")) {
                        Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFlag == 0) {
                        if (isChecked) {
                            mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        } else {
                            deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                            mTbt3.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    } else {
                        if (isChecked) {
                            mTbt3.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio3.getText().toString()));
                        } else {
                            deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio3.getText().toString()));
                            mTbt3.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGpio4 = findViewById(R.id.btn_gpio4);
        mBtnGpio4.setOnClickListener(this);
        mTbt4 = findViewById(R.id.tbt4);
        mTbt4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (mBtnGpio4.getText().toString().contains("设置GPIO")) {
                        Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFlag == 0) {
                        if (isChecked) {
                            mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        } else {
                            deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                            mTbt4.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    } else {
                        if (isChecked) {
                            mTbt4.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio4.getText().toString()));
                        } else {
                            deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio4.getText().toString()));
                            mTbt4.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnGpio5 = findViewById(R.id.btn_gpio5);
        mBtnGpio5.setOnClickListener(this);
        mTbt5 = findViewById(R.id.tbt5);
        mTbt5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (mBtnGpio5.getText().toString().contains("设置GPIO")) {
                        Toast.makeText(MainActivity.this, "请设置GPIO值！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFlag == 0) {
                        if (isChecked) {
                            mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.MainPowerOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        } else {
                            deviceControl.MainPowerOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                            mTbt5.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    } else {
                        if (isChecked) {
                            mTbt5.setBackgroundResource(R.drawable.btn_press_on_true);
                            deviceControl.newSetGpioOn(Integer.parseInt(mBtnGpio5.getText().toString()));
                        } else {
                            deviceControl.newSetGpioOff(Integer.parseInt(mBtnGpio5.getText().toString()));
                            mTbt5.setBackgroundResource(R.drawable.btn_press_off_false);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mBtnNewGpio1 = findViewById(R.id.btn_new_gpio1);
        mBtnNewGpio1.setOnClickListener(this);
        mNewBtn1 = findViewById(R.id.new_btn1);

        mBtnSetGpio1 = findViewById(R.id.btn_set_gpio1);
        mBtnSetGpio1.setOnClickListener(this);
        mTbtW1 = findViewById(R.id.tbt_w1);
        mTbtW1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        deviceControl2.ExpandPowerOn(Integer.parseInt(mBtnSetGpio1.getText().toString()));
                        mTbtW1.setBackgroundResource(R.drawable.btn_press_on_true);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        deviceControl2.ExpandPowerOff(Integer.parseInt(mBtnSetGpio1.getText().toString()));
                        mTbtW1.setBackgroundResource(R.drawable.btn_press_off_false);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mBtnSetGpio2 = findViewById(R.id.btn_set_gpio2);
        mBtnSetGpio2.setOnClickListener(this);
        mTbtW2 = findViewById(R.id.tbt_w2);
        mTbtW2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        deviceControl2.ExpandPowerOn(Integer.parseInt(mBtnSetGpio2.getText().toString()));
                        mTbtW2.setBackgroundResource(R.drawable.btn_press_on_true);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        deviceControl2.ExpandPowerOff(Integer.parseInt(mBtnSetGpio2.getText().toString()));
                        mTbtW2.setBackgroundResource(R.drawable.btn_press_off_false);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mBtnSetGpio3 = findViewById(R.id.btn_set_gpio3);
        mBtnSetGpio3.setOnClickListener(this);
        mTbtW3 = findViewById(R.id.tbt_w3);

        mTbtW3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        deviceControl2.ExpandPowerOn(Integer.parseInt(mBtnSetGpio3.getText().toString()));
                        mTbtW3.setBackgroundResource(R.drawable.btn_press_on_true);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        deviceControl2.ExpandPowerOff(Integer.parseInt(mBtnSetGpio3.getText().toString()));
                        mTbtW3.setBackgroundResource(R.drawable.btn_press_off_false);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mBtnSetGpio4 = findViewById(R.id.btn_set_gpio4);
        mBtnSetGpio4.setOnClickListener(this);
        mTbtW4 = findViewById(R.id.tbt_w4);

        mTbtW4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    try {
                        deviceControl2.ExpandPowerOn(Integer.parseInt(mBtnSetGpio4.getText().toString()));
                        mTbtW4.setBackgroundResource(R.drawable.btn_press_on_true);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        deviceControl2.ExpandPowerOff(Integer.parseInt(mBtnSetGpio4.getText().toString()));
                        mTbtW4.setBackgroundResource(R.drawable.btn_press_off_false);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mSSwitch = findViewById(R.id.s_switch);
        mSSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isFlag = 1;
                } else {
                    isFlag = 0;
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_gpio1:
                setdatas(mBtnGpio1, mTbt1);
                break;
            case R.id.btn_gpio2:
                setdatas(mBtnGpio2, mTbt2);
                break;
            case R.id.btn_gpio3:
                setdatas(mBtnGpio3, mTbt3);
                break;
            case R.id.btn_gpio4:
                setdatas(mBtnGpio4, mTbt4);
                break;
            case R.id.btn_gpio5:
                setdatas(mBtnGpio5, mTbt5);
                break;
            case R.id.btn_new_gpio1:
                break;
            case R.id.btn_set_gpio1:
                setoutdatas(mBtnSetGpio1, mTbtW1);
                break;
            case R.id.btn_set_gpio2:
                setoutdatas(mBtnSetGpio2, mTbtW2);
                break;
            case R.id.btn_set_gpio3:
                setoutdatas(mBtnSetGpio3, mTbtW3);
                break;
            case R.id.btn_set_gpio4:
                setoutdatas(mBtnSetGpio4, mTbtW4);
                break;
            case R.id.btn_to_imbox:
                startActivity(new Intent(this, IMBoxGpioAct.class));
                break;
        }
    }

}
