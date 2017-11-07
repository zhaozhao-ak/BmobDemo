package com.zz.ak.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.zz.ak.demo.tool.LoadingDialog;

/**
 * Created by Administrator on 2017/11/8.
 */

public class BaseFragment extends Fragment {
    public static LoadingDialog loadingdialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void showloading() {
        loadingdialog = new LoadingDialog(this.getContext());
        loadingdialog.setCanceledOnTouchOutside(false);
        loadingdialog.setCancelable(false);
        loadingdialog.show();
    }

    public void closeloading() {
        if (loadingdialog!=null){
            loadingdialog.dismiss();
        }
    }

    Toast mToast;
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(this.getContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }


}
