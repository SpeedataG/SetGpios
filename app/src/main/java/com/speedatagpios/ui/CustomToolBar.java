package com.speedatagpios.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.speedatagpios.R;


/**
 * @author xuyan  title栏
 */
public class CustomToolBar extends RelativeLayout {

    private TextView tvCamera;
    private ImageView imageSetting;

    public CustomToolBar(Context context) {
        super(context);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化组件
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        tvCamera = findViewById(R.id.title_name);
        imageSetting = findViewById(R.id.title_settings);

    }

}