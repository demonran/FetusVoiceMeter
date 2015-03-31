package com.example.fetusvoicemeter.recorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.example.fetusvoicemeter.utils.HKCommonUtils;
import com.fetus.FetusCore;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class RecordingProcess {

	Context mContext;

	boolean isRecording = false;// �Ƿ�¼�ŵı��
	static final int frequency = 44100;
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	int recBufSize, playBufSize;
	AudioRecord audioRecord;
	AudioTrack audioTrack;

	private OutputStream mOutputStream;
	private File mWriteFile;
	private long mWriteFileStartTime;
	private boolean mIsWriteFile;
	private long mWriteFileTimeLen;

	public RecordingProcess(Context context) {
		mContext = context;
		initRecord();
	}

	private void initRecord() {
		recBufSize = AudioRecord.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);
		Log.i("TAG", "recBufSize=" + recBufSize + "---");

		playBufSize = AudioTrack.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);
		Log.i("TAG", "playBufSize=" + playBufSize + "---");
		// -----------------------------------------
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
				channelConfiguration, audioEncoding, recBufSize);

		audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, frequency,
				channelConfiguration, audioEncoding, playBufSize,
				AudioTrack.MODE_STREAM);
	}

	public void startAudioRecord(boolean paramBoolean) {
		if (this.audioRecord == null)
			try {
				this.audioRecord.startRecording();
				this.isRecording = true;
				if (paramBoolean) {
					new RecordPlayThread().start();// ��һ���̱߳�¼�߷�
				}
			} catch (Exception localException) {
			}
	}

	class RecordPlayThread extends Thread {
		public void run() {
			try {
				byte[] buffer = new byte[recBufSize];
				audioRecord.startRecording();// ��ʼ¼��
				audioTrack.play();// ��ʼ����
				// startWriteFile();

				while (isRecording) {
					// ��MIC�������ݵ�������
					int bufferReadResult = audioRecord.read(buffer, 0,
							recBufSize);

					byte[] tmpBuf = new byte[bufferReadResult];
					System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
					// д�����ݼ�����
					// Log.i("TAG","tmpBuf.length="+tmpBuf.length+"---");
					// audioTrack.write(buffer, 0, bufferReadResult);
					audioTrack.write(tmpBuf, 0, tmpBuf.length);
					// FetusCore.put(tmpBuf, tmpBuf.length);
					// mOutputStream.write(tmpBuf);

				}
				audioTrack.stop();
				audioRecord.stop();
				mOutputStream.close();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public boolean startWriteFile() {
		boolean bool = false;
		if (createWriteFile())
			;
		try {
			this.mOutputStream = new FileOutputStream(this.mWriteFile);
			this.mWriteFileStartTime = System.currentTimeMillis();
			this.mIsWriteFile = true;
			bool = true;
			return bool;
		} catch (FileNotFoundException localFileNotFoundException) {
		}
		return true;
	}

	private boolean createWriteFile() {
		File localFile = Environment.getExternalStorageDirectory();
		if (localFile.canWrite())
			;
		{
			this.mWriteFile = new File(localFile + File.separator + "weiyu"
					+ HKCommonUtils.getTimeString() + ".pcm");
		}
		return true;
	}

	private void startDealThread() {
		new Thread() {
			public void run() {
				FetusCore.deal();
			}
		}.start();
	}

	private void setAudioNormal() {
		AudioManager localAudioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		Log.i("TAG", localAudioManager.isSpeakerphoneOn() + "**");
		localAudioManager.setMode(AudioManager.MODE_NORMAL);
		if (localAudioManager.isWiredHeadsetOn())
			localAudioManager.setSpeakerphoneOn(false);

	}

	// �����������
	private void setAudioOnSpeaker() {
		AudioManager audioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		// audioManager.setMode(AudioManager.MODE_IN_CALL);
		audioManager.setSpeakerphoneOn(true);// ʹ����������ţ���ʹ�Ѿ��������
		audioManager
				.setStreamVolume(AudioManager.STREAM_VOICE_CALL, audioManager
						.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
						AudioManager.STREAM_VOICE_CALL);
	}

	public void stopWriteFile() {
		this.mWriteFileTimeLen = (System.currentTimeMillis() - this.mWriteFileStartTime);
		this.mIsWriteFile = false;
		stopAudioRecord();
	}
	
	public void stopAudioRecord()
	{
		 this.isRecording = false;
	      this.audioRecord.stop();
	}
}
