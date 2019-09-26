package com.example.myapplication.calendarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 范围选择月视图 Created by huanghaibin on 2018/9/13.
 */

public class CustomRangeMonthView extends RangeMonthView {

  private int mRadius;
  /*选中背景圆角角度*/
  private float mSelectRadius = 8L;
  private String TAG = "YANG";
  //日期中间连接的画笔
  private Paint mMiddleBackgroundPaint = new Paint();
  //开始结束文字画笔
  private Paint mTextPaint = new Paint();
  private Context mContext;

  public CustomRangeMonthView(Context context) {
    super(context);
    mContext = context;
  }


  @Override
  protected void onPreviewHook() {
    mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
    mSchemePaint.setStyle(Paint.Style.STROKE);
    mMiddleBackgroundPaint.setAntiAlias(true);
    mMiddleBackgroundPaint.setStyle(Paint.Style.FILL);
    mMiddleBackgroundPaint.setStrokeWidth(2);
    mMiddleBackgroundPaint.setColor(Color.parseColor("#D4ECF7"));

    mTextPaint.setAntiAlias(true);
    mTextPaint.setTextAlign(Paint.Align.CENTER);
    mTextPaint.setColor(Color.parseColor("#FFFFFF"));
    mTextPaint.setFakeBoldText(true);
    mTextPaint.setTextSize(CalendarUtil.dipToPx(mContext, 10));
  }

  @Override
  protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y,
      boolean hasScheme,
      boolean isSelectedPre, boolean isSelectedNext) {
    int cx = x + mItemWidth / 2;
    int cy = y + mItemHeight / 2;

    //上一个日期被选中
    if (isSelectedPre) {
      if (isSelectedNext) {
        //中间的日期
        canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mMiddleBackgroundPaint);
      } else {//最后日期
        canvas.drawRoundRect(cx - (mItemWidth / 2), cy - mRadius, cx + (mItemWidth / 2), cy + mRadius,
                mSelectRadius, mSelectRadius, mSelectedPaint);
        //解决连接处圆角和矩形衔接问题
        canvas.drawRect(cx - (mItemWidth / 2), cy - mRadius, cx, cy + mRadius,
            mSelectedPaint);
      }
    }
    //上一个日期未被选中,第一个
    else {
      canvas.drawRoundRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectRadius,
          mSelectRadius, mSelectedPaint);
      //解决连接处圆角和矩形衔接问题
      if (isSelectedNext) {
        canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
      }
    }

    return false;
  }

  @Override
  protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {

  }

  @Override
  protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
      boolean isSelected,boolean isSelectedPre, boolean isSelectedNext) {
    float baselineY = mTextBaseLine + y;
    int cx = x + mItemWidth / 2;
    int cy = y + mItemHeight/2;
    //是否在日期范围内
    boolean isInRange = isInRange(calendar);
    //是否设置了拦截器
    boolean isEnable = !onCalendarIntercept(calendar);

    if (isSelected) {
      //第一个
      if (!isSelectedPre){
        canvas.drawText(String.valueOf(calendar.getDay()),
            cx,
            baselineY-(mItemHeight/6),
            mSelectTextPaint);
        canvas.drawText("起始",cx,baselineY+(mItemHeight/6),mTextPaint);

      }else if (!isSelectedNext){
        //最后一个
        canvas.drawText(String.valueOf(calendar.getDay()),
            cx,
            baselineY-(mItemHeight/6),
            mSelectTextPaint);
        canvas.drawText("结束",cx,baselineY+(mItemHeight/6),mTextPaint);
      }else {
        //中间
        canvas.drawText(String.valueOf(calendar.getDay()),
            cx,
            baselineY,
            mSelectTextPaint);
      }
    } else if (hasScheme) {

      canvas.drawText(String.valueOf(calendar.getDay()),
          cx,
          baselineY,
          calendar.isCurrentDay() ? mCurDayTextPaint :
              calendar.isCurrentMonth() && isInRange && isEnable ? mSchemeTextPaint
                  : mOtherMonthTextPaint);
    } else {

      canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
          calendar.isCurrentDay() ? mCurDayTextPaint :
              calendar.isCurrentMonth() && isInRange && isEnable ? mCurMonthTextPaint
                  : mOtherMonthTextPaint);
    }
  }
}
