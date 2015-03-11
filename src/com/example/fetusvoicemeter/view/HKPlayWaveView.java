package com.example.fetusvoicemeter.view;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.fetusvoicemeter.recorder.HKFloatDataCollector;
import com.example.fetusvoicemeter.recorder.HKWaveDataCollector;

public class HKPlayWaveView extends HKBaseView
{
  protected int gridYnum = 15;
  private float mBaseX = 0.0F;
  private Context mContext;
  protected HKWaveDataCollector mFrequenceCollector = null;
  protected float mPaddingBottom = 0.0F;
  protected float mPaddingTop = 0.0F;
  protected HKFloatDataCollector mTagCollector = null;
  protected Bitmap mTextBg = null;
  protected float mTextSize = 0.0F;
  protected float mTextWidth = 0.0F;
  protected HKFloatDataCollector mTimeCollector = null;
  protected float mUnitWidth = 0.0F;
  protected int mWidth = 0;

  public HKPlayWaveView(Context paramContext)
  {
    super(paramContext, null);
    this.mContext = paramContext;
    init();
  }

  public HKPlayWaveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    mFrequenceCollector = new HKWaveDataCollector();
    mFrequenceCollector.addData(125);
    init();
  }

  private void drawFrequenceText(Canvas paramCanvas)
  {
    float f1 = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum;
    float f2 = 3.0F * f1;
    int i = Math.round((this.mWidth + getWaveWidth()) / f2);
    String[] arrayOfString = getResources().getStringArray(R.array.emailAddressTypes);
    int j = 0;
    for (int k = 0; k <= this.gridYnum; k += 3)
    {
      int n = 0;
      if (n < i)
      {
        float f5 = this.mTextPaint.measureText(arrayOfString[j]);
        if ((n % 6 != 0) || (this.mBaseX + f2 * n > this.mWidth + getWaveWidth()));
        while (true)
        {
          n++;
          paramCanvas.drawBitmap(this.mTextBg, this.mBaseX + f2 * n - this.mTextBg.getWidth() / 2, this.mPaddingTop + f1 * k - this.mTextBg.getHeight() / 2, null);
          paramCanvas.drawText(arrayOfString[j], this.mBaseX + f2 * n - f5 / 2.0F, this.mPaddingTop + f1 * k + this.mTextSize / 3.0F, this.mTextPaint);
          if (this.mBaseX - f2 * n >= 0.0F)
          {
            paramCanvas.drawBitmap(this.mTextBg, this.mBaseX - f2 * n - this.mTextBg.getWidth() / 2, this.mPaddingTop + f1 * k - this.mTextBg.getHeight() / 2, null);
            paramCanvas.drawText(arrayOfString[j], this.mBaseX - f2 * n - f5 / 2.0F, this.mPaddingTop + f1 * k + this.mTextSize / 3.0F, this.mTextPaint);
          }
        }
      }
      j++;
    }
    float f3 = this.mTextPaint.measureText("EHR bpm");
    Bitmap localBitmap = resizeBackground(this.mTextBg, f3, this.mTextBg.getHeight());
    int m = 0;
    if (m < i)
    {
      float f4 = this.mTextPaint.measureText("EHR bpm");
      if (this.mBaseX + f2 * m > this.mWidth + getWaveWidth());
      while (true)
      {
        m += 6;
        paramCanvas.drawBitmap(localBitmap, this.mBaseX + f2 * m - localBitmap.getWidth() / 2, getHeight() / 2 - localBitmap.getHeight() / 2, null);
        paramCanvas.drawText("EHR bpm", this.mBaseX + f2 * m - f4 / 2.0F, getHeight() / 2 + this.mTextSize / 3.0F, this.mTextPaint);
        if (this.mBaseX - f2 * m >= 0.0F)
        {
          paramCanvas.drawBitmap(localBitmap, this.mBaseX - f2 * m - localBitmap.getWidth() / 2, View.MeasureSpec.getSize(getHeight()) / 2 - localBitmap.getHeight() / 2, null);
          paramCanvas.drawText("EHR bpm", this.mBaseX - f2 * m - f4 / 2.0F, View.MeasureSpec.getSize(getHeight()) / 2 + this.mTextSize / 3.0F, this.mTextPaint);
        }
      }
    }
  }

  private void drawFrequency(Canvas paramCanvas)
  {
//    if ((this.mFrequenceCollector == null) || (this.mFrequenceCollector.dataSize() == 0))
//      return;
//    float f = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / (10 * this.gridYnum);
//    int i = 1;
//    label48: if (i < this.mFrequenceCollector.dataSize())
//      if (this.mFrequenceCollector.getCurrentData(i) != 0)
//        break label76;
//    while (true)
//    {
//      i++;
//      break label48;
//      break;
//      label76: if (this.mBaseX + this.mUnitWidth * this.mTimeCollector.getCurrentData(i) > View.MeasureSpec.getSize(getWidth()))
//        break;
//      if (this.mFrequenceCollector.getCurrentData(i - 1) == 0)
//      {
//        if ((i + 1 < this.mFrequenceCollector.dataSize()) && (this.mFrequenceCollector.getCurrentData(i + 1) == 0))
//        {
//          float[] arrayOfFloat = new float[2];
//          arrayOfFloat[0] = (this.mBaseX + this.mUnitWidth * this.mTimeCollector.getCurrentData(i));
//          arrayOfFloat[1] = (f * (210 - this.mFrequenceCollector.getCurrentData(i)));
//          paramCanvas.drawPoints(arrayOfFloat, this.mFrequencePaint);
//        }
//      }
//      else
//        paramCanvas.drawLine(this.mBaseX + this.mUnitWidth * this.mTimeCollector.getCurrentData(i - 1), this.mPaddingTop + f * (210 - this.mFrequenceCollector.getCurrentData(i - 1)), this.mBaseX + this.mUnitWidth * this.mTimeCollector.getCurrentData(i), this.mPaddingTop + f * (210 - this.mFrequenceCollector.getCurrentData(i)), this.mFrequencePaint);
//    }
  }

  @SuppressLint("NewApi")
