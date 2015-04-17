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

public class HKRecordWaveView extends HKRecordBgView {
	private List<Integer> showList;

	private Paint linePaint = new Paint();

	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ��ʼ��λ��(X������)
	 */
	private float showedBeginX = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ�������λ��(X������)
	 */
	private float showedEndX = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ��ʼ��ʱ���γ��ȵ�ֵ(Y������)
	 */
	private float showedBeginY = 0;
	/**
	 * ��ʾ�Ĳ���ÿ����Ԫ�������ʱ���γ��ȵ�ֵ(Y������)
	 */
	private float showedEndY = 0;

	/**
	 * ��ʾ�Ƿ��ǵ�һ�λ�ͼ
	 */
	private boolean isFirstDraw = true;

	public HKRecordWaveView(Context paramContext) {
		super(paramContext, null);
	}

	public HKRecordWaveView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);

		init();

	}

	private void init() {
		linePaint.setColor(Color.BLACK);
		// ���û��ʵ�������ϸ
		linePaint.setStrokeWidth(3);
	}

	private void drawL(Canvas canvas) {
		if (isFirstDraw) {
			this.mHeight = getHeight();
			Log.i("TAG", " this.mWidth=" + this.mWidth + ", mHeight=" + mHeight);
			this.mUnitHeight = this.mHeight / 150.0F;
			isFirstDraw = false;
		}

		// ����ĵ����ݼ�����������
		if (showList != null && showList.size() >= 2) {

			// �������ȫ��ʾ�Ѿ���ʾ���Ĳ������ݾ;�ֱ����ӽ���ʾ���������ݵļ����з���ȥ����һ������ӽ�ȥ
			if (showList.size() > maxNum) {
				showList = showList.subList(showList.size() - maxNum - 1,
						showList.size() - 1);
			}

			showedBeginX = this.mWidth - showList.size() * mUnitWidth;
			showedBeginY = mHeight - (showList.get(0) - 40) * mUnitHeight;

			for (int i = 1; i < showList.size(); i++) {
				showedEndX = showedBeginX + mUnitWidth;
				showedEndY = mHeight - (showList.get(i) - 40) * mUnitHeight;
				// Log.i("TAG",showedBeginX+","+ showedBeginY+","+
				// showedEndX+","+showedEndY);
				canvas.drawLine(showedBeginX, showedBeginY, showedEndX,
						showedEndY, linePaint);
				// canvas.drawLine(0, 0, 100,100, linePaint);
				showedBeginX = showedEndX;
				showedBeginY = showedEndY;
			}
		}
	}

	public void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
		if (paramCanvas != null)
			// drawDefault(paramCanvas);
			drawL(paramCanvas);
	}

	public void updateVisualizer(List<Integer> paramArrayOfFloat) {
		this.showList = paramArrayOfFloat;
		invalidate();
	}
}

