package com.jiekai.wzglkg.ui.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiekai.wzglkg.R;

import java.text.NumberFormat;

/**
 * Created by LaoWu on 2018/3/13.
 */

public class UpdateLoadingDialog extends BaseDialogFragment {
    private ProgressBar progressBar;
    private TextView currentSize;
    private TextView allSize;

    public static UpdateLoadingDialog newInstance(boolean mIsOutCanback, boolean mIsKeyCanback) {
        Bundle args = new Bundle();
        args.putBoolean("mIsOutCanback", mIsOutCanback);
        args.putBoolean("mIsKeyCanback", mIsKeyCanback);
        UpdateLoadingDialog fragment = new UpdateLoadingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mIsKeyCanback = bundle.getBoolean("mIsKeyCanback", false);
        mIsOutCanback = bundle.getBoolean("mIsOutCanback", false);
    }

    @Override
    protected View bindView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.update_dialog_loading, container);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        allSize = (TextView) view.findViewById(R.id.allsize);
        currentSize = (TextView) view.findViewById(R.id.currentsize);

        currentSize.setText(String.format(getResources().getString(R.string.currentsize), "0"));
        allSize.setText(String.format(getResources().getString(R.string.allsize), "0"));
        return view;
    }

    public void setProgressBar(long allLenght, long loaddingSize, int progress) {
        progressBar.setProgress(progress);
        currentSize.setText(String.format(getResources().getString(R.string.currentsize), byteToMB(loaddingSize)));
        allSize.setText(String.format(getResources().getString(R.string.allsize), byteToMB(allLenght)));
    }

    /**
     * 1MB等于1024kb 1kb等于1024字节
     *
     * @return
     */
    private String byteToMB(long length) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat.format(Float.valueOf(length) / 1024f / 1024f);
    }
}
