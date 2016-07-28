package com.mozhuowen.rxandroid.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.mozhuowen.R;


/**
 * Created by Awen on 16/2/18.
 * Email:mozhuowen@gmail.com
 */
public class SimpleAlterDialog extends DialogFragment {

    private static View.OnClickListener onPositiveClick;
    private static View.OnClickListener onNegativeClick;
    private static String titlestr;

    public static SimpleAlterDialog newInstance(int title,View.OnClickListener oklistener,View.OnClickListener cancelistener) {
        SimpleAlterDialog frag = new SimpleAlterDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        onPositiveClick = oklistener;
        onNegativeClick = cancelistener;
        frag.setArguments(args);
        return frag;
    }

    public static SimpleAlterDialog newInstance(String titleStr,View.OnClickListener oklistener,View.OnClickListener cancelistener) {
        SimpleAlterDialog frag = new SimpleAlterDialog();
        Bundle args = new Bundle();
        args.putInt("title", 0);
        onPositiveClick = oklistener;
        onNegativeClick = cancelistener;
        titlestr = titleStr;
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        if (title > 0)
            titlestr = getActivity().getString(title);

        return new AlertDialog.Builder(getActivity())
                .setTitle(titlestr)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (onPositiveClick != null)
                                    onPositiveClick.onClick(null);
                                else
                                    dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (onNegativeClick != null)
                                    onNegativeClick.onClick(null);
                                else
                                    dismiss();
                            }
                        }
                )
                .create();
    }
}