private void drawGrid(Canvas canvas)
  {
	  DisplayMetrics outMetrics = new DisplayMetrics();
	  this.getDisplay().getMetrics(outMetrics);
	  Log.i("TAG",outMetrics.widthPixels+","+outMetrics.heightPixels+"***");
	  Log.i("TAG", this.getDisplay().getWidth()+","+ this.getDisplay().getHeight()+"***");
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

  private void drawLevelRect(Canvas paramCanvas)
  {
    float f = (View.MeasureSpec.getSize(getHeight()) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum;
    paramCanvas.drawRect(0.0F, this.mPaddingTop + 5.0F * f, this.mWidth + getWaveWidth(), this.mPaddingTop + 9.0F * f, this.mLevelRectPaint);
  }

  private void drawTag(Canvas paramCanvas)
  {
//    if ((this.mTagCollector == null) || (this.mTagCollector.dataSize() == 0))
//      return;
//    Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), 2130837707);
//    int i = 0;
//    label30: if (i < this.mTagCollector.dataSize())
//      if (this.mTagCollector.getCurrentData(i) != 0.0F)
//        break label60;
//    while (true)
//    {
//      i++;
//      break label30;
//      break;
//      label60: if (this.mBaseX + this.mUnitWidth * this.mTagCollector.getCurrentData(i) > View.MeasureSpec.getSize(getWidth()))
//        break;
//      paramCanvas.drawBitmap(localBitmap, this.mBaseX + this.mUnitWidth * this.mTagCollector.getCurrentData(i) - 7.5F, -30 + getHeight(), this.mTagPaint);
//    }
  }

  private void init()
  {
    this.mWidth = ((WindowManager)this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    this.mBaseX = (this.mWidth / 2.0F);
    this.mTextSize = getResources().getDimension(R.dimen.app_icon_size);
    this.mTextWidth = this.mTextPaint.measureText("111");
    if (this.mTextBg == null)
    {
      this.mTextBg = BitmapFactory.decodeResource(getResources(), R.drawable.alert_dark_frame);
      if (this.mTextBg != null)
        this.mTextBg = resizeBackground(this.mTextBg, this.mTextWidth, getResources().getDimension(R.dimen.app_icon_size));
    }
    this.mPaddingTop = 0F;
    this.mPaddingBottom = 0F;
    setWillNotDraw(false);
  }

  public float getUnitX()
  {
    return this.mUnitWidth;
  }

  public float getWaveWidth()
  {
	  float f = 0.0F;
    if (this.mTimeCollector == null)
    {
    	return f;
    }
    else
    {
    	f = this.mUnitWidth * this.mTimeCollector.getCurrentData( this.mTimeCollector.dataSize()-1);
    }
    return f;
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((paramCanvas != null) && (this.mFrequenceCollector != null))
    {
      drawGrid(paramCanvas);
//      drawFrequenceText(paramCanvas);
//      drawFrequency(paramCanvas);
//      drawTag(paramCanvas);
//      drawLevelRect(paramCanvas);
    }
  }

  public void onMeasure(int paramInt1, int paramInt2)
  {
	  Log.i("TAG",paramInt1+","+paramInt2);
    this.mUnitWidth = (3.0F * ((View.MeasureSpec.getSize(paramInt2) - this.mPaddingTop - this.mPaddingBottom) / this.gridYnum) / 20.0F);
//    if (this.mFrequenceCollector != null)
//    {
//    setMeasuredDimension(View.MeasureSpec.getSize(this.mWidth + Math.round(getWaveWidth())), View.MeasureSpec.getSize(paramInt2));
    setMeasuredDimension(View.MeasureSpec.getSize(this.mWidth ), View.MeasureSpec.getSize(paramInt2));
//    }else
//    {
//    	 setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
//    }
  }

  protected Bitmap resizeBackground(Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    return Bitmap.createScaledBitmap(paramBitmap, Math.round(paramFloat1), Math.round(paramFloat2), true);
  }

  public void setDataList(HKWaveDataCollector paramHKWaveDataCollector, HKFloatDataCollector paramHKFloatDataCollector1, HKFloatDataCollector paramHKFloatDataCollector2)
  {
    this.mFrequenceCollector = paramHKWaveDataCollector;
    this.mTimeCollector = paramHKFloatDataCollector1;
    this.mTagCollector = paramHKFloatDataCollector2;
    setBackgroundColor(0);
    invalidate();
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKPlayWaveView
 * JD-Core Version:    0.6.2
 */