package com.jiekai.wzglkg.ui.update;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;

/**
 * Created by LaoWu on 2018/3/13.
 * 当有更新的时候弹出框
 */

public class UpdateHaveUpdateDialog extends BaseDialogFragment {
    private String updataInfo;
    private HaveUpdateInterface updateInterface;

    public UpdateHaveUpdateDialog newInstance() {
        UpdateHaveUpdateDialog updateHaveUpdateDialog = new UpdateHaveUpdateDialog();
        return updateHaveUpdateDialog;
    }

    public static UpdateHaveUpdateDialog newInstance(boolean mIsOutCanback, boolean mIsKeyCanback, String updataInfo) {
        UpdateHaveUpdateDialog updateHaveUpdateDialog = new UpdateHaveUpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("mIsOutCanback", mIsOutCanback);
        bundle.putBoolean("mIsKeyCanback", mIsKeyCanback);
        bundle.putString("updataInfo", updataInfo);
        updateHaveUpdateDialog.setArguments(bundle);
        return updateHaveUpdateDialog;
    }

    public void setUpdateInterface(HaveUpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mIsOutCanback = bundle.getBoolean("mIsOutCanback", false);
        mIsKeyCanback = bundle.getBoolean("mIsKeyCanback", false);
        updataInfo = bundle.getString("updataInfo", "");
    }

    @Override
    protected View bindView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.update_dialog_have_new_apk, container);
        view.findViewById(R.id.enter).setOnClickListener(lisen);
        view.findViewById(R.id.cancle).setOnClickListener(lisen);
        ((TextView)view.findViewById(R.id.content)).setText(updataInfo);
        return view;
    }

    private View.OnClickListener lisen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.enter:
                    if (updateInterface != null) {
                        updateInterface.enterDownLoad();
                    }
                    hideDialog();
                    break;
                case R.id.cancle:
                    if (updateInterface != null) {
                        updateInterface.cancleDownLoad();
                    }
                    hideDialog();
                    break;
            }
        }
    };
}
