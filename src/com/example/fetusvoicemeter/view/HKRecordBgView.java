package com.example.fetusvoicemeter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class HKRecordBgView extends HKBaseView
{
  protected int gridYnum = 15;
  private float mBaseX = 0.0F;
  private Context mContext;
  protected float mPaddingBottom = 0.0F;
  protected float mPaddingTop = 0.0F;
  protected float mUnitWidth = 0.0F;
  protected int mWidth = 0;

  public HKRecordBgView(Context paramContext)
  {
    super(paramContext, null);
    this.mContext = paramContext;
    init();
  }

  public HKRecordBgView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    init();
  }

  private void drawGrid(Canvas paramCanvas)
  {
    float f1 = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum;
    int i = 0;
    if (i <= this.gridYnum)
    {
      if (i % 3 == 0);
      for (Paint localPaint2 = this.mGridBondPaint; ; localPaint2 = this.mGridPaint)
      {
        paramCanvas.drawLine(0.0F, this.mPaddingTop + f1 * i, this.mWidth, this.mPaddingTop + f1 * i, localPaint2);
        i++;
        break;
      }
    }
    float f2 = 3.0F * f1;
    int j = Math.round(this.mWidth / f2);
    int k = 0;
    while (k <= j)
      if (this.mBaseX + f2 * k > this.mWidth)
      {
        k++;
      }
      else
      {
        if (k % 3 == 0);
        for (Paint localPaint1 = this.mGridBondPaint; ; localPaint1 = this.mGridPaint)
        {
          paramCanvas.drawLine(this.mBaseX + f2 * k, this.mPaddingTop, this.mBaseX + f2 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint1);
          if (this.mBaseX - f2 * k < 0.0F)
            break;
          paramCanvas.drawLine(this.mBaseX - f2 * k, this.mPaddingTop, this.mBaseX - f2 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint1);
          break;
        }
      }
  }

  private void init()
  {
    this.mWidth = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
    this.mBaseX = (this.mWidth / 2.0F);
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (paramCanvas != null)
      drawGrid(paramCanvas);
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKRecordBgView
 * JD-Core Version:    0.6.2
 */