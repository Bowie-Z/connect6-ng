package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AboutUsActivity extends Activity{
	Button okBtn;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        //��ȥ��Ӧ�ó��������  ע�⣺һ��Ҫ��setContentView֮ǰ  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //�����Ƕ���Ĵ�������ΪĬ����ͼ  
        setContentView(R.layout.aboutus);
        
        okBtn = (Button)findViewById(R.id.aboutusok);
        okBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	finish();	 
		    }
		});
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
	} 
	
}
