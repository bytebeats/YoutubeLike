package me.danielpan.youtubelike.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.danielpan.youtubelike.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFrag extends Fragment {
    private String mTitle;
    private String mMessage;
    private String pstvAction;
    private String ngtvAction;
    private String ntrlAction;
    private OnPositiveBtnClickListener pstvListener;
    private OnNegativeBtnClickListener ngtvListener;
    private OnNeutralBtnClickListener ntrlListener;

    public DialogFrag() {
        // Required empty public constructor
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public void setPstvListener(OnPositiveBtnClickListener pstvListener) {
        this.pstvListener = pstvListener;
    }

    public void setNgtvListener(OnNegativeBtnClickListener ngtvListener) {
        this.ngtvListener = ngtvListener;
    }

    public void setNtrlListener(OnNeutralBtnClickListener ntrlListener) {
        this.ntrlListener = ntrlListener;
    }

    public void setPstvAction(String pstvAction) {
        this.pstvAction = pstvAction;
    }

    public void setNgtvAction(String ngtvAction) {
        this.ngtvAction = ngtvAction;
    }

    public void setNtrlAction(String ntrlAction) {
        this.ntrlAction = ntrlAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    public interface OnPositiveBtnClickListener extends View.OnClickListener {
    }

    public interface OnNegativeBtnClickListener extends View.OnClickListener {
    }

    public interface OnNeutralBtnClickListener extends View.OnClickListener {
    }


}
