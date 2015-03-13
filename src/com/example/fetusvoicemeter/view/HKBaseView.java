package com.example.fetusvoicemeter.view;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class HKBaseView extends View
{
  protected Context mContext;
  protected Paint mDefaultPaint = null;
  protected Paint mFrequencePaint = null;
  protected Paint mGridBondPaint = null;
  protected Paint mGridPaint = null;
  protected Paint mInteroducePaint = null;
  protected Paint mLevelRectPaint = null;
  protected Paint mTagPaint = null;
  protected Paint mTextPaint = null;

  public HKBaseView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HKBaseView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initGridPaint();
    initGridBondPaint();
    initDefaultPaint();
    initFrequencePaint();
    initTextPaint();
    initTagPaint();
    initIntroducePaint();
    initLevelRectPaint();
  }

  private void initDefaultPaint()
  {
    this.mDefaultPaint = new Paint();
    this.mDefaultPaint.setStrokeWidth(this.mContext.getResources().getDimension(R.dimen.app_icon_size));
    this.mDefaultPaint.setAntiAlias(true);
    this.mDefaultPaint.setColor(this.mContext.getResources().getColor(R.color.tab_indicator_text));
  }

  private void initFrequencePaint()
  {
    this.mFrequencePaint = new Paint();
    this.mFrequencePaint.setColor(this.mContext.getResources().getColor(R.color.tab_indicator_text));
    this.mFrequencePaint.setAntiAlias(true);
    this.mFrequencePaint.setStrokeWidth(getResources().getDimension(R.dimen.app_icon_size));
  }

  private void initGridBondPaint()
  {
    this.mGridBondPaint = new Paint();
    this.mGridBondPaint.setColor(Color.RED);
    this.mGridBondPaint.setAntiAlias(true);
    this.mGridBondPaint.setStrokeWidth(1);
  }

  private void initGridPaint()
  {
    this.mGridPaint = new Paint();
    this.mGridPaint.setColor(Color.BLUE);
    this.mGridPaint.setAntiAlias(true);
    this.mGridPaint.setStrokeWidth(2);
  }

  private void initIntroducePaint()
  {
    this.mInteroducePaint = new Paint(33);
    this.mInteroducePaint.setColor(-16777216);
    this.mInteroducePaint.setTextSize(16.0F);
  }

  private void initLevelRectPaint()
  {
    this.mLevelRectPaint = new Paint();
    this.mLevelRectPaint.setColor(getResources().getColor(R.color.tab_indicator_text));
    this.mLevelRectPaint.setStyle(Paint.Style.FILL);
    this.mLevelRectPaint.setAntiAlias(true);
  }

  private void initTagPaint()
  {
    this.mTagPaint = new Paint();
    this.mTagPaint.setColor(-16777216);
    this.mTagPaint.setTextSize(getResources().getDimension(R.dimen.app_icon_size));
    this.mTagPaint.setAntiAlias(true);
  }

  private void initTextPaint()
  {
    this.mTextPaint = new Paint();
    this.mTextPaint.setColor(getResources().getColor(R.color.tab_indicator_text));
    this.mTextPaint.setTextSize(getResources().getDimension(R.dimen.app_icon_size));
    this.mTextPaint.setAntiAlias(true);
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKBaseView
 * JD-Core Version:    0.6.2
 */