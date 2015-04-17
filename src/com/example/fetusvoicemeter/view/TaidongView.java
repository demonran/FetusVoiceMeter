package com.example.fetusvoicemeter.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fetusvoicemeter.R;
import com.example.fetusvoicemeter.listener.ShakeListener;
import com.example.fetusvoicemeter.listener.ShakeListener.OnShakeListener;
import com.example.fetusvoicemeter.utils.Utils;



public class TaidongView {
	private Context context;
	private View view;
	
	// 两次检测的时间间隔
	private static int update_interval_time = 70;
	
	private Button start_btn;
	
	ShakeListener mShakeListener = null;
	Vibrator mVibrator;
	
	private LinearLayout start_root_ll;
	private LinearLayout taidong_record_root_ll;
	
	private Button action_btn;
	private Button stop_btn;
	
	private TextView taidong_distance_text;
	private TextView taidong_num_text;
	private TextView taidong_valid_num;
	private TextView taidong_begin_time_text;
	private TextView taidong_remain_time_text;
	
	private MediaPlayer player;
	Chronometer chronometer;
	
	// 上次检测时间
	private long lastUpdateTime;
	
	public TaidongView(Context context)
	{
		this.context = context;
		LayoutInflater mLi = LayoutInflater.from(context);
		view = mLi.inflate(R.layout.main_tab_friends, null);
		
		start_root_ll = (LinearLayout)view.findViewById(R.id.start_root);
		start_btn = (Button)view.findViewById(R.id.start_btn); 
		taidong_record_root_ll = (LinearLayout)view.findViewById(R.id.taidong_record_root);
		action_btn =(Button)view.findViewById(R.id.action_btn);
		stop_btn =  (Button)view.findViewById(R.id.stop_btn);
		taidong_distance_text = (TextView)view.findViewById(R.id.taidong_distance_text);
		taidong_num_text = (TextView)view.findViewById(R.id.taidong_num_text);
		taidong_valid_num = (TextView)view.findViewById(R.id.taidong_valid_num);
		taidong_begin_time_text = (TextView)view.findViewById(R.id.taidong_begin_time_text);
		taidong_remain_time_text = (TextView)view.findViewById(R.id.taidong_remain_time_text);
		
		mVibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		
		player = MediaPlayer.create(context, R.raw.taidong);
		
		chronometer = new Chronometer(context);
		Log.i("TAG",System.currentTimeMillis()+"---");
		Log.i("TAG",SystemClock.elapsedRealtime()+"---");
		chronometer.setBase(SystemClock.elapsedRealtime()-60 * 60 * 1000);
		chronometer.setFormat("H:MM");
		
		
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {
			
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				
				stopTaidong();
			}
		});
		addEvent();
	}
	
	private void addEvent()
	{
		start_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTaidong();
			}
		});
		
		action_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addTaidong();
			}
		});
		
		stop_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopTaidong();
			}
		});
	}
	
	private void addTaidong()
	{
		taidong_num_text.setText(Integer.parseInt((String)taidong_num_text.getText())+1+"");
		playVoice();
		// 现在检测时间
		long currentUpdateTime = System.currentTimeMillis();
		// 两次检测的时间间隔
		long timeInterval = currentUpdateTime - lastUpdateTime;
		// 判断是否达到了检测时间间隔
		if (timeInterval < update_interval_time)
			return;
		// 现在的时间变成last时间
		lastUpdateTime = currentUpdateTime;
		taidong_valid_num.setText(Integer.parseInt((String)taidong_valid_num.getText())+1+"");
	}
	
	private void stopTaidong()
	{
		start_root_ll.setVisibility(View.VISIBLE);
		taidong_record_root_ll.setVisibility(View.GONE);
		mShakeListener.stop();
		mShakeListener = null;
		taidong_valid_num.setText("0");
		taidong_num_text.setText("0");
		chronometer.stop();
	}
	
	private void startTaidong()
	{
		start_root_ll.setVisibility(View.GONE);
		taidong_record_root_ll.setVisibility(View.VISIBLE);
		update_interval_time = Integer.parseInt((String)taidong_distance_text.getText())*60*100;
		taidong_begin_time_text.setText(Utils.getTimeString("HH:MM:ss"));
		chronometer.start();
		taidong_remain_time_text.setText(chronometer.getText());
		mShakeListener = new ShakeListener(context);
        mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				startVibrato(); //开始 震动
				mShakeListener.stop();
				new Handler().post(new Runnable(){
					@Override
					public void run(){
						addTaidong();
						mShakeListener.start();
					}
				});
			}
		});
	}
	
	public void startVibrato(){		//定义震动
		mVibrator.vibrate( new long[]{500,200,500,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}
	
	private void playVoice()
	{
		player.start();
	}

	public View getView() {
		return view;
	}
	
	

}
