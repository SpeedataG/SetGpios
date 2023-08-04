package com.speedatagpios.ui;

import static com.speedatagpios.ScanConstant.SETGPIO_PATH;

import android.annotation.SuppressLint;
import android.os.SystemProperties;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.speedatagpios.AppSet;
import com.speedatagpios.R;
import com.speedatagpios.WeightEvent;
import com.speedatagpios.utils.Logcat;

import org.greenrobot.eventbus.EventBus;


/**
 * @author xuyan
 */
public class SetWindows extends PopupWindow {


    @SuppressLint("NewApi")
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

        mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
        mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
        mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
        mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
        mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
        mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
        mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

        mOne.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mTwo.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "EXPAND");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mThree.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "EXPAND2");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mFour.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "NEW_MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mFive.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "NEW_MAIN_FG");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });


        mSix.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "NEW_MAIN_SC");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });

        mSeven.setOnClickListener(v -> {

            mOne.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mTwo.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mThree.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFour.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mFive.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSix.setTextColor(AppSet.getInstance().getResources().getColor(R.color.color_tab_choose_un, null));
            mSeven.setTextColor(AppSet.getInstance().getResources().getColor(R.color.colorBlueMenu, null));

            //写一个系统属性
            SystemProperties.set(SETGPIO_PATH, "ZHANRUI_MAIN");
            EventBus.getDefault().postSticky(new WeightEvent(""));
            dismiss();
        });
    }


}
