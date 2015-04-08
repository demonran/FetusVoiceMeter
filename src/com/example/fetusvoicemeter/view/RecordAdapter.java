package com.example.fetusvoicemeter.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fetusvoicemeter.R;
import com.example.fetusvoicemeter.utils.Utils;

public class RecordAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;

	private List<File> data = new ArrayList<File>();

	public RecordAdapter(Context paramContext) {
		this.mContext = paramContext;
		this.mInflater = LayoutInflater.from(this.mContext);
	}

	public void addItem(File file) {
		data.add(file);
	}

	public void reflesh(File[] files) {
		data.clear();
		data.addAll(Arrays.asList(files));

	}

	public int getCount() {
		return data.size();
	}

	public File getItem(int index) {
		return data.get(index);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public int size() {
		return data.size();
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		View localView = this.mInflater.inflate(R.layout.list_item, null);
		TextView localTextView1 = (TextView) localView
				.findViewById(R.id.fileName);
		TextView localTextView2 = (TextView) localView
				.findViewById(R.id.createtime);
		final File localFile = getItem(position);
		localTextView1.setText(localFile.getName());
		localTextView2.setText(Utils.millis2CalendarString(localFile
				.lastModified()));
		((Button) localView.findViewById(R.id.Bt_delete))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						localFile.delete();
						data.remove(localFile);
						notifyDataSetChanged();
					}
				});

		return localView;
	}

}
