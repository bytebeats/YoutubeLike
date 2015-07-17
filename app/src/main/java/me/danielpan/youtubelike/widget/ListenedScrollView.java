package me.danielpan.youtubelike.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by it-od-m-2572 on 15/6/10.
 */
public class ListenedScrollView extends ScrollView {
    private OnScrollChangeListener mListener;

    public static interface OnScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public OnScrollChangeListener getOnScrollChangeListener() {
        return mListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener mListener) {
        this.mListener = mListener;
    }

    public ListenedScrollView(Context context) {
        super(context);
    }

    public ListenedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListenedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mListener != null) {
            mListener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
