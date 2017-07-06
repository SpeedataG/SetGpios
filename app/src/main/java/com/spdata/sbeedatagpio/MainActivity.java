package com.spdata.sbeedatagpio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private static final String MAIN_GPIO = "sys/class/misc/mtgpio/pin";
    private static final String OUT_GPIO = "/sys/class/misc/aw9523/gpio";
    @ViewById
    Button btn_gpio1;
    @ViewById
    ToggleButton tbt1;
    @ViewById
    Button btn_gpio2;
    @ViewById
    ToggleButton tbt2;
    @ViewById
    Button btn_gpio3;
    @ViewById
    ToggleButton tbt3;
    @ViewById
    Button btn_gpio4;
    @ViewById
    ToggleButton tbt4;
    @ViewById
    Button btn_gpio5;
    @ViewById
    ToggleButton tbt5;
    @ViewById
    Button btn_set_gpio1;
    @ViewById
    ToggleButton tbt_w1;
    @ViewById
    Button btn_set_gpio2;
    @ViewById
    ToggleButton tbt_w2;
    @ViewById
    Button btn_set_gpio3;
    @ViewById
    ToggleButton tbt_w3;
    @ViewById
    Button btn_set_gpio4;
    @ViewById
    ToggleButton tbt_w4;
    @ViewById
    LinearLayout activityMain;
    private String outgpioinfo;

    @Click
    void btn_gpio1() {
//        Toast.makeText(this, "ssss", Toast.LENGTH_LONG).show();
        setdatas(btn_gpio1, tbt1);
    }

    @Click
    void btn_gpio2() {
        setdatas(btn_gpio2, tbt2);
    }

    @Click
    void btn_gpio3() {
        setdatas(btn_gpio3, tbt3);
    }

    @Click
    void btn_gpio4() {
        setdatas(btn_gpio4, tbt4);
    }

    @Click
    void btn_gpio5() {
        setdatas(btn_gpio5, tbt5);
    }

    @Click
    void btn_set_gpio1() {
        setoutdatas(btn_set_gpio1, tbt_w1);
//        int in= Integer.parseInt(btn_set_gpio1.getText().toString());
//        if (in<0||in>7){
//            Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！！", Toast.LENGTH_LONG).show();
//        }
    }

    @Click
    void btn_set_gpio2() {
        setoutdatas(btn_set_gpio2, tbt_w2);
    }

    @Click
    void btn_set_gpio3() {
        setoutdatas(btn_set_gpio3, tbt_w3);
    }

    @Click
    void btn_set_gpio4() {
        setoutdatas(btn_set_gpio4, tbt_w4);
    }

    @Click
    void tbt1() {
        if (tbt1.isChecked()) {
            deviceControl.PowerOnDevice(btn_gpio1.getText().toString());
        } else {
            deviceControl.PowerOffDevice(btn_gpio1.getText().toString());
        }
    }

    @Click
    void tbt2() {
        if (tbt2.isChecked()) {
            deviceControl.PowerOnDevice(btn_gpio2.getText().toString());
        } else {
            deviceControl.PowerOffDevice(btn_gpio2.getText().toString());
        }
    }

    @Click
    void tbt3() {
        if (tbt3.isChecked()) {
            deviceControl.PowerOnDevice(btn_gpio3.getText().toString());
        } else {
            deviceControl.PowerOffDevice(btn_gpio3.getText().toString());
        }
    }

    @Click
    void tbt4() {
        if (tbt4.isChecked()) {
            deviceControl.PowerOnDevice(btn_gpio4.getText().toString());
        } else {
            deviceControl.PowerOffDevice(btn_gpio4.getText().toString());
        }
    }

    @Click
    void tbt5() {
        if (tbt5.isChecked()) {
            deviceControl.PowerOnDevice(btn_gpio5.getText().toString());
        } else {
            deviceControl.PowerOffDevice(btn_gpio5.getText().toString());
        }
    }

    @Click
    void tbt_w1() {
        if (tbt_w1.isChecked()) {
            try {
                deviceControl2.PowerOnDeviceOut(btn_set_gpio1.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                deviceControl2.PowerOffDeviceOut(btn_set_gpio1.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Click
    void tbt_w2() {
        if (tbt_w2.isChecked()) {
            try {
                deviceControl2.PowerOnDeviceOut(btn_set_gpio2.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                deviceControl2.PowerOffDeviceOut(btn_set_gpio2.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Click
    void tbt_w3() {
        if (tbt_w3.isChecked()) {
            try {
                deviceControl2.PowerOnDeviceOut(btn_set_gpio3.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                deviceControl2.PowerOffDeviceOut(btn_set_gpio3.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Click
    void tbt_w4() {
        if (tbt_w4.isChecked()) {
            try {
                deviceControl2.PowerOnDeviceOut(btn_set_gpio4.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                deviceControl2.PowerOffDeviceOut(btn_set_gpio4.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "这是拓展GPIO 请连接拓展设备！", Toast.LENGTH_LONG).show();
            }
        }
    }

    private DeviceControl deviceControl;
    private DeviceControl deviceControl2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public String OutGPIO() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/sys/class/misc/aw9523/gpio"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        }
        return sb.toString();
    }

    public List MainGPIO() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("sys/class/misc/mtgpio/pin"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List lists = new ArrayList();
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
        return lists;
    }

    private void init() {
        try {
            deviceControl = new DeviceControl("sys/class/misc/mtgpio/pin");
            deviceControl2 = new DeviceControl("/sys/class/misc/aw9523/gpio");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setdatas(final Button btn, final ToggleButton tbtn) {
        final EditText edvCount = new EditText(MainActivity.this);
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(MainActivity.this).setTitle("设置GPIO").setView(edvCount)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edvCount.getText().toString();
                        if (!text.equals("")) {
                            btn.setText(text);
                            btn.setTextColor(Color.RED);
                            List list = MainGPIO();
                            for (int i = 1; i < list.size(); i++) {
                                String lists = list.get(i).toString();
                                String gpio = lists.substring(0, lists.indexOf(":"));
                                String upordown = lists.substring(7, 8);
                                if (text.equals(gpio.trim())) {//gpio去空格
                                    if (upordown.equals("1")) {
                                        tbtn.setChecked(true);
                                        return;
                                    } else if (upordown.equals("0")) {
                                        tbtn.setChecked(false);
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
        edvCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(MainActivity.this).setTitle("设置GPIO").setView(edvCount)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = edvCount.getText().toString();
                        if (!text.equals("")) {
                            btn.setText(text);
                            btn.setTextColor(Color.RED);
                            String[] split = new String[0];
                            try {
                                split = OutGPIO().split(" ");
                                if (split[1].equals("0xEA")) {
                                    Toast.makeText(MainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
                                    return;
                                }else {
                                    String bb = hexString2binaryString(split[1]);
                                    List outlist = new ArrayList();
                                    if (Integer.parseInt(text) <= 7) {
//                                        for (int i = bb.length(); i>=0 ; i--) {
//                                            outlist.add(bb.getBytes());
//                                        }
//                                        for (int i = 0; i < outlist.size(); i++) {
//                                            if (Integer.parseInt(text) == i) {
//                                                if (outlist.get(i).equals("1")) {
//                                                    tbtn.setChecked(true);
//                                                    return;
//                                                } else if (outlist.get(i).equals("0")) {
//                                                    tbtn.setChecked(false);
//                                                    return;
//                                                }
//                                            }
//                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                if (OutGPIO().equals("234")) {
                                    Toast.makeText(MainActivity.this, "请连接拓展设备,在设置GPIO！", Toast.LENGTH_SHORT).show();
                                    return;
                                }else {
                                  String  gpio=  Integer.toBinaryString(Integer.parseInt(OutGPIO()));
                                    List outlist = Arrays.asList(gpio);
                                    if (Integer.parseInt(text) <= 7) {
//                                        for (int i = outlist.size(); i >=0 ; i--) {
//                                            if (Integer.parseInt(text) == i) {
//                                                if (outlist.get(i).equals("1")) {
//                                                    tbtn.setChecked(true);
//                                                    return;
//                                                } else if (outlist.get(i).equals("0")) {
//                                                    tbtn.setChecked(false);
//                                                    return;
//                                                }
//                                            }
//                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "请设置正确的GPIO(0~7)", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "请设置GPIO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    /*
    16进制转2二进制
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString
                    .substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }
}
