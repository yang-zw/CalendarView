package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.myapplication.calendarView.CalendarView;
import com.example.myapplication.calendarView.CalendarView.OnMonthChangeListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private CalendarView mCalendarView;
  private TextView mTvCalendarTitle;
  private ImageView mImgLastYear, mImgLastMonth, mImgNextYear, mImgNextMonth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();

    setCalendarText(mCalendarView.getCurYear(), mCalendarView.getCurMonth());

    //日历滑动事件
    mCalendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
      @Override
      public void onMonthChange(int year, int month) {
        setCalendarText(year, month);
      }
    });
  }

  private void initView() {
    mCalendarView = findViewById(R.id.calendarView);
    mTvCalendarTitle = findViewById(R.id.tv_info);
    mImgLastYear = findViewById(R.id.img_last_year);
    mImgLastMonth = findViewById(R.id.img_last_month);
    mImgNextYear = findViewById(R.id.img_next_year);
    mImgNextMonth = findViewById(R.id.img_next_month);

    mImgLastYear.setOnClickListener(this);
    mImgLastMonth.setOnClickListener(this);
    mImgNextYear.setOnClickListener(this);
    mImgNextMonth.setOnClickListener(this);
  }

  private void setCalendarText(int year, int month) {
    mTvCalendarTitle.setText(String.format("%s年%s月", year, month));
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.img_last_year:
        int lastCurrentItem = mCalendarView.getMonthViewPager().getCurrentItem();
        mCalendarView.getMonthViewPager().setCurrentItem(lastCurrentItem - 12);
        break;
      case R.id.img_last_month:
        int i = mCalendarView.getMonthViewPager().getCurrentItem();
        mCalendarView.getMonthViewPager().setCurrentItem(i - 1);
        break;
      case R.id.img_next_year:
        int nextCurrentItem = mCalendarView.getMonthViewPager().getCurrentItem();
        mCalendarView.getMonthViewPager().setCurrentItem(nextCurrentItem + 12);
        break;
      case R.id.img_next_month:
        int i2 = mCalendarView.getMonthViewPager().getCurrentItem();
        mCalendarView.getMonthViewPager().setCurrentItem(i2 + 1);
        break;
    }
  }
}
