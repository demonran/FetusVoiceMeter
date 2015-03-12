package com.example.fetusvoicemeter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
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

  private void drawGrid(Canvas canvas)
  {
	  DisplayMetrics outMetrics = new DisplayMetrics();
	  Log.i("TAG",outMetrics.widthPixels+","+outMetrics.heightPixels+"***");
	  Log.i("TAG",canvas.getWidth()+","+canvas.getHeight()+"***");
    float f1 = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum;
    Log.i("TAG",getHeight()+","+View.MeasureSpec.getSize(getHeight()));
    Paint localPaint2 = this.mGridBondPaint;
//      if (i % 3 == 0);
   
	  for (int i = 0;i <= this.gridYnum ; i++)
	  {
	    canvas.drawLine(0.0F, this.mPaddingTop + f1 * i, View.MeasureSpec.getSize(getWidth()), this.mPaddingTop + f1 * i, localPaint2);
	    Log.i("TAG", "0.0F,"+ (this.mPaddingTop + f1 * i)+", 720, "+(this.mPaddingTop + f1 * i));
	  }
    
	  float f2 = 3.0F * f1;
//        if (k % 3 == 0);
    for (int k = 0;k <= this.gridYnum ; k++)
    {
      canvas.drawLine(this.mBaseX + f2 * k, this.mPaddingTop, this.mBaseX + f2 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint2);
      canvas.drawLine(this.mBaseX - f2 * k, this.mPaddingTop, this.mBaseX - f2 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint2);
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