package com.goach.tabdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goach.tabdemo.R;

/**
 * Created by maxpengli
 *
 * 指示器为一张图片
 */

public class IndicationImageViewTabLayout extends LinearLayout {
    //指示器
    private int mSelectedIndicatorHeight;
    private Paint mSelectedIndicatorPaint;
    private int mSelectedPosition = -1;
    private float mSelectionOffset;
    private int mIndicatorLeft = -1;
    private int mIndicatorRight = -1;
    private Bitmap mBitmap;
    private Rect mSrcRect; //第一个矩形代表需要绘制的bitmap区域
    private Rect mDestRect;//第二个矩形代表bitmap绘制到什么位置




    public IndicationImageViewTabLayout(Context context) {
        this(context,null);
    }

    public IndicationImageViewTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicationImageViewTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        mBitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.tab_index)).getBitmap();

        mSelectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectedIndicatorPaint.setFilterBitmap(true);
        mSelectedIndicatorPaint.setDither(true);


        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());


    }

    //设置颜色无效
    public void setSelectedIndicatorColor(int color) {
//        if (mSelectedIndicatorPaint.getColor() != color) {
//            mSelectedIndicatorPaint.setColor(color);
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
    }

    //表示图片距离底部到大小
    public void setSelectedIndicatorHeight(int height) {
        if (mSelectedIndicatorHeight != height) {
            mSelectedIndicatorHeight = height;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * 定位指示器
     * @param position
     * @param positionOffset
     */
    public void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        updateIndicatorPosition();
    }
    private void updateIndicatorPosition() {
        final View selectedTitle = getChildAt(mSelectedPosition);
        int left, right;

        if (selectedTitle != null && selectedTitle.getWidth() > 0) {
            int selectTextPadding = (int) ((selectedTitle.getWidth()-measureTextLength(selectedTitle))/2+0.5f);
            left = selectedTitle.getLeft()+selectTextPadding;
            right = selectedTitle.getRight()-selectTextPadding;

            if (mSelectionOffset > 0f && mSelectedPosition < getChildCount() - 1) {
                View nextTitle = getChildAt(mSelectedPosition + 1);
                int textPadding = (int) ((nextTitle.getWidth()-measureTextLength(nextTitle))/2+0.5f);
                int moveLeft = nextTitle.getLeft()+textPadding-left;
                int moveRight = nextTitle.getRight()-textPadding-right;
                left = (int) (left + moveLeft * mSelectionOffset);
                right = (int) (right + moveRight * mSelectionOffset);
            }
        } else {
            left = right = -1;
        }
        setIndicatorPosition(left, right);
    }

    private void setIndicatorPosition(int left, int right) {
        if (left != mIndicatorLeft || right != mIndicatorRight) {
            mIndicatorLeft = left;
            mIndicatorRight = right;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    private float measureTextLength(View measureView){
        if(measureView instanceof TextView){
            TextView textView = ((TextView)measureView);
            String text =textView .getText().toString();
            return textView.getPaint().measureText(text);
        }
        return 0;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
            mDestRect = new Rect(mIndicatorLeft, getHeight()-mBitmap.getHeight()-mSelectedIndicatorHeight, mIndicatorRight, getHeight()-mSelectedIndicatorHeight);
            canvas.drawBitmap(mBitmap,mSrcRect,mDestRect,mSelectedIndicatorPaint);
        }
    }
}
