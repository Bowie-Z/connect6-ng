package cn.edu.tsinghua.se2012.connect6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
	static float screenHeight; // ��Ļ�߶�
	static float screenWidth; // ��Ļ���
	static boolean flag = false;
	
	final int CODE = 0x717;			//��������ģʽ���������

	ImageButton startGameBtn;
	ImageButton setModeBtn;
	ImageButton aboutUsBtn;
	ImageButton exitBtn;

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

		if (flag == false) {
			gotoWelcomeView();
		} else {
			gotoMainView();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CODE && resultCode == CODE){
			Bundle bundle = data.getExtras();
			isPVE = bundle.getBoolean("isPVE");
			isPractice = bundle.getBoolean("isPractice");
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
		exitBtn = (ImageButton) findViewById(R.id.exit);

		// ��ʼ��Ϸ��ť
		startGameBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						GameActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("isPVE", isPVE);
				bundle.putBoolean("isPractice", isPractice);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});

		// ����ģʽ��ť
		setModeBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						SetModeActivity.class);
				startActivity(intent);
			}
		});

		// �������ǰ�ť
		aboutUsBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StartActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
			}
		});
		
		//�˳���ť
		exitBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				flag = false;
				finish();
				System.exit(0);
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(KeyEvent.KEYCODE_BACK == keyCode){
			flag = false;
			finish();
			System.exit(0);
		}
		return true;
	}
}
