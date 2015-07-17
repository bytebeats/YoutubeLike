package me.danielpan.youtubelike.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import me.danielpan.youtubelike.R;

/**
 * Created by it-od-m-2572 on 15/7/1.
 */
public class DragScaleLayout extends LinearLayout {

    private OnHeaderSizeChangeListener mSizeListener;

    private final ViewDragHelper mDragHelper;
    private View mHeaderView;
    private View mContentView;

    private float mInitMotionX;
    private float mInitMotionY;

    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public DragScaleLayout(Context context) {
        this(context, null);
    }

    public DragScaleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragScaleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0F, new DragCallback());
    }

    public void setOnHeaderSizeChangeListener(OnHeaderSizeChangeListener listener) {
        this.mSizeListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0), resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - mHeaderView.getHeight();
        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mContentView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.header_view);
        mContentView = findViewById(R.id.content_view);
    }

    public void maximize() {
        smoothSlideTo(0.0F);
    }

    boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);
        if (mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action != MotionEvent.ACTION_DOWN) {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptCap = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitMotionX = x;
                mInitMotionY = y;
                interceptCap = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                final float adx = Math.abs(x - mInitMotionX);
                final float ady = Math.abs(y - mInitMotionY);
                final int slop = mDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mDragHelper.cancel();
                    return false;
                }
                break;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptCap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();
        boolean isHeaderUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mInitMotionY = y;
                mInitMotionX = x;
                break;
            case MotionEvent.ACTION_UP:
                final float dx = x - mInitMotionX;
                final float dy = y - mInitMotionY;
                final int slop = mDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop && isHeaderUnder) {
                    if (mDragOffset == 0) {
                        smoothSlideTo(1F);
                    } else {
                        smoothSlideTo(0F);
                    }
                }
                break;
        }
        return isHeaderUnder && (isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mContentView, (int) x, (int) y));
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        getLocationOnScreen(parentLocation);
        final int screenX = parentLocation[0] + x;
        final int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX <= viewLocation[1] + view.getWidth()
                && screenY >= viewLocation[0] && screenY <= viewLocation[1] + view.getHeight();
    }

    private class DragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;
            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);
            mContentView.setAlpha(1 - mDragOffset);
            requestLayout();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5F)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
            invalidate();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }
    }

    public interface OnHeaderSizeChangeListener {
        void onSizeChanged(int width, int height);
    }
}
