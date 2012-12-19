package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class GameSettingActivity extends Activity {
	
	private String[] openArray = { "����", "�ر�" };

	private Button soundOpenBtn;
	private Button vibrateOpenBtn;
	private Button okBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ����Ϊ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ȫ��
		setContentView(R.layout.gamesetting);
		
		soundOpenBtn = (Button)findViewById(R.id.soundOpen);
		vibrateOpenBtn = (Button)findViewById(R.id.vibrateOpen);
		okBtn = (Button)findViewById(R.id.gamesettingok);
		
		if (StartActivity.soundOpen){
			soundOpenBtn.setText("����");
		}else{
			soundOpenBtn.setText("�ر�");
		}
		if (StartActivity.vibrateOpen){
			vibrateOpenBtn.setText("����");
		}else{
			vibrateOpenBtn.setText("�ر�");
		}
	
	// ����Ч������
	soundOpenBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					StartActivity.soundOpen = !StartActivity.soundOpen;
					soundOpenBtn.setText(openArray[(StartActivity.soundOpen?0:1)]);		
				}
			});
	//��Ч����ť
	vibrateOpenBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					StartActivity.vibrateOpen = !StartActivity.vibrateOpen;
					vibrateOpenBtn.setText(openArray[(StartActivity.vibrateOpen?0:1)]);		
				}
			});
	// ȷ����ť
	okBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			});
	}
}
