/*
 * Copyright 2012 Shuyang Jiang, Yipeng Ma and Bo Liu
 * 
 * This file is part of Connect6.

   Connect6 is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Connect6 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Connect6.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * 实现点击主菜单"关于我们"按钮弹出的界面
 * 
 * @version 1.0
 * @author Shuyang Jiang, Yipeng Ma and Bo Liu
 *
 */

public class AboutUsActivity extends Activity{
	/** 确定按钮 */
	Button okBtn;
	/** SoundPool对象，用来播放确定按钮按下的声音 */
	private SoundPool soundpool;
	
	/**
	 * 创建界面，做一些数据的初始化工作
	 */
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置为竖屏
        //先去除应用程序标题栏  注意：一定要在setContentView之前  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //将我们定义的窗口设置为默认视图  
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
	 * 播放确定按钮按下的声音
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
