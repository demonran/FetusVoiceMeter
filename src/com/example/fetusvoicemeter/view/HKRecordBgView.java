package com.example.fetusvoicemeter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class HKRecordBgView extends HKBaseView
{
  protected int gridYnum = 18;
  protected int gridXnum = 8;
  private float mBaseX = 0.0F;
  private Context mContext;
  protected float mPaddingBottom = 0.0F;
  protected float mPaddingTop = 0.0F;
  protected float mUnitWidth = 0.0F;
  protected float mUnitHeight = 0.0F;
  protected int mWidth = 0;
  protected int mHeight ;
  protected int maxNum;
  

  public HKRecordBgView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HKRecordBgView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    init();
  }

  private void drawGrid(Canvas canvas)
  {
    float f1 = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum;
    Paint localPaint2 = this.mGridPaint;
//      
   
	  for (int i = 0;i <= this.gridYnum ; i++)
	  {
		  if (i % 3 == 0)
		  {
			  localPaint2 = mGridBondPaint;
			  canvas.drawText(40+10*(this.gridYnum-i)+"",mWidth-20, this.mPaddingTop + f1 * i, mTextPaint);
		  }else
		  {
			  localPaint2 = mGridPaint;
		  }
	    canvas.drawLine(0.0F, this.mPaddingTop + f1 * i, View.MeasureSpec.getSize(getWidth()), this.mPaddingTop + f1 * i, localPaint2);
//	    canvas.drawText(40+10*i+"",mWidth-40, this.mPaddingTop + f1 * i, mTextPaint);
	  }
    
//        if (k % 3 == 0);
    for (int k = 0;k <= this.gridXnum/2 ; k++)
    {
      canvas.drawLine(this.mBaseX + mUnitWidth *80 * k, this.mPaddingTop, this.mBaseX + mUnitWidth *80 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint2);
      canvas.drawLine(this.mBaseX - mUnitWidth *80 * k, this.mPaddingTop, this.mBaseX - mUnitWidth *80 * k, View.MeasureSpec.getSize(getHeight()) - this.mPaddingBottom, localPaint2);
    }
  }

  private void init()
  {
    this.mWidth = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth()-20;
    this.mBaseX = (this.mWidth / 2.0F);
    this.maxNum = 4* 20 * gridXnum;
	this.mUnitWidth = this.mWidth/(float)maxNum;
	 
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