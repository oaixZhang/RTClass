package com.xiao.rtclassteacher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xiao.rtclassteacher.R;

public class ImageAndText extends View {
    public static int FITXY = 0;
    public static int CENTER = 1;

    private Bitmap mImage;
    private int mTextSize;
    private int mTextColor;
    private String mText;
    private int mScaleType;

    private Rect rect;
    private Paint mPaint;
    private Rect mTextBound;

    private int mWidth, mHeight;

    public ImageAndText(Context context) {
        this(context, null);
    }

    public ImageAndText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageAndText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ImageAndText,
                defStyleAttr, 0);
        int conut = ta.getIndexCount();
        for (int i = 0; i < conut; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ImageAndText_image:
                    mImage = BitmapFactory.decodeResource(getResources(), ta.getResourceId(attr, 0));
                    break;
                case R.styleable.ImageAndText_imageScaleType:
                    mScaleType = ta.getInt(attr, 0);
                    break;
                case R.styleable.ImageAndText_imageText:
                    mText = ta.getString(attr);
                    break;
                case R.styleable.ImageAndText_imageTextSize:
                    mTextSize = ta.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ImageAndText_imageTextColor:
                    mTextColor = ta.getColor(attr, Color.BLACK);
                    break;
            }
        }
        ta.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //width
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            //match_parent
            mWidth = specSize;
        } else {
            int imageWidth = getPaddingLeft() + mImage.getWidth() + getPaddingRight();
            int textWidth = getPaddingLeft() + mTextBound.width() + getPaddingRight();
            int width = Math.max(imageWidth, textWidth);
            mWidth = Math.min(width, specSize);
        }
        //height
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY)
        // match_parent , accurate
        {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (specMode == MeasureSpec.AT_MOST)// wrap_content
            {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //border
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth) {
            TextPaint textPaint = new TextPaint(mPaint);
            String s = TextUtils.ellipsize(mText, textPaint,
                    (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(s, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mText, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        rect.bottom -= mTextBound.height();

        if (mScaleType == FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else {
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }

    }
}
