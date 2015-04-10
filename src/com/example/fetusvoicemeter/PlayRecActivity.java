package com.example.fetusvoicemeter;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.fetusvoicemeter.db.RecorderDAO;
import com.example.fetusvoicemeter.entity.RecorderEntity;
import com.example.fetusvoicemeter.recorder.RecordingProcess;
import com.example.fetusvoicemeter.recorder.RecordingProcess.OnRecordingProcessListener;
import com.example.fetusvoicemeter.utils.Utils;
import com.example.fetusvoicemeter.view.HKRecordWaveView;
import com.fetus.FetusCore;

public class PlayRecActivity extends Activity implements OnRecordingProcessListener{

	
	private TextView et_minBeat;
	private TextView et_maxBeat;
	private TextView et_averageBeat;
	
	private TextView rec_fq_num;

	private TextView rec_audio_time;
	
	Chronometer chronometer;
	
	private HKRecordWaveView recordWaveView;
	
	private RecordingProcess mRecProcess;
	
	private Integer[] beatValus;
	
	private Timer timer;
	
	private int count;
	
	/**
	 * 在画布上正在显示的数据集合
	 */
	private ArrayList<Integer> showedList = new ArrayList<Integer>();

	
	private RecorderDAO recorderDao;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int bpm = msg.arg1;
			rec_fq_num.setText(String.valueOf(bpm));
			showedList.add(bpm);
			recordWaveView.updateVisualizer(showedList);
		}

	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_playrec);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		recorderDao = RecorderDAO.createChatMsgDAO(this);
		this.mRecProcess = new RecordingProcess(this);
		mRecProcess.setListener(this);
		
		initUI();
		
		Intent intent = getIntent();
		File file = (File)intent.getSerializableExtra("pcmfile");
		String name = file.getName();
		RecorderEntity recorderEntity = recorderDao.getData(name);
		beatValus = recorderEntity.getBeatValues();
		et_minBeat.setText(Utils.getMinFromArray(beatValus)+"");
		et_maxBeat.setText(Utils.getMaxFromArray(beatValus)+"");
		et_averageBeat.setText(Utils.getAverageFromArray(beatValus)+"");
		((TextView)findViewById(R.id.TV_date)).setText(Utils.getTimeString("yyyy-MM-dd"));
		
		startPlay(file);
	}
	
	private void startPlay(File file)
	{
		this.chronometer.setBase(SystemClock.elapsedRealtime());
	    this.chronometer.start();
	    mRecProcess.startAudioPlay(file);
	    timer = new Timer();
	    timer.schedule(new RecBpm(), 0, 250);
	}

	private void initUI() {
		rec_fq_num = (TextView) this.findViewById(R.id.rec_fq_num);
		
		et_minBeat = (TextView) this.findViewById(R.id.minBeat);
		et_maxBeat = (TextView) this.findViewById(R.id.maxBeat);
		et_averageBeat = (TextView) this.findViewById(R.id.averageBeat);
		this.chronometer = (Chronometer)findViewById(R.id.chronometer);
		recordWaveView = (HKRecordWaveView) this.findViewById(R.id.rec_wave);
		rec_fq_num = (TextView) this.findViewById(R.id.playRec_fq_num);
	}

	public void onDestroy()
	{
		stopPlay();
		super.onDestroy();
		
	}
	
	private void stopPlay()
	{
		chronometer.stop();
		mRecProcess.stopAudioPlay();
		if(timer != null)
		{
			timer.cancel();
			timer =null;
		}
		
	}

	class RecBpm extends TimerTask {
		
		@Override
		public void run() {
			int bpm = 0;
			
			if(count < beatValus.length)
			{
				bpm = beatValus[count];
			}
			Message msg = new Message();
			msg.arg1 = bpm;
			handler.sendMessage(msg);
			count++;
		}
	}

	@Override
	public void onPlayStop() {
		Log.i("TAG","onPlayStop");
		stopPlay();
		
	}
	
}
