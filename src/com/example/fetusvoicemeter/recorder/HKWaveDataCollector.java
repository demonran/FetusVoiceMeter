package com.example.fetusvoicemeter.recorder;

import java.util.Vector;

public class HKWaveDataCollector {
	private Vector<Integer> mList = new Vector<Integer>();

	public void addData(int paramInt) {
		this.mList.add(Integer.valueOf(paramInt));
	}

	public void clearData() {
		this.mList.clear();
	}

	public int dataSize() {
		return this.mList.size();
	}

	public int getCurrentData(int index) {
		if ((index < 0) || (index >= this.mList.size())) {
			return 0;
		}
		return this.mList.get(index);
	}

	public void setData(int paramInt1, int paramInt2) {
		if ((paramInt1 < 0) || (paramInt1 >= this.mList.size())) {
			this.mList.set(paramInt1, Integer.valueOf(paramInt2));
		}
	}
}
