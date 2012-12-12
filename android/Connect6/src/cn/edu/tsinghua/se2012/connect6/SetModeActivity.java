package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SetModeActivity extends Activity{
	static private boolean isPVE = true;
	static private boolean isPractice = true;
	private String[] PVEArray = {"�˻���ս", "���˶�ս"};
	private String[] PracticeArray = {"��ϰģʽ", "ʵսģʽ"};
	
	private Button PVEmodeBtn;
	private Button operationalModeBtn;
	private Button okBtn;
	private Button cancelBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setmode);
		
		PVEmodeBtn = (Button)findViewById(R.id.PVEmode);
		operationalModeBtn = (Button)findViewById(R.id.operationalmode);
		okBtn = (Button)findViewById(R.id.setmodeok);
		cancelBtn = (Button)findViewById(R.id.setmodecancel);
		
		if (isPVE){
			PVEmodeBtn.setText("�˻���ս");
		}else{
			PVEmodeBtn.setText("���˶�ս");
		}
		if (isPractice){
			operationalModeBtn.setText("��ϰģʽ");
		}else{
			operationalModeBtn.setText("ʵսģʽ");
		}
		
		// ��սģʽ����
		PVEmodeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						isPVE = !isPVE;
						PVEmodeBtn.setText(PVEArray[(isPVE?0:1)]);		
					}
				});
		// ��Ϸģʽ��ť
		operationalModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						isPractice = !isPractice;
						operationalModeBtn.setText(PracticeArray[(isPractice?0:1)]);		
					}
				});
		// ȷ����ť
		okBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = getIntent();
						Bundle bundle = new Bundle();
						bundle.putBoolean("isPVE", isPVE);
						bundle.putBoolean("isPractice", isPractice);
						intent.putExtras(bundle);
						setResult(0x717, intent);
						finish();
					}
				});
		// ȡ����ť
		cancelBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						finish();
					}
				});
	}
}
