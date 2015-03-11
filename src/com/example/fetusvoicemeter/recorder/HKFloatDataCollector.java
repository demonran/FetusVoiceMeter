package com.example.fetusvoicemeter.recorder;

import java.util.Vector;

public class HKFloatDataCollector
{
  private Vector<Float> mList = new Vector();

  public void addData(float paramFloat)
  {
    this.mList.add(Float.valueOf(paramFloat));
  }

  public void clearData()
  {
    this.mList.clear();
  }

  public int dataSize()
  {
    return this.mList.size();
  }

  public float getCurrentData(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.mList.size()));
    for (float f = 0.0F; ; f = ((Float)this.mList.get(paramInt)).floatValue())
      return f;
  }

  public void setData(int paramInt, float paramFloat)
  {
    if ((paramInt < 0) || (paramInt >= this.mList.size()))
    {
      this.mList.set(paramInt, Float.valueOf(paramFloat));
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.recorder.HKFloatDataCollector
 * JD-Core Version:    0.6.2
 */