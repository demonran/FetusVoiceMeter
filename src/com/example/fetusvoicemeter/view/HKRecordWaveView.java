package com.example.fetusvoicemeter.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

public class HKRecordWaveView extends HKRecordBgView
{
  private List<Integer> showList;
  
  private Paint linePaint = new Paint();
  
	/**
	 * 显示的波形每个单元格开始的位置(X轴坐标)
	 */
	private float showedBeginX = 0;
	/**
	 * 显示的波形每个单元格结束的位置(X轴坐标)
	 */
	private float showedEndX = 0;
	/**
	 * 显示的波形每个单元格开始的时候波形长度的值(Y轴坐标)
	 */
	private float showedBeginY = 0;
	/**
	 * 显示的波形每个单元格结束的时候波形长度的值(Y轴坐标)
	 */
	private float showedEndY = 0;
	
	/**
	 * 标示是否是第一次画图
	 */
	private boolean isFirstDraw = true;

  public HKRecordWaveView(Context paramContext)
  {
    super(paramContext, null);
  }

  public HKRecordWaveView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
   
	
	init();
	
	
  }
  
  private void init()
  {
	  linePaint.setColor(Color.BLACK);
		// 设置画笔的线条粗细
		linePaint.setStrokeWidth(3);
  }

  
  private void drawL(Canvas canvas)
  {
	  if(isFirstDraw)
	  {
		  this.mHeight = getHeight();
		  Log.i("TAG", " this.mWidth="+this.mWidth+", mHeight="+mHeight);
		  this.mUnitHeight = this.mHeight/150.0F;
		  isFirstDraw = false;
	  }
	 
	//如果心电数据集合中有数据
		if (showList!= null && showList.size() >= 2) {
			
			// 如果能完全显示已经显示过的波形数据就就直接添加进显示过波形数据的集合中否则去掉第一个再添加进去
			if (showList.size() > maxNum) {
				showList = showList.subList(showList.size()-maxNum-1 ,showList.size()-1 );
			}
			
		
			showedBeginX = this.mWidth - showList.size() * mUnitWidth;
			showedBeginY = mHeight - (showList.get(0)-60) * mUnitHeight;
			
			for (int i = 1; i < showList.size(); i++) {
				showedEndX = showedBeginX + mUnitWidth;
				showedEndY = mHeight - (showList.get(i)-60) * mUnitHeight;
//				 Log.i("TAG",showedBeginX+","+ showedBeginY+","+ showedEndX+","+showedEndY);
				canvas.drawLine(showedBeginX, showedBeginY, showedEndX,showedEndY, linePaint);
//				 canvas.drawLine(0, 0, 100,100, linePaint);
				showedBeginX = showedEndX;
				showedBeginY = showedEndY;
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
	  this.showList = paramArrayOfFloat;
    invalidate();
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.view.HKRecordWaveView
 * JD-Core Version:    0.6.2
 */