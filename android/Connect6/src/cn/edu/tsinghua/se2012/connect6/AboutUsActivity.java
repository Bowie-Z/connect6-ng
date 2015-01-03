package cn.edu.tsinghua.se2012.connect6;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * 
 * @author jsy1992
 * ���ڽ���
 *
 */
public class AboutUsActivity extends Activity{
	Button okBtn;
	private SoundPool soundpool;
	
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ����Ϊ������
        //��ȥ��Ӧ�ó��������  ע�⣺һ��Ҫ��setContentView֮ǰ  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //�����Ƕ���Ĵ�������ΪĬ����ͼ  
        setContentView(R.layout.aboutus);
        
        okBtn = (Button)findViewById(R.id.aboutusok);
        soundpool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
        okBtn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	playSound();
		    	finish();	 
		    }
		});
    }

	/**
	 * ��������
	 */
	public void playSound(){
		if (StartActivity.soundOpen) {
			final int sourceId = soundpool.load(AboutUsActivity.this,
					R.raw.okbutton, 1);
			soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
						public void onLoadComplete(SoundPool soundPool,
								int sampleId, int status) {
							soundPool.play(sourceId, 1, 1, 0,
									0, 1);
						}
					});
		}
	}
	
}
