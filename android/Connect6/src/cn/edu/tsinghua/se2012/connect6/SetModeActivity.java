package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class SetModeActivity extends Activity{
	private String[] PVEArray = {"�˻���ս", "���˶�ս"};
	private String[] PracticeArray = {"��ϰģʽ", "ʵսģʽ"};
	private String[] FirstArray = {"�������", "��������"};
	
	private Button PVEmodeBtn;
	private Button operationalModeBtn;
	private Button firstModeBtn;
	private Button okBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setmode);
		
		PVEmodeBtn = (Button)findViewById(R.id.PVEmode);
		operationalModeBtn = (Button)findViewById(R.id.operationalmode);
		firstModeBtn = (Button) findViewById(R.id.firstmode);
		okBtn = (Button)findViewById(R.id.setmodeok);
		
		if (StartActivity.isPVE){
			PVEmodeBtn.setText("�˻���ս");
		}else{
			PVEmodeBtn.setText("���˶�ս");
		}
		if (StartActivity.isPractice){
			operationalModeBtn.setText("��ϰģʽ");
		}else{
			operationalModeBtn.setText("ʵսģʽ");
		}
		if (StartActivity.isFirst){
			firstModeBtn.setText("�������");
		}else{
			firstModeBtn.setText("��������");
		}
		
		// ��սģʽ����
		PVEmodeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						StartActivity.isPVE = !StartActivity.isPVE;
						PVEmodeBtn.setText(PVEArray[(StartActivity.isPVE?0:1)]);		
					}
				});
		// ��Ϸģʽ��ť
		operationalModeBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						StartActivity.isPractice = !StartActivity.isPractice;
						operationalModeBtn.setText(PracticeArray[(StartActivity.isPractice?0:1)]);		
					}
				});
		// ����˳������
				firstModeBtn.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								StartActivity.isFirst = !StartActivity.isFirst;
								firstModeBtn.setText(FirstArray[(StartActivity.isFirst?0:1)]);	
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
