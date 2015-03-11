package com.example.fetusvoicemeter.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class HKRecordWaveView extends HKBaseView
{
  private float[] mBeatArray = null;
  private Rect mRect = new Rect();

  public HKRecordWaveView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HKRecordWaveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void drawDefault(Canvas paramCanvas)
  {
    this.mRect.set(0, 0, getWidth(), getHeight());
    if (this.mBeatArray == null)
    {
      Paint localPaint = new Paint();
      localPaint.setColor(0);
      paramCanvas.drawPaint(localPaint);
      return;
    }
    float f1 = this.mRect.width() / ( this.mBeatArray.length - 1);
    float f2 = 0;//HKWaveFormData.getOffsetValue();
    float f3 = this.mRect.height();
    float f4 = 17.0F - f2;
    if (17.0F - f2 <= 0.0F)
    {
    	f4 = 6.0F;
    }
      float f5 = f3 / f4;
      for (int i = 1; i < this.mBeatArray.length; i++)
      {
        float f6 = this.mRect.height() - f5 * this.mBeatArray[(i - 1)];
        float f7 = this.mRect.height() - f5 * this.mBeatArray[i];
        paramCanvas.drawLine(f1 * (i - 1), f6, f1 * i, f7, this.mDefaultPaint);
      }
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (paramCanvas != null)
      drawDefault(paramCanvas);
  }

  public void updateVisualizer(float[] paramArrayOfFloat)
  {
    this.mBeatArray = paramArrayOfFloat;
    invalidate();
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKRecordWaveView
 * JD-Core Version:    0.6.2
 */