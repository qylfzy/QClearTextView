package com.qiyou.qtextview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

/**
 * Created by qiyou on 2018/8/17.
 */

public class QClearTextView extends AppCompatEditText implements TextWatcher {

    private static final String TAG = "QClearTextView";
    private boolean isShowClear = false;//是否显示删除按钮
    private Drawable clearDrawable = null;//删除按钮
    private static final int MAX_LINES = 1;

    public QClearTextView(Context context) {
        this(context, null);
        init();
    }

    public QClearTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public QClearTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //获取右侧删除按钮 如果用户没设置给一个默认的按钮 getCompoundDrawables()获取四周图标数组 0-3 分别代表 左上右下
        clearDrawable = this.getCompoundDrawables()[2];
        if (clearDrawable == null) {
            clearDrawable = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
            //四个参数表示将drawable 绘制在那个矩形区域内
            clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
        }

        setGravity(Gravity.LEFT | Gravity.CENTER);
        setMaxLines(MAX_LINES);
        setPadding(5, 0, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clearDrawable != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //leftX 点击的位置要大于 控件本身的长度 - 删除按钮的长度 - 控件的getPaddingRight()
                boolean leftX = event.getX() > getWidth() - clearDrawable.getIntrinsicWidth() - getPaddingRight();
                //rightX 点击位置要小于 控件的本身长度 - 控件getPaddingRight()
                boolean rightX = event.getX() < getWidth() - getPaddingRight();
                //这个范围之内触发删除按钮的点击事件
                if (leftX && rightX) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(charSequence)) {
            if (isShowClear) {
                isShowClear = !isShowClear;
                //设置控件的四周的图标 依次是左上右下
                this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
            }
        } else {
            if (!isShowClear) {
                isShowClear = !isShowClear;
                this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearDrawable, null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
