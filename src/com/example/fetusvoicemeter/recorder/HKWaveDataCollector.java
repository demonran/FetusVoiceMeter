package com.example.fetusvoicemeter.recorder;

import java.util.Vector;

public class HKWaveDataCollector
{
  private Vector<Integer> mList = new Vector();

  public void addData(int paramInt)
  {
    this.mList.add(Integer.valueOf(paramInt));
  }

  public void clearData()
  {
    this.mList.clear();
  }

  public int dataSize()
  {
    return this.mList.size();
  }

  public int getCurrentData(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.mList.size()));
    for (int i = 0; ; i = ((Integer)this.mList.get(paramInt)).intValue())
      return i;
  }

  public void setData(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.mList.size()))
    {
      this.mList.set(paramInt1, Integer.valueOf(paramInt2));
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\classes_dex2jar.jar
 * Qualified Name:     com.icarenewlife.recorder.HKWaveDataCollector
 * JD-Core Version:    0.6.2
 */