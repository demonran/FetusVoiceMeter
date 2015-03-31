package com.example.fetusvoicemeter;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fetusvoicemeter.recorder.RecordingProcess;
import com.example.fetusvoicemeter.view.HKRecordWaveView;
import com.fetus.FetusCore;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	public static MainActivity instance = null;

	private ViewPager mTabPager;
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int two;
	private int three;
	private LinearLayout play_ll;
	private LinearLayout rec_ll;
	private TextView rec_fq_num;
	
	private TextView rec_audio_time;

	private HKRecordWaveView recordWaveView;
	
	private String temp = "recaudio_";// 临时文件前缀
	
	private RecordingProcess mRecProcess;
	/**
	 * 在画布上正在显示的数据集合
	 */
	private ArrayList<Integer> showedList = new ArrayList<Integer>();

	
	Timer timer;
	
	private long writeFileStartTime;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int bpm = msg.arg1;
			rec_fq_num.setText(String.valueOf(bpm));
			showedList.add(bpm);
			recordWaveView.updateVisualizer(showedList);
		}

	};

	private Runnable mRecUpdateAudioTime = new Runnable()
	  {
	    DecimalFormat df = new DecimalFormat("00");

	    public void run()
	    {
	      if (writeFileStartTime == 0)
	        Log.e("TAG", "mUpdateAudioTime | mRecordingProcess is null!");
	      else
	      {
	        long l = Math.round((float)((System.currentTimeMillis() - writeFileStartTime) / 1000L));
	        String str1 = "%s:%s";
	        Object[] arrayOfObject = new Object[2];
	        arrayOfObject[0] = this.df.format(l / 60L);
	        arrayOfObject[1] = this.df.format(l % 60L);
	        String str2 = String.format(str1, arrayOfObject);
	        rec_audio_time.setText(str2);
	        handler.postDelayed(mRecUpdateAudioTime, 1000L);
	      }
	    }
	  };


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_weixin);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		initUI();

		// setAudioNormal();

		FetusCore.init();

		timer = new Timer();

	}


	private void initUI() {
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		int displayWidth = currDisplay.getWidth();
		one = displayWidth / 4; // 设置水平动画平移大小
		two = one * 2;
		three = one * 3;

		// InitImageView();//使用动画
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
		recordWaveView = (HKRecordWaveView) view1.findViewById(R.id.rec_wave);
		rec_audio_time = (TextView)view1.findViewById(R.id.rec_audio_time);
		play_ll = (LinearLayout) view1.findViewById(R.id.play_ll);
		rec_ll = (LinearLayout) view1.findViewById(R.id.rec_ll);
		rec_fq_num = (TextView) view1.findViewById(R.id.rec_fq_num);
		View view2 = mLi.inflate(R.layout.main_tab_address, null);
		View view3 = mLi.inflate(R.layout.main_tab_friends, null);
		View view4 = mLi.inflate(R.layout.main_tab_settings, null);

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mTabPager.setAdapter(mPagerAdapter);
		
		recordWaveView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("TAG","view click..");
				mRecProcess.stopAudioRecord();
				
			}
		});
	}

	private void updateRecUI() {
		play_ll.setVisibility(View.GONE);
		rec_ll.setVisibility(View.VISIBLE);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	/*
	 * 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_fetusvoice_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recored_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_discovery_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recreation_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_recored_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_discovery_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recreation_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_discovery_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recored_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recreation_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_recreation_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_recored_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_discovery_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
			this.finish(); // back键
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void btnmainright(View view) {
		updateRecUI();
		this.mRecProcess = new RecordingProcess(this);
		mRecProcess.startAudioRecord(true);
		timer.schedule(new RecBpm(), 1000, 1);
	}

	class RecBpm extends TimerTask {

		@Override
		public void run() {
			int bpm = FetusCore.get();
			Message msg = new Message();
			msg.arg1 = bpm;
			handler.sendMessage(msg);
		}
	}

}
