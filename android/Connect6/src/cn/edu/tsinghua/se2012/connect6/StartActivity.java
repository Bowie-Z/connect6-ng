package cn.edu.tsinghua.se2012.connect6;

import java.util.Vector;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class StartActivity extends Activity {
	static public boolean isPVE = true;
	static public boolean isPractice = true;
	static public boolean isFirst = true;
	static public boolean soundOpen = true;
	static public boolean vibrateOpen = true;
	static public boolean hasChessManual = false;

	static float screenHeight; // ��Ļ�߶�
	static float screenWidth; // ��Ļ���
	static boolean flag = false;

	final int CODE = 0x717; // ��������ģʽ���������

	private ImageButton startGameBtn;
	private ImageButton setModeBtn;
	private ImageButton loadBtn;
	private ImageButton aboutUsBtn;
	private ImageButton exitBtn;
	private SoundPool soundpool;
	
	private boolean isExit = false;

	// ������Ϣ������ת
	Handler hd = new Handler() {
		@Override
		public void handleMessage(Message msg) {// ��д����
			switch (msg.what) {
			case 0:
				gotoMainView(); // ������
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ����Ϊ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics dm = new DisplayMetrics(); // ��ȡ�ֻ��ֱ���
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;

		readPreferences();

		if (flag == false) {
			gotoWelcomeView();
		} else {
			gotoMainView();
		}
	}

	// ��ӭ����
	public void gotoWelcomeView() {
		WelcomeView mView = new WelcomeView(this);
		setContentView(mView);
	}

	// ����������
	public void gotoMainView() {
		setContentView(R.layout.start);
		if (flag == false) {
			flag = true;
		}

		aboutUsBtn = (ImageButton) findViewById(R.id.aboutus);
		startGameBtn = (ImageButton) findViewById(R.id.startgame);
		setModeBtn = (ImageButton) findViewById(R.id.setmode);
		// loadBtn = (ImageButton) findViewById(R.id.openchessmanual);
		exitBtn = (ImageButton) findViewById(R.id.exit);
		soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);

		// ��ʼ��Ϸ��ť
		startGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();

				Intent intent = new Intent(StartActivity.this,
						GameActivity.class);
				startActivity(intent);
			}
		});

		// ����ģʽ��ť
		setModeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				
				Intent intent = new Intent(StartActivity.this,
						SetModeActivity.class);
				startActivity(intent);
			}
		});

		// �������ǰ�ť
		aboutUsBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playSound();
				
				Intent intent = new Intent(StartActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
			}
		});

		// �˳���ť
		exitBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				isExit = true;
				playSound();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			writePreferences();
			flag = false;
			finish();
			System.exit(0);
		}
		return true;
	}

	public void readPreferences() {
		SharedPreferences preferences = getSharedPreferences("Pref",
				MODE_PRIVATE);
		isPVE = preferences.getBoolean("PVE", true);
		isPractice = preferences.getBoolean("Practice", true);
		isFirst = preferences.getBoolean("First", true);
		soundOpen = preferences.getBoolean("sound", true);
		vibrateOpen = preferences.getBoolean("vibrate", true);
		hasChessManual = preferences.getBoolean("haschessmanual", false);
	}

	public void writePreferences() {
		SharedPreferences preferences = getSharedPreferences("Pref",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("PVE", isPVE);
		editor.putBoolean("Practice", isPractice);
		editor.putBoolean("First", isFirst);
		editor.putBoolean("sound", soundOpen);
		editor.putBoolean("vibrate", vibrateOpen);
		editor.putBoolean("haschessmanual", hasChessManual);
		editor.commit();
	}
	
	//�˳���Ϸ
	public void exitGame(){
		writePreferences();
		flag = false;
		finish();
		System.exit(0);
	}
	
	public void playSound(){
		if (soundOpen) {
			final int sourceId = soundpool.load(StartActivity.this,
					R.raw.startbutton, 1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0,
									0, 1);
							if (isExit){
								exitGame();
							}
						}
					});
		}
	}
}
