package com.example.fetusvoicemeter.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

public class HKRecordWaveView extends HKBaseView
{
  private float[] mBeatArray = null;
  private List<Integer> showList;
  private Rect mRect = new Rect();
  
  private Paint linePaint = new Paint();
  
  private boolean isFirstDraw = true;
  
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ��ʼ��λ��(X������)
	 */
	private int showedBeginX = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ�������λ��(X������)
	 */
	private int showedEndX = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ��ʼ��ʱ���γ��ȵ�ֵ(Y������)
	 */
	private int showedBeginY = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ�������ʱ���γ��ȵ�ֵ(Y������)
	 */
	private int showedEndY = 0;
	
	/**
	 * �Ѿ���ʾ�����ݲ��� ����ܹ���ʾ�ĵ�λ������(������ 15px Ϊһ����λ��)
	 */
	private int maxNum = 0;

  public HKRecordWaveView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HKRecordWaveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    linePaint.setColor(Color.GREEN);
	// ���û��ʵ�������ϸ
	linePaint.setStrokeWidth(2);
	
	maxNum = 100;
  }

  private void drawDefault(Canvas canvas)
  {
    this.mRect.set(0, 0, getWidth(), getHeight());
    if (this.mBeatArray == null)
    {
      Paint localPaint = new Paint();
      localPaint.setColor(0);
      canvas.drawPaint(localPaint);
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
        canvas.drawLine(f1 * (i - 1), f6, f1 * i, f7, this.mDefaultPaint);
      }
  }
  
  private void drawL(Canvas canvas)
  {
	  int width = canvas.getWidth();
	  int height = canvas.getHeight();
		if (isFirstDraw) {
			maxNum = (width/2 - 5) / 5;
			isFirstDraw = false;
		}
	//����ĵ����ݼ�����������
		if (showList!= null && showList.size() >0) {
			
			// �������ȫ��ʾ�Ѿ���ʾ���Ĳ������ݾ;�ֱ����ӽ���ʾ���������ݵļ����з���ȥ����һ������ӽ�ȥ
			if (showList.size() > maxNum) {
				showList = showList.subList(showList.size()-maxNum-1 ,showList.size()-1 );
			}
			
		
			showedBeginX = width - (5 * showList.size()+5);
			showedEndX = showedBeginX + 5;
			showedBeginY = height / 2;
			for (int i = 0; i < showList.size(); i++) {
				showedEndY = showList.get(i);
				canvas.drawLine(showedBeginX, showedBeginY, showedEndX,showedEndY, linePaint);
				showedBeginX = showedEndX;
				showedEndX = showedBeginX +5;
				showedBeginY = showedEndY;
				showedEndY = height+60-showList.get(i+1);
			}
		}
			
  }

  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (paramCanvas != null)
//      drawDefault(paramCanvas);
    	drawL(paramCanvas);
  }

  public void updateVisualizer(List<Integer> paramArrayOfFloat)
  {
//    this.mBeatArray = paramArrayOfFloat;
	  this.showList = paramArrayOfFloat;
    invalidate();
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKRecordWaveView
 * JD-Core Version:    0.6.2
 */