package com.example.fetusvoicemeter;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.fetusvoicemeter.view.HKRecordWaveView;
import com.fetus.FetusCore;

public class MainActivity extends Activity {
	
	public static MainActivity instance = null;
	 
	private ViewPager mTabPager;	
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	
	private HKRecordWaveView recordWaveView;
	//private Button mRightBtn;
	
	/**
	 * 在画布上正在显示的数据集合
	 */
	private ArrayList<Integer> showedList = new ArrayList<Integer>();
	
	
	   boolean isRecording = false;//是否录放的标记  
	    static final int frequency = 44100;  
	    static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;  
	    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;  
	    int recBufSize,playBufSize;  
	    AudioRecord audioRecord;  
	    AudioTrack audioTrack;
	    Timer timer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_weixin);
        int sum = FetusCore.add(1, 2);
        Log.i("TAG",sum+"***");
         //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        instance = this;
       
        recBufSize = AudioRecord.getMinBufferSize(frequency,  
                channelConfiguration, audioEncoding);  
        Log.i("TAG","recBufSize="+recBufSize+"---");
  
        playBufSize=AudioTrack.getMinBufferSize(frequency,  
                channelConfiguration, audioEncoding);  
        Log.i("TAG","playBufSize="+playBufSize+"---");
        // -----------------------------------------  
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,  
                channelConfiguration, audioEncoding, recBufSize);  
  
        audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, frequency,  
                channelConfiguration, audioEncoding,  
                playBufSize, AudioTrack.MODE_STREAM);  
        timer =  new Timer();
        
        mTabPager = (ViewPager)findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        
        mTab1 = (ImageView) findViewById(R.id.img_weixin);
        mTab2 = (ImageView) findViewById(R.id.img_address);
        mTab3 = (ImageView) findViewById(R.id.img_friends);
        mTab4 = (ImageView) findViewById(R.id.img_settings);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));
        mTab4.setOnClickListener(new MyOnClickListener(3));
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        int displayWidth = currDisplay.getWidth();
        one = displayWidth/4; //设置水平动画平移大小
        two = one*2;
        three = one*3;
        //Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);
        
        //InitImageView();//使用动画
      //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.main_tab_weixin, null);
        recordWaveView = (HKRecordWaveView)view1.findViewById(R.id.rec_wave);
        View view2 = mLi.inflate(R.layout.main_tab_address, null);
        View view3 = mLi.inflate(R.layout.main_tab_friends, null);
        View view4 = mLi.inflate(R.layout.main_tab_settings, null);
        
      //每个页面的view数据
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
      //填充ViewPager的数据适配器
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
				((ViewPager)container).removeView(views.get(position));
			}
			
			//@Override
			//public CharSequence getPageTitle(int position) {
				//return titles.get(position);
			//}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
		
		mTabPager.setAdapter(mPagerAdapter);
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
    
	 /* 页卡切换监听(原作者:D.Winter)
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_fetusvoice_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_recored_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_recreation_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_recored_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_recreation_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_recored_normal));
				}
				else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_recreation_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(R.drawable.tab_recreation_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(R.drawable.tab_fetusvoice_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(R.drawable.tab_recored_normal));
				}
				else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(R.drawable.tab_discovery_normal));
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
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键
    		
        	if(menu_display){         //如果 Menu已经打开 ，先关闭Menu
        		menuWindow.dismiss();
        		menu_display = false;
        		}
//        	else {
//        		Intent intent = new Intent();
//            	intent.setClass(MainWeixin.this,Exit.class);
//            	startActivity(intent);
//        	}
    	}
    	
//    	else if(keyCode == KeyEvent.KEYCODE_MENU){   //获取 Menu键			
//			if(!menu_display){
//				//获取LayoutInflater实例
//				inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//				//这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
//				//该方法返回的是一个View的对象，是布局中的根
//				layout = inflater.inflate(R.layout.main_menu, null);
//				
//				//下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
//				menuWindow = new PopupWindow(layout,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //后两个参数是width和height
//				//menuWindow.showAsDropDown(layout); //设置弹出效果
//				//menuWindow.showAsDropDown(null, 0, layout.getHeight());
//				menuWindow.showAtLocation(this.findViewById(R.id.mainweixin), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//				//如何获取我们main中的控件呢？也很简单
//				mClose = (LinearLayout)layout.findViewById(R.id.menu_close);
//				mCloseBtn = (LinearLayout)layout.findViewById(R.id.menu_close_btn);
//				
//				
//				//下面对每一个Layout进行单击事件的注册吧。。。
//				//比如单击某个MenuItem的时候，他的背景色改变
//				//事先准备好一些背景图片或者颜色
//				mCloseBtn.setOnClickListener (new View.OnClickListener() {					
//					@Override
//					public void onClick(View arg0) {						
//						//Toast.makeText(Main.this, "退出", Toast.LENGTH_LONG).show();
//						Intent intent = new Intent();
//			        	intent.setClass(MainWeixin.this,Exit.class);
//			        	startActivity(intent);
//			        	menuWindow.dismiss(); //响应点击事件之后关闭Menu
//					}
//				});				
//				menu_display = true;				
//			}else{
//				//如果当前已经为显示状态，则隐藏起来
//				menuWindow.dismiss();
//				menu_display = false;
//				}
//			
//			return false;
//		}
    	return false;
    }
//	//设置标题栏右侧按钮的作用
//	public void btnmainright(View v) {  
//		Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);			
//		startActivity(intent);	
//		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
//      }  	
//	public void startchat(View v) {      //小黑  对话界面
//		Intent intent = new Intent (MainWeixin.this,ChatActivity.class);			
//		startActivity(intent);	
//		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
//      }  
//	public void exit_settings(View v) {                           //退出  伪“对话框”，其实是一个activity
//		Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);			
//		startActivity(intent);	
//	 }
//	public void btn_shake(View v) {                                   //手机摇一摇
//		Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);			
//		startActivity(intent);	
//	}
	
	
	public void btnmainright(View view)
	{
		 isRecording = true;  
         new RecordPlayThread().start();// 开一条线程边录边放  
         timer.schedule(new RecBpm(), 1000, 250);
	}
	
	  class RecBpm extends TimerTask{

			@Override
			public void run() {
				int bpm = FetusCore.get();
				Message msg = new Message();
				msg.obj = "***bpm="+bpm+"***";
				showedList.add(bpm);
				recordWaveView.updateVisualizer(showedList);
				
				Log.i("TAG","***bpm="+bpm+"***");
				
			}
	    	
	    }
	  
	    class RecordPlayThread extends Thread {  
	        public void run() {  
	            try {  
	                byte[] buffer = new byte[recBufSize];  
	                audioRecord.startRecording();//开始录制  
	                audioTrack.play();//开始播放  
	                  
	                while (isRecording) {  
	                    //从MIC保存数据到缓冲区  
	                    int bufferReadResult = audioRecord.read(buffer, 0,  
	                            recBufSize);  
	  
	                    byte[] tmpBuf = new byte[bufferReadResult];  
	                    System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);  
	                    //写入数据即播放  
//	                    Log.i("TAG","tmpBuf.length="+tmpBuf.length+"---");
	                    FetusCore.put(tmpBuf, tmpBuf.length);
	                    audioTrack.write(tmpBuf, 0, tmpBuf.length);  
	                }  
	                audioTrack.stop();  
	                audioRecord.stop();  
	            } catch (Throwable t) {  
	                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();  
	            }  
	        }  
	    };  
	    
	    private void startDealThread()
	    {
	    	new Thread(){
	    		public void run()
	    		{
	    			FetusCore.deal();
	    		}
	    	}.start();
	    }
}
    
    

