package com.speedatagpios.ui;

import android.os.SystemProperties;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.speedatagpios.R;
import com.speedatagpios.WeightEvent;
import com.speedatagpios.utils.Logcat;

import org.greenrobot.eventbus.EventBus;

import static com.speedatagpios.ScanConstant.SETGPIO_PATH;


/**
 * @author xuyan
 */
public class SetWindows extends PopupWindow {


    public SetWindows(AppCompatActivity mContext) {

        Logcat.d("open popwindows");

        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        setWidth(dm.widthPixels);
        setHeight(dm.heightPixels);

        View popupView = LayoutInflater.from(mContext).inflate(R.layout.view_end_dialog_layout,
                new LinearLayout(mContext), false);

        setContentView(popupView);

        TextView mOne = popupView.findViewById(R.id.balance_one);
        TextView mTwo = popupView.findViewById(R.id.balance_two);
        TextView mThree = popupView.findViewById(R.id.balance_three);
        TextView mFour = popupView.findViewById(R.id.balance_four);
        TextView mFive = popupView.findViewById(R.id.balance_five);
        TextView mSix = popupView.findViewById(R.id.balance_six);
        TextView mSeven = popupView.findViewById(R.id.balance_seven);
        TextView mEight = popupView.findViewById(R.id.balance_eight);

        mOne.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mTwo.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "EXPAND");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mThree.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "MAIN_AND_EXPAND");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mFour.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "NEW_MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mFive.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "NEW_MAIN_FG");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mSix.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "EXPAND2");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mSeven.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "MAIN_AND_EXPAND2");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mEight.setOnClickListener(v -> {
            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "GAOTONG_MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });


    }


}
