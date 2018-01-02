package com.jiekai.wzglkg.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by LaoWu on 2017/12/6.
 * 输入用户名和密码的工具类
 */

public class InputPasswordUtils implements TextWatcher, View.OnClickListener{
    private static final int MAX = 20;

    private String editTextCache = "";
    private EditText editText;
    private ImageView cancleImage;

    public InputPasswordUtils(EditText editText, ImageView cancleImage) {
        this.editText = editText;
        this.cancleImage = cancleImage;
        this.editText.addTextChangedListener(this);
        this.cancleImage.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0) {
            cancleImage.setVisibility(View.VISIBLE);
            if (Check.isExChar(s.toString())) {
                s = editTextCache;
                editText.setText(s);
                editText.setSelection(s.length());
                Toast.makeText(editText.getContext(), "输入不能含有特殊字符", Toast.LENGTH_SHORT).show();
            }
            if (s.length() > MAX) {
                s = editTextCache;
                editText.setText(s);
                editText.setSelection(s.length());
                Toast.makeText(editText.getContext(), "输入不能超过" + MAX + "字符", Toast.LENGTH_SHORT).show();
            }
        } else {
            cancleImage.setVisibility(View.GONE);
        }
        editTextCache = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (v == cancleImage) {
            editText.setText("");
        }
    }
}
